#include<stdlib.h>
#include<stdio.h>

#define SYM_00 0
#define SYM_0V 1
#define SYM_H0 2
#define SYM_HV 3


typedef struct {
    int rozmer;
    int sucet;
    int symetria;
    int **hodnoty;
} Matica;



void zlyVstup() {
    printf("---Vysledky---\n");
    printf("Nespravny vstup.");
    exit(1);
}

void vypisMaticu(Matica *mat) {
    int i,k;
    for(i =0;i < mat->rozmer; i++) {
        for(k =0;k < mat->rozmer; k++) {
            printf("%d ", mat->hodnoty[i][k]);
        }
        printf("\n");
    }
}


int ziskajSymetriu(Matica *mat) {
    int h = 1,v = 1;
    int i,j;
    int polovica;

    if(mat->rozmer % 2 == 0) {
        polovica = mat->rozmer/2;
    } else {
        polovica = (mat->rozmer-1)/2;
    }

    // horizontalny
    for(i = 0; i < polovica; i++) {
        for(j = 0; j < mat->rozmer; j++) {
            if(mat->hodnoty[i][j] != mat->hodnoty[ mat->rozmer-1-i ][j]) h = 0;
        }
    }
    // vertikalny
    for(i = 0; i < mat->rozmer; i++) {
        for(j = 0; j < polovica; j++) {
            if(mat->hodnoty[i][j] != mat->hodnoty[i][ mat->rozmer-1-j ]) v = 0;
        }
    }

    if(h == 1 && v == 0) {
        return SYM_H0;
    } else if(h == 0 && v == 1) {
        return SYM_0V;
    } else if(h == 1 && v == 1) {
        return SYM_HV;
    } else {
        return SYM_00;
    }
}

Matica *vytvorMaticu(int poradie) {
    int roz;
    int i=0,j;

    Matica *mat;
    mat = (Matica*) malloc(sizeof(Matica));
    if(mat == NULL) {
        printf("Nedostatok pamati\n");
        exit(1);
    }

    printf("Zadejte velikost %d. matice:\n",poradie+1);
    if(scanf("%d", &roz) != 1 || roz < 1) {
        zlyVstup();
    }
        // naalokuj pole hodnot pre maticu
    (*mat).rozmer = roz;

    mat->hodnoty = (int **) malloc(mat->rozmer * sizeof(int*));
    if(mat->hodnoty == NULL) {
        printf("Nedostatok pamati\n");
        exit(1);
    }

    printf("Zadajte prvky %d. matice:\n",poradie+1);
    mat->sucet = 0;
    for(i=0; i < mat->rozmer; i++) { // riadky
        // alokuj stplce
        if((mat->hodnoty[i] = (int *) malloc(mat->rozmer * sizeof(int))) == NULL) {
            printf("Nedostatok pamati\n");
            exit(1);
        }
        for(j=0; j < mat->rozmer; j++) {
            if(scanf("%d", &mat->hodnoty[i][j]) != 1 || mat->hodnoty[i][j] < 0) {
                zlyVstup();
            }
            mat->sucet += mat->hodnoty[i][j];
        }
    }
    mat->symetria = ziskajSymetriu(mat);

    return mat;
}

int radenieSumou(const void *a, const void *b) {
    Matica **m1 = (Matica **) a;
    Matica **m2 = (Matica **) b;

    int r;
    r = (*m1)->sucet - (*m2)->sucet;
    if (r == 0) {
        return (*m2)->symetria - (*m1)->symetria;
    } else {
        return r;
    }
}
void dealokace(int **zacatek, int n) {

    int i;
    for(i=0; i<n; i++) {
        free(*(zacatek++));
    }

}

void uklid(Matica **pole, int n) {

    int i;
    for(i=0; i<n; i++) {

        int r;
        r = pole[i]->rozmer;

        dealokace(pole[i]->hodnoty, r);
        free(pole[i]->hodnoty);
        free(pole[i]);
    }

    free(pole);

}


int main() {
    int pocetMatic, rozmer;
    int i;
    Matica **poleMatic;

    printf("Zadejte pocet matic: \n");
    if(scanf("%d", &pocetMatic) != 1 || pocetMatic < 1) {
        zlyVstup();
    }

    // alokuj ukazatele na matice
    if ((poleMatic = (Matica **) malloc(pocetMatic * sizeof(Matica*))) == NULL) {
        printf("Nedostatok pamati\n");
        exit(1);
    }

    for(i = 0; i < pocetMatic; i++) {
        poleMatic[i] = vytvorMaticu(i);
    }

    // zorad matice
    qsort((void *) poleMatic, pocetMatic, sizeof(*poleMatic), radenieSumou);

    // vypis matice
    for(i = 0; i < pocetMatic; i++) {
        printf("Soucet prvku matice je %d\n", poleMatic[i]->sucet);
        vypisMaticu(poleMatic[i]);
    }

    printf("---Vysledky---\n");
    for(i = 0; i < pocetMatic; i++) {
        switch(poleMatic[i]->symetria) {
            case SYM_00:
                printf("00 ");
                break;
            case SYM_H0:
                printf("H0 ");
                break;
            case SYM_0V:
                printf("0V ");
                break;
            case SYM_HV:
                printf("HV ");
                break;
        }
    }

    uklid(poleMatic, pocetMatic);

    return 0;
}

