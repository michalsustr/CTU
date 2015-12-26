#include <stdio.h>
#include <stdlib.h>

int main(int argc, char** argv) {
    int i,j;
    int caseNum = 0;
    int in,out,price;
    int max_price, max_from, max_to;
    int min_price;

    /* matrix n x n containing on diagonal status (0/1 - the node has (not) been already visited)
     * and capacity of routes (edges) on non-diagonal entries - 0 if there is no edge between 2 nodes */
    int matrix[100][100];
    /* matrix 1 x n listing the previous edge with the greatest capacity
     */
    int prev_nodes[100];

    int n,r; /* number of nodes and edges */
    int start, destination, tourists;

    while(1) {
        scanf("%d %d", &n, &r);
        if(n == 0 && r ==0) break;
       
        for(i = 0; i < n; i++) {
            prev_nodes[i]  = -1;
            for(j = 0; j < n; j++) {
                matrix[i][j] = 0;
            }
        }
        
        for(i = 0; i < r; i++) {
            scanf("%d %d %d", &in, &out, &price);
            in--; out--;
            if(in > n || out > n) break;
            matrix[in][out] = matrix[out][in] = price - 1;
        }
        scanf("%d %d %d", &start, &destination, &tourists);
        start--; destination--;

        printf("Scenario #%d\n", ++caseNum);
        if(start == destination) {
            printf("Minimum Number of Trips = 1\n\n");
            continue;
        }
        /* find maximal skeleton */
        /* begin at starting node s, stop the search when destination d is reached */
        matrix[start][start] = 1;

        while(1) {
            max_price = 0;
            max_from = -1;
            max_to = -1;
            for(i = 0; i < n; i++) {
                if(matrix[i][i] == 1) { /* loop through this row */
                    for(j = 0; j < n; j++) {
                        if(j != i && matrix[i][j] > max_price && matrix[j][j] == 0) {
                            max_price = matrix[i][j];
                            max_from = i; max_to = j;
                        }
                    }
                }
            }
            /* add this node to skeleton */
            matrix[max_to][max_to] = 1;
            prev_nodes[max_to] = max_from;
            if(max_to == destination) break;
        }

        /* go from destination till the start */
        i = destination;
        min_price = matrix[destination][prev_nodes[destination]];
        while(1) {
            if(i == start) break;
            /* find the lowest capacity */
            if(matrix[i][prev_nodes[i]] < min_price) {
                min_price = matrix[i][prev_nodes[i]];
            }
            i = prev_nodes[i];
        }
        printf("Minimum Number of Trips = %d\n\n",
           (tourists/(min_price)+(  (tourists % (min_price)) == 0 ? 0 : 1  )));
    }

    return (EXIT_SUCCESS);
}
