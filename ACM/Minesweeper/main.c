#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

int main(int argc, char** argv) {

    int field[102][102]; /* matrix representing number of neighbouring mines, negative number = mine*/
    int n,m; /* n = rows, m = cols */
    int i,j;
    int caseNum = 0;
    char s[101]; /* maximum size of the field is 100x100 */
    char ch;

    while(1) {
        scanf("%d %d", &n, &m);
        if(n == 0 && m == 0) break;

        /* initialize field */
        for(i = 0; i <= n+1; i++) {
            for(j = 0; j <= m+1; j++) {
                field[i][j] = 0;
            }
        }


        for(i = 1; i <= n; i++) {
            scanf("%s\n", s); /* read one line */
            for(j = 1; j <= m; j++) {
                ch = s[j-1];
                /* we found a mine, so increase all fields around */
                if(ch == '*') {
                    /* this is safe because the field size is max 100x100 */
                    /* -9 would be fine as well - only 8 neighbours are max */
                    field[i][j] = INT_MIN;

                    /* upper neighbours */
                    field[i-1][j-1]++;
                    field[i-1][j]++;
                    field[i-1][j+1]++;
                    /* lower neighbours*/
                    field[i+1][j-1]++;
                    field[i+1][j]++;
                    field[i+1][j+1]++;
                    /* same level neighbours*/
                    field[i][j-1]++;
                    field[i][j+1]++;
                }
            }
        }

        if(caseNum > 0) {
            printf("\n");
        }

        printf("Field #%i:\n", ++caseNum);

        for(i = 1; i<=n; i++) {
            for(j = 1; j<=m; j++){
                if(field[i][j] < 0) {
                    printf("*");
                } else {
                    printf("%i", field[i][j]);
                }
            }
            printf("\n");
        }
    }

    return (EXIT_SUCCESS);
}

