#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char** argv) {
    char ch;
    char conv_table[256]; /* only ascii */
    int i;

    char tr_from[46] = "\n1234567890-=WERTYUIOP[]\\SDFGHJKL;'XCVBNM,./";
    char tr_to[46] =   "\n`1234567890-QWERTYUIOP[]ASDFGHJKL;ZXCVBNM,.";

    for(i = 0; i < 256; i++) {
        conv_table[i] = ' ';
    }
    for(i = 0; i < strlen(tr_from); i++){
        conv_table[tr_from[i]] = tr_to[i];
    }

    while((ch = getchar()) != EOF) {
        if(conv_table[ch] != ' ') {
            putchar(conv_table[ch]);
        }
        if(ch == ' ') putchar(ch);
    }

    return (EXIT_SUCCESS);
}

