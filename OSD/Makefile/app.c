#include <stdio.h>
#include "nd.h"
#include "nsd.h"

int main() {
	int a,b;
	
	if(scanf("%d %d", &a, &b) != 2) {
		fprintf(stderr, "Chybny vstup, zadaj dve cisla\n");
		return 1;
	}
	
	if(nd(a) == 1 && nd(b) == 1) {
		printf("prvocisla\n");
		return 0;
	}
	
	printf("%d\n", nsd(a,b));
	
	return 0;
}

