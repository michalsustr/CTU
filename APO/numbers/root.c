#include <stdio.h>
#include <stdlib.h>

#define DELKA_SIG 1                     // bitová délka znaménka
#define DELKA_EXP 8                     // bitová délka exponentu
#define DELKA_MAN 23                    // bitová délka mantisy
#define DELKA (DELKA_SIG+DELKA_EXP+DELKA_MAN) // celková bitová délka FP hodnoty
#define BIAS      127                   // posun exponentu

// unie, které slouží k rozkladu FP hodnoty na její bitové složky
typedef union t_fp32 {
    float        fp;                    // hodnota uložená v FP formátu
    unsigned int uint;                  // 32 bitové slovo na stejném místě paměti
    struct {                            // rozklad 32 bitového slova na tři složky:
        unsigned int mantisa:DELKA_MAN; // - 23bitovou mantisu
        unsigned int exp:DELKA_EXP;     // - 8bitový exponent
        unsigned int signum:DELKA_SIG;  // - 1bitové znaménko
    } fp_deleny;
} t_fp32;

// funkce pro výpis vnitřní struktury FP hodnoty
void showFP(float fp)
{
    t_fp32 fp32;                        // unie s přiřazovanými FP hodnotami
    int i;                              // počitadlo smyčky
    int val;                            // FP hodnota převedená na 32bitové slovo

    fp32.fp=fp;                         // zapsat FP hodnotu do unionu
    val=fp32.uint;                      // získat slovo s FP hodnotou
    printf("%7.3f    %08x    ", fp32.fp, fp32.uint); // výpis FP hodnoty a příslušného slova
    for (i=0; i<DELKA; i++) {           // převod na řetězec bitů (do dvojkové soustavy)
        putchar(!!(val & (1<<(DELKA-1)))+'0'); // výpis hodnoty aktuálně nejvyššího bitu
        if (!i || i==DELKA_EXP) putchar(' ');  // po znaménku a za exponentem udělat mezeru
        val=val<<1;                     // posun na další (méně významný) bit
    }
    printf("   %c  %+4d  %f\n",
           "+-"[fp32.fp_deleny.signum], // zjištění znaménka podle nejvyššího bitu
           fp32.fp_deleny.exp-BIAS,     // posun exponentu o bias
           1.0+fp32.fp_deleny.mantisa/(float)(1<<DELKA_MAN)); // výpis mantisy (pouze pro normalizovaná čísla)
}

int main(void)
{
    float  values[]={+0, -0, 1.0, -1.0, 2.0, -2.0, 10, 100, 999, 0.1, 0.01};
    int    i;
    for (i=0; i<11; i++)                // výpis vnitřní struktury všech zadaných hodnot
       showFP(values[i]);
       
    for (i=0; i<11; i++) {                // výpis vnitřní struktury všech zadaných hodnot
       showFP(10 - 1*i);
       if(10 - 1 * i == 0) {
       		printf("found\n");
       }
    }
       
    return 0;
}

// finito 
