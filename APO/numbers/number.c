int main() {
	int a = -200;
	printf("hodnota: %u = %d = %f = %c \n", a, a, *((float*)(&a)), a);
	
	return 0;
}

