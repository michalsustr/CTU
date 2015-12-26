#include <stdio.h>
#include <stdlib.h>


/* matrix n x n containing colors on diagonal (0 (red) or 1 (blue) or -1 (not set) )
 * and edges on non-diagonal entries - 1/0 if edge exists or not */
int **matrix;
int n; /* number of nodes */
int l; /* number of edges */

int color_graph(int node) {
    int i;

    /* no color is set yet - this goes for the first iteration (node 0) */
    if(matrix[node][node] == -1) {
        matrix[node][node] = 0;
    }

    /* loop through neigbours and set color */
    for(i = node+1; i < n; i++) {
        if(matrix[node][i] == 1) {
            /* neighbour has no color defined yet, so assign complementary color */
            if(matrix[i][i] == -1) {
                matrix[i][i] = !matrix[node][node];
            } else {
                /* neighbour has a different color than expected, graph is not bicolorable */
                if(matrix[i][i] != !matrix[node][node]) {
                    return 0;
                }
            }

            /* recursive loop through all neigbours */
            if(!color_graph(i)) return 0;
        }
    }

    return 1;
}

int main(int argc, char** argv) {
    int i,j;
    int in,out;

    while(1) {
        scanf("%d", &n);
        if(n == 0) break;
        scanf("%d", &l);

        matrix = (int *) malloc(n * sizeof(int *));
        for(i = 0; i < n; i++) {
            matrix[i] = malloc(n * sizeof(int));
            for(j = 0; j < n; j++) {
                matrix[i][j] = 0;
            }
            matrix[i][i] = -1;
        }

        for(i = 0; i < l; i++) {
            scanf("%d %d", &in, &out);
            if(in > n || out > n) break;
            matrix[in][out] = 1;
            matrix[out][in] = 1;
        }

        if(color_graph(0)) {
            printf("BICOLORABLE.\n");
        } else {
            printf("NOT BICOLORABLE.\n");
        }

        for(i = 0; i < n; i++) {
            free(matrix[i]);
        }
        free(matrix);
    }

    return (EXIT_SUCCESS);
}

