#include<stdlib.h>
#include<stdio.h>
#define FIT 1
#define NOFIT 0
#define MAX_BOX 30
#define MAX_DIM 10

int boxes_cnt;
int box_dim;

/* compares two integers */
int int_cmp(const int *a,const int *b)
{
  if (*a==*b)
    return 0;
  else
    if (*a < *b)
        return -1;
     else
      return 1;
}

int box_can_fit(const int *a, const int *b) {
    int i;

    for(i = 0; i < box_dim; i++) {
        if(a[i] <= b[i]) return NOFIT;
    }
    return FIT;
}



int main(void) {
    /* array of boxes_cnt * box_dim */
    int boxes[MAX_BOX][MAX_DIM];

    /* array of boxes_cnt * boxes_cnt
     *   it is a neighbours matrix, FIT in column indicates that
     *   given column fits into given row */
    int neighbours[MAX_BOX][MAX_BOX];
    
    /* array of boxes_cnt, box_index=>entering_edges */
    int entering_edges[MAX_BOX];

    /* array of boxes_cnt, order=>box_index */
    int topol_order[MAX_BOX];

    /* array of boxes_cnt, box_index=>the longest route that can be done to come
     *  to this node (box) */
    int longest_routes[MAX_BOX];

    /* array of boxes_cnt, box_index=>entering box_index */
    int longest_from[MAX_BOX];

    /* the final path we are looking for */
    int longest_path[MAX_BOX];
    int length;

    int i,j,k;
    int max, current_box;

    /* LOAD DATA */
    while(scanf("%d %d", &boxes_cnt, &box_dim) != EOF) {
        for(i = 0; i < boxes_cnt; i++) {
            entering_edges[i] = 0;
            topol_order[i]    = -1; /* no box is defined yet */
            longest_routes[i] = 0;
            longest_from[i]   = -1;

            for(j = 0; j < box_dim; j++) {
                scanf("%d", &boxes[i][j]);
            }
        }

        /* sort all the boxes dims */
        for(i = 0; i < boxes_cnt; i++) {
            qsort(boxes[i], box_dim, sizeof(int), int_cmp);
        }

        /* for each box look if other boxes can be inserted into it
           resulting matrix corresponds to col can be inserted into row */
        for(i = 0; i < boxes_cnt; i++) {
            for(j = 0; j < boxes_cnt; j++) {
                if(i == j) {
                    neighbours[i][j] = NOFIT;
                } else {
                    neighbours[i][j] = box_can_fit(boxes[i], boxes[j]);
                    if(neighbours[i][j] == FIT) {
                        entering_edges[j]++;
                    }
                }
            }
        }

        /* create topological ordering */
        i = -1; /* -1 so the first i++ goes to 0 */
        j = boxes_cnt-1; /* position in topol_orderp */
        while(1) {
            i++;

            if(i >= boxes_cnt) { /* we already looped once through the array, go again */
                i = 0;
            }

            /* for entering_edges, -1 means we are already done with this node */
            if(entering_edges[i] == -1) continue; /* skip this node */

            if(entering_edges[i] == 0) {
                topol_order[j] = i;
                j--;
                entering_edges[i] = -1; /* set to -1 so it can be skipped */
                /* decrease all neighbours */
                for(k = 0; k < boxes_cnt; k++) {
                    if(neighbours[i][k] == FIT) {
                        entering_edges[k]--;
                    }
                }

                if(j == -1) { /* we numbered all boxes */
                    break;
                }
            }
        }

        /* proceed in order of topological ordering */
        for(i = 0; i < boxes_cnt; i++) {
            current_box = topol_order[i];
            /* loop all neighbours, and increase the longest path if possible */
            for(j = 0; j < boxes_cnt; j++) {
                if(neighbours[j][current_box] == FIT) {
                    if(longest_routes[current_box]+1 > longest_routes[j]) {
                        longest_routes[j] = longest_routes[current_box]+1;
                        longest_from[j] = current_box;
                    }
                }
            }
        }

        /* find the box with greatest longest_routep */
        max = 0;
        current_box = -1;
        for(i = 0; i < boxes_cnt; i++) {
            if(longest_routes[i] > max) {
                max = longest_routes[i];
                current_box = i;
            }
        }

        /* and trace it all the way back */
        if(current_box == -1) { /* no nesting is possible */
            printf("1\n1\n");
        } else {
            longest_path[0] = current_box;
            length = 1;
            while(1) {
                if(longest_from[current_box] != -1) {
                    current_box = longest_from[current_box];
                    longest_path[length] = current_box;
                    length++;
                } else {
                    break;
                }
            }

            /* print out the results */
            printf("%i\n", length);
            for(i = length-1;  i >= 0; i--) {
                printf("%i ", longest_path[i]+1);
            }
            printf("\n");
        }
    }
    return EXIT_SUCCESS;
}

