#include<stdlib.h>
#include<stdio.h>

#define SYM_00 0
#define SYM_0V 1
#define SYM_H0 2
#define SYM_HV 3

int soucetPrvku(int **matica, int rozmer) {
    int i,k;
    int sucet = 0;
    for(i =0;i < rozmer; i++) {
        for(k =0;k < rozmer; k++) {
            sucet+= matica[i][k];
        }
    }
    return sucet;
}

void vypisMaticu(int **matica, int rozmer) {
    int i,k;
    for(i =0;i < rozmer; i++) {
        for(k =0;k < rozmer; k++) {
            printf("%d ", matica[i][k]);
        }
        printf("\n");
    }
}

int ziskajSymetriu(int **matica, int rozmer) {
    int h = 1,v = 1;
    int i,j;
    int polovica;

    if(rozmer % 2 == 0) {
        polovica = rozmer/2;
    } else {
        polovica = (rozmer-1)/2;
    }

    // horizontalny
    for(i = 0; i < polovica; i++) {
        for(j = 0; j < rozmer; j++) {
            if(matica[i][j] != matica[ rozmer-1-i][j]) h = 0;
        }
    }
    // vertikalny
    for(i = 0; i < rozmer; i++) {
        for(j = 0; j < polovica; j++) {
            if(matica[i][j] != matica[i][rozmer-1-j]) v = 0;
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


int radenieSumou(const void *a, const void *b) {
    int **s1, **s2;

    s1 = (int**) a;
    s2 = (int**) b;

    if ((*s1)[1] > (*s2)[1]) return 1;
    if ((*s1)[1] < (*s2)[1]) return -1;
    if ((*s1)[2] > (*s2)[2]) return -1;
    if ((*s1)[2] < (*s2)[2]) return 1;
    return 0;
}


int main() {
    int pocetMatic;
    int rozmer;
    int *rozmery;
    int ***matice;
    int **suctyPrvkov; // 2D pole; 0 obsahuje index matice, 1 obsahuje sucet, 2 symetriu

    int i,j,k;

    printf("Zadejte pocet matic: \n");
    if(scanf("%d", &pocetMatic) != 1 || pocetMatic < 1) {
        printf("---Vysledky---\n");
        printf("Nespravny vstup.");
        return 1;
    }
    // alokuj prvu dimenziu pola
    if ((matice = (int***) malloc(pocetMatic * sizeof(int**))) == NULL) {
        printf("Nedostatok pamati\n");
        return 1;
    }
    // alokuj pole pre ukladanie rozmerov matic
    if ((rozmery = (int*) malloc(pocetMatic * sizeof(int))) == NULL) {
        printf("Nedostatok pamati\n");
        return 1;
    }
    // alokuj pole pre ukladanie suctov matic
    if ((suctyPrvkov = (int**) malloc(pocetMatic * sizeof(int*))) == NULL) {
        printf("Nedostatok pamati\n");
        return 1;
    }

    for(i = 0; i < pocetMatic; i++) {
        printf("Zadejte velikost %d. matice:\n",i+1);
        if(scanf("%d", &rozmer) != 1 || rozmer < 1) {
            printf("---Vysledky---\n");
            printf("Nespravny vstup.");
            return 1;
        }
        // uloz rozmer
        rozmery[i] = rozmer;

        matice[i] = (int **) malloc(rozmer * sizeof(int*));
        suctyPrvkov[i] = (int *) malloc(3 * sizeof(int));
        if(matice[i] == NULL || suctyPrvkov[i] == NULL) {
            printf("Nedostatok pamati\n");
            return 1;
        }

        printf("Zadajte prvky %d. matice:\n",i+1);

        for(j=0; j < rozmer; j++) { // riadky
            // alokuj stplce
            if((matice[i][j] = (int *) malloc(rozmer * sizeof(int))) == NULL) {
                printf("Nedostatok pamati\n");
                return 1;
            }
            for(k=0; k < rozmer; k++) {
                if(scanf("%d", &matice[i][j][k]) != 1 || matice[i][j][k] < 1) {
                    printf("---Vysledky---\n");
                    printf("Nespravny vstup.");
                    return 1;
                }
            }

        }
    }

    for(i = 0; i < pocetMatic; i++) {
        suctyPrvkov[i][0] = i;
        suctyPrvkov[i][1] = soucetPrvku(matice[i], rozmery[i]);
        suctyPrvkov[i][2] = ziskajSymetriu(matice[i], rozmery[i]);
    }

    // zorad matice
    qsort((void *) suctyPrvkov, pocetMatic, sizeof(*suctyPrvkov), radenieSumou);

    // vypis matice
    for(i = 0; i < pocetMatic; i++) {
        printf("Soucet prvku matice je %d\n", suctyPrvkov[i][1]);
        vypisMaticu(matice[  suctyPrvkov[i][0]  ], rozmery[  suctyPrvkov[i][0]  ]);
    }

    printf("---Vysledky---\n");
    for(i = 0; i < pocetMatic; i++) {
        switch(suctyPrvkov[i][2]) {
            case SYM_00:
                printf("00");
                break;
            case SYM_H0:
                printf("H0");
                break;
            case SYM_0V:
                printf("0V");
                break;
            case SYM_HV:
                printf("HV");
                break;
        }
        if(i != pocetMatic-1) {
            printf(" ");
        }
    }

    return 0;
}
