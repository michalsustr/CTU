/* 
 * File:   main.c
 * Author: michal
 *
 * Created on April 14, 2011, 1:19 PM
 */

#include <stdio.h>
#include <stdlib.h>
#define SPRAVODLIVE_ROZDELENIE 1
#define NESPRAVODLIVE_ROZDELENIE 0
#define NAD_PRIEMER 2

int check(int *lupy, int *priradenie, int n, int priemer) {
    int suma[3], i;

    for(i = 0; i < 3; i++) {
        suma[i] = 0;
    }
    for(i = 0; i < n; i++) {
        suma[ priradenie[i] ] += lupy[i];
    }
    if(suma[0] == suma[1] && suma[1] == suma[2]) {
        return SPRAVODLIVE_ROZDELENIE;
    }
    if(suma[0] > priemer || suma[1] > priemer || suma[2] > priemer) {
        return NAD_PRIEMER;
    }
    return NESPRAVODLIVE_ROZDELENIE;
}

int iteruj(int *lupy, int *priradenie, int n, int priemer, int level) {
    int i;
    if(level == n) {
        if(check(lupy, priradenie,n,priemer) == SPRAVODLIVE_ROZDELENIE) {
            return 1;
        } else {
            return 0;
        }
    }
    
    for(i = 0; i < 3; i++) {
        priradenie[level] = i;

        if(check(lupy, priradenie,level,priemer)==NAD_PRIEMER) continue;
        if(iteruj(lupy,priradenie,n,priemer,level+1)) return 1;
    }
    return 0;
}

int main(int argc, char** argv) {
    int n,i;
    int lupy[20], priradenie[20];
    int priemer = 0;

    printf("Zadajte pocet predmete:");
    if(scanf("%d", &n) != 1 || n < 3 || n > 20) {
        printf("---Vysledky---\n");
        printf("Nespravny vstup.");
        return 1;
    }
    printf("Zadaj cenu jednotlivych predmetu:");


    for(i = 0; i < n; i++) {
        scanf("%d", &lupy[i]);
    }

    printf("---Vysledky---\n");

    for(i = 0; i < n; i++) {
        if(lupy[i] <= 0) {
            printf("Nespravny vstup.\n");
            return 1;
        }
        priemer += lupy[i];
    }

    if(priemer % 3 != 0) {
        printf("Predmety nelze rozdelit spravedlive.");
        return 0;
    }

    priemer /= 3;
    priradenie[0] = 0;

    if(iteruj(&lupy,&priradenie,n,priemer,1)) {
        printf("Predmety lze rozdelit spravedlive.");
    } else {
        printf("Predmety nelze rozdelit spravedlive.");
    }
    return (EXIT_SUCCESS);
}
