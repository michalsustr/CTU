#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define MAX(x,y) x > y ? x : y

struct crane {
    int x;
    int y;
    int r;
} cr [15];

int n;

/* enabled is integer whose bits indicate whether given circle is enabled or not */
int is_intersection(int p) {
    int i,j,dist;
    for ( i = 0; i < n; i++ ) {
		if ( (1 << i) & p ) {	/* i selected */
			for ( j = i + 1; j < n; j++ ) {
				if ((1 << j) & p ) {	/* j selected */
					dist = pow(cr[i].x - cr[j].x,2) + pow(cr [i].y - cr [j].y,2);
					if ( dist <= pow(cr [i].r + cr [j].r, 2) ) return 1;
				}
			}
		}
	}

	return 0;
}

int calc_area(int enabled) {
    int area = 0;
    int i;

    for(i = 0; i < n; i++) {
        if(enabled & (1 << i)) { /* i-th circle is enabled */
            area += pow(cr[i].r,2);
        }
    }
    return area;
}

int main(int argc, char** argv) {
    int i;
    int cases;
    int max_area;

    scanf("%d", &cases);

    while(cases--) {
        scanf("%d", &n);

        for(i = 0; i < n; i++) {
            scanf("%d %d %d", &cr[i].x,&cr[i].y,&cr[i].r);
        }
        
        max_area = 0;
        /* iterate through all posibilities, 2^n */
        for(i = 1; i < (1 << n); i++) {
            if(!is_intersection(i)) {
                  max_area = MAX(max_area, calc_area(i));
            }
        }
        printf("%d\n", max_area);
    }
    return (EXIT_SUCCESS);
}

