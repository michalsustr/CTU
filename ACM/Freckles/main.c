#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <limits.h>

/* list of point coordinates */
float pointsX[101];
float pointsY[101];

/* array that indicates if given point is already in the skeleton (0/1) */
int skeleton[101];

float length;

float dist(float x1, float y1, float x2, float y2) {
    return pow(x1-x2,2)+pow(y1-y2,2);
}

int main(int argc, char** argv) {
    int i,j;
    int cases;
    int n;
    float min_length;
    int min_from, min_to;
    int do_break;
    float x,y;
    int blank_line = 0;

    scanf("%d", &cases);

    while(cases--) {
        scanf("%d", &n);
        
        for(i = 0; i < n; i++) {
            scanf("%f %f", &x,&y);
            pointsX[i] = x;
            pointsY[i] = y;
            skeleton[i] = 0;
        }

        if(n == 1) {
            printf("0.00\n\n");
            continue;
        }

        /* find the minimal skeleton */
        /* begin at node 0, stop the search when skeleton is full */
        skeleton[0] = 1;
        length = 0;
        while(1) {
            min_length = INT_MAX; /* this should be a safe value */
            min_from = -1;
            min_to = -1;
            do_break = 1;
            
            for(i = 0; i < n; i++) {
                if(skeleton[i] == 1) { /* loop through this point */
                    for(j = 0; j < n; j++) {
                        if(j != i && skeleton[j] == 0 && dist(pointsX[j],pointsY[j],pointsX[i],pointsY[i]) < min_length) {
                            min_length = dist(pointsX[j],pointsY[j],pointsX[i],pointsY[i]);
                            min_from = i; min_to = j;
                        }
                    }
                }
            }

            /* add this node to skeleton */
            skeleton[min_to] = 1;
            /* increase the length of the line */
            length += sqrt(dist(pointsX[min_from],pointsY[min_from],pointsX[min_to],pointsY[min_to]));
            /* check if we are done - skeleton is full */
            for(i = 0; i<n; i++) {
                if(skeleton[i] == 0) do_break = 0;
            }
            if(do_break == 1) break;
        }

        if(blank_line) {
            printf("\n");
        }
        blank_line = 1;
        printf("%.2f\n", length);
    }

    return (EXIT_SUCCESS);
}
