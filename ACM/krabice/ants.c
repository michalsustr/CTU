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

/*
 * 
 */
int main(int argc, char** argv) {
    int wood_length, ants_cnt;
    int *ants_positionsp;
    int *ants_directionsp;

    char dir;
    int i;

    while(1) {
        scanf("%d %d", &wood_length, &ants_cnt);
        if(wood_length == 0 && ants_cnt == 0) {
            break;
        }

        ants_positionsp  = malloc(ants_cnt * sizeof(int));
        ants_directionsp = malloc(ants_cnt * sizeof(int));
        for(i = 0; i < ants_cnt; i++) {
            scanf("%d %c", &ants_positionsp[i], &dir);
            if(dir == 'R') {
                ants_directionsp[i] = RIGHT;
            } else if (dir == 'L') {
                ants_directionsp[i] = LEFT;
            } else {
                printf("Unknown direction!");
                exit(1);
            }
        }

    }

    return (EXIT_SUCCESS);
}

