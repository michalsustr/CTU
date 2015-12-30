#include <stdio.h>
#include <stdlib.h>
#include "nd.c"
#include "nsd.c" 

int main(){
	int prvni,druhy;
	if(scanf("%d %d",&prvni, &druhy)!=2){
		fprintf(stderr,"input error, please input two integers\n");
		return 1;
	}
	if((nd(prvni)==1)&&(nd(druhy)==1)){
	printf("prvocisla\n");
	return 0;
	}
	printf("%d\n",nsd(prvni,druhy));
	return 0;
}

