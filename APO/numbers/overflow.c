#include <stdio.h>
#include <math.h>
#define PRINT_MEM(a) print_mem((unsigned char*)&(a), sizeof(a))

void print_mem(unsigned char *ptr, int size) {
  int i;
  printf("adresa = 0x%08lx\n", (long unsigned int)ptr);

  for (i = 0; i < size; i++) {
    printf("0x%02x ", *(ptr+i));
  }

  printf("\n");
}

int main() {
  unsigned int unsig = 0;
  int sig = -pow(2,31)-1;

  printf("hodnota = %u\n", unsig);
  PRINT_MEM(unsig);

  printf("\nhodnota = %d\n", sig);
  PRINT_MEM(sig);

  return 0;
}
