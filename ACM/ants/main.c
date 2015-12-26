/*
 * File:   ants.c
 * Author: michal
 *
 * Created on November 14, 2011, 9:13 AM
 */

#include <stdio.h>
#include <stdlib.h>

#define RIGHT 1
#define LEFT 0

typedef struct {
    int position;
    int direction;
} Ant;

/*int radenieSumou(const void *a, const void *b) {
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
*/
/* compares two integers */
int int_cmp(const void *a,const void *b)
{
    Ant *ant1 = (Ant *) a;
    Ant *ant2 = (Ant *) b;

    printf("comp %i %i\n", ant1->position, ant2->position);
  if (ant1->position == ant2->position)
    return 0;
  else
    if (ant1->position < ant2->position)
        return -1;
     else
      return 1;
}


/*
 *
 */
int main(int argc, char** argv) {
    int wood_length, ants_cnt;
    Ant ants[100];
    Ant ant;

    char dir;
    int i;
    int time;
    int stop;
    
    while(scanf("%d %d", &wood_length, &ants_cnt) != EOF) {
        
        if(wood_length == 0 && ants_cnt == 0) {
            break;
        }

        wood_length *= 2;

        /* ants  = (Ant**) malloc(ants_cnt * sizeof(Ant*)); */
        for(i = 0; i < ants_cnt; i++) {
            /* ant = (Ant *) malloc(sizeof(Ant)); */
            scanf("%d %c", &ant.position, &dir);
            ant.position *= 2;

            if(dir == 'R') {
                ant.direction = RIGHT;
            } else if (dir == 'L') {
                ant.direction = LEFT;
            } else {
                printf("Unknown direction!");
                exit(1);
            }

            ants[i] = ant;
        }

        /* sort the ants positions */
        qsort((void *) ants, ants_cnt, sizeof(ant), int_cmp);

        while(1) {
            time++;

            stop = 0;
            for(i = 0; i < ants_cnt; i++) {
                if(ant[i].position != -1) {
                    if(ant[i].direction == LEFT) {
                        ant[i].position--;
                    } else {
                        ant[i].position++;
                    }
                } else {
                    stop++;
                }
                if(ant[i].position > wood_length) {
                    ant[i].position = -1;
                }
            }

            if(stop == ants_cnt) {
                break;
            }

            // check if two ants are at the same position
            for(i = 0; i < ants_cnt; i++) {
                if(i != 0) {
                    if(ant[i-1].position == ant[i].position) {
                        ant[i-1].direction = !ant[i-1].direction;
                        ant[i].direction = !ant[i].direction;
                    }
                }
                if(i != ants_cnt-1)
                    if(ant[i+1].position == ant[i].position) {
                        ant[i+1].direction = !ant[i+1].direction;
                        ant[i].direction = !ant[i].direction;
                    }
                }
            }
        }

    printf("time %i", time);

        for(i = 0; i<ants_cnt; i++) {
            printf("mravec %i: %i %i", i, ants[i].position, ants[i].direction);
        }


    }

    return (EXIT_SUCCESS);
}

