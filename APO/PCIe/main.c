/*
 * File:   main.c
 * Author: Michal Šustr
 *         Matúš Cvengroš
 *
 *
 * Automat for tickets, using external card that is accessed via PCIe
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <signal.h>

#include "device.h"

// device identification
#define DEVICE 0x1f321172

// location of coin stack file
#define COIN_STACK "/tmp/coins"
// beep on key press
#define BEEP 0

// prices of tickets
#define CENA_1 32  // zakladni plna
#define CENA_2 16  // zakladni zlevnena
#define CENA_3 24  // kratkodoba plna
#define CENA_4 12  // kratkodoba zlevnena
#define CENA_5 110 // celodenni plna
#define CENA_6 55  // celodenni zlevnena

// coins counts structure (used for stack and input)
typedef struct {
    int cnt1, cnt2, cnt5, cnt10, cnt20, cnt50;
} coins;

// tickets count
typedef struct {
    int cnt1, cnt2, cnt3, cnt4, cnt5, cnt6;
} tickets_struct;

coins stack, input;
tickets_struct tickets;



// load coins stack from file
coins load_coins() {
    coins c;
    FILE *filep;

    filep = fopen(COIN_STACK, "r");
    if(!filep) { // could not read the file, maybe it does not exist
        c.cnt1=c.cnt2=c.cnt5=c.cnt10=c.cnt20=c.cnt50=0;
        printf("Coin stack file does not exist\n");
        return c;
    }
    if(!fread(&c, sizeof(coins),1,filep)) {
        fprintf(stderr, "Could not read contents of coin stack file '%s'", COIN_STACK);
        exit(1);
    }
    fclose(filep);

    // show coins in stack
    printf("Loading coins in stack:\n");
    printf(" 1 Kc:\t%d coins\n",    c.cnt1);
    printf(" 2 Kc:\t%d coins\n",    c.cnt2);
    printf(" 5 Kc:\t%d coins\n",    c.cnt5);
    printf("10 Kc:\t%d coins\n",    c.cnt10);
    printf("20 Kc:\t%d coins\n",    c.cnt20);
    printf("50 Kc:\t%d coins\n\n",  c.cnt50);

    return c;
}

// save coins stack into file
void save_coins(coins c) {
    FILE *filep;
    filep = fopen(COIN_STACK, "w");
    if(!filep) {
        fprintf(stderr, "Could not open coin stack file '%s' for write", COIN_STACK);
        exit(1);
    }

    if(!fwrite(&c, sizeof(coins),1,filep)) {
        fprintf(stderr, "Could not write coin stack file '%s'", COIN_STACK);
        exit(1);
    }
    fclose(filep);
	
   // show coins in stack
    printf("Saving coins in stack:\n");
    printf(" 1 Kc:\t%d coins\n",    c.cnt1);
    printf(" 2 Kc:\t%d coins\n",    c.cnt2);
    printf(" 5 Kc:\t%d coins\n",    c.cnt5);
    printf("10 Kc:\t%d coins\n",    c.cnt10);
    printf("20 Kc:\t%d coins\n",    c.cnt20);
    printf("50 Kc:\t%d coins\n\n",  c.cnt50);

}

// get the current price to be written on LCD
int get_price() {
    return
        CENA_1 * tickets.cnt1 -  1 * input.cnt1  +
        CENA_2 * tickets.cnt2 -  2 * input.cnt2  +
        CENA_3 * tickets.cnt3 -  5 * input.cnt5  +
        CENA_4 * tickets.cnt4 - 10 * input.cnt10 +
        CENA_5 * tickets.cnt5 - 20 * input.cnt20 +
        CENA_6 * tickets.cnt6 - 50 * input.cnt50;
}

// write price on LCD
void update_price(int sign) {
    char text[16] = "";
    char buffer[13];
    sprintf( buffer, "%d", sign*get_price());

    strcat(text, buffer);
    strcat(text, " Kc");
    write_lcd_line(text, 1);
}

void choose_ticket() {
    tickets.cnt1=tickets.cnt2=tickets.cnt3=tickets.cnt4=tickets.cnt5=tickets.cnt6=0;
    input.cnt1=input.cnt2=input.cnt5=input.cnt10=input.cnt20=input.cnt50=0;

    write_lcd_line("Zvolte jizdenky", 0);
    update_price(1);
}

// return new stack if machine can give back change,
// if not, stack.cnt1 is set to -1
coins can_return_money(int price, coins stack) {
    if(price == 0) return stack;

    if(price >= 50 && stack.cnt50 > 0) { stack.cnt50--; return can_return_money(price-50, stack); }
    if(price >= 20 && stack.cnt20 > 0) { stack.cnt20--; return can_return_money(price-20, stack); }
    if(price >= 10 && stack.cnt10 > 0) { stack.cnt10--; return can_return_money(price-10, stack); }
    if(price >= 5  && stack.cnt5  > 0) { stack.cnt5--;  return can_return_money(price-5,  stack); }
    if(price >= 2  && stack.cnt2  > 0) { stack.cnt2--;  return can_return_money(price-2,  stack); }
    if(price >= 1  && stack.cnt1  > 0) { stack.cnt1--;  return can_return_money(price-1,  stack); }

    stack.cnt1 = -1;
    return stack;
}

void storno() {
    tickets.cnt1=tickets.cnt2=tickets.cnt3=tickets.cnt4=tickets.cnt5=tickets.cnt6=0;
    write_lcd_line("Storno, vracim", 0);
    update_price(-1);
    input.cnt1=input.cnt2=input.cnt5=input.cnt10=input.cnt20=input.cnt50=0;
}

void pay_for_ticket() {
    int price, add_coins_to_stack = 0;
    coins new_stack;

    price = get_price();
    if(price == 0) {
         write_lcd_line("Zaplaceno", 0);
         write_lcd_line("", 1);
         add_coins_to_stack = 1;
    } else {
        // check if we have enough of coins in stack
        new_stack = can_return_money(-price, stack);
         if(new_stack.cnt1 != -1) {
            // print to terminal which coins are returned
            printf("Returning coins\n");
            printf(" 1 Kc:\t%d coins\n",    stack.cnt1-new_stack.cnt1);
            printf(" 2 Kc:\t%d coins\n",    stack.cnt2-new_stack.cnt2);
            printf(" 5 Kc:\t%d coins\n",    stack.cnt5-new_stack.cnt5);
            printf("10 Kc:\t%d coins\n",    stack.cnt10-new_stack.cnt10);
            printf("20 Kc:\t%d coins\n",    stack.cnt20-new_stack.cnt20);
            printf("50 Kc:\t%d coins\n\n",  stack.cnt50-new_stack.cnt50);

            stack = new_stack;
            add_coins_to_stack = 1;

            write_lcd_line("Zaplaceno,vracim", 0);
            update_price(-1);
         } else {
            add_coins_to_stack = 0;

            write_lcd_line("Automat nema", 0);
            write_lcd_line("mince na vraceni", 1);
            sleep(3);
            storno();
        }
    }

    if(add_coins_to_stack) {
         stack.cnt1  += input.cnt1;
         stack.cnt2  += input.cnt2;
         stack.cnt5  += input.cnt5;
         stack.cnt10 += input.cnt10;
         stack.cnt20 += input.cnt20;
         stack.cnt50 += input.cnt50;

         save_coins(stack);
    }
}

void quitproc() {
    printf("\nTurning off device...\n");
    turn_off();
    exit(0);
}

int main(int argc, char** argv) {
    int input_key, last_key = -1;
    int is_paying = 0;

    signal(SIGINT, quitproc);

    // load coins from file
    stack=load_coins();

    // initialize the device
    turn_on(DEVICE);
    stop_sound();
    hide_leds();
    initialize_lcd();

    choose_ticket();

    printf("Waiting for input...\n");

    while(1) {
        // get current key pressed, -1 for none
        input_key = read_keyboard();

        // key has been released
        if(input_key == -1 && last_key != -1) {
//            printf("key: %d\n", last_key);

            // interpret what key has been pressed
            // it depends whether we choose tickets or pay for them
            if(!is_paying) {
                switch(last_key) {
                    case 0: tickets.cnt1++; break;
                    case 1: tickets.cnt2++; break;
                    case 4: tickets.cnt3++; break;
                    case 5: tickets.cnt4++; break;
                    case 7: tickets.cnt5++; break;
                    case 8: tickets.cnt6++; break;
                }
            } else {
                switch(last_key) {
                    case 0: input.cnt1++; break;
                    case 1: input.cnt2++; break;
                    case 4: input.cnt5++; break;
                    case 5: input.cnt10++; break;
                    case 7: input.cnt20++; break;
                    case 8: input.cnt50++; break;
                }

                // ticket has been payed for
                if(get_price() <= 0) {
                    pay_for_ticket();
                    is_paying = 0;
                    sleep(1);
                    choose_ticket();
                }
            }

            // choose paying for ticket
            if(last_key == 13) {
                is_paying = 1;
                write_lcd_line("Vhodte mince", 0);
            }
            // storno paying / choosing
            if(last_key == 12) {
                storno();
                sleep(1);
                choose_ticket();
                is_paying = 0;
            }

            update_price(1);
        }

        last_key = input_key;
    }

    printf("Exiting application\n");
    return (EXIT_SUCCESS);
}

