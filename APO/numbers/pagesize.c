#include <sys/mman.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <byteswap.h>
#include <inttypes.h>
#include <sys/types.h>
#include <dirent.h>

int main() {
	printf("hodnota: %d", getpagesize());
	
	return 0;
}

