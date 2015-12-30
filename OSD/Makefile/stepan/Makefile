# Makefile pro ulohu "make" predmetu ODS
#
# by Stepan Krivanec   krivaste@fel.cvut.cz

all : lib 
	gcc -o program -Wall program.c -lm -L.

lib : nsd.c nd.c
	gcc -g -fPIC -c -Wall nsd.c 
	gcc -g -fPIC -c -Wall nd.c 
	gcc -shared -Wl,-soname,libnsd.so \
    -o libnsd.so nsd.o nd.o -lc

clean :
	rm *.o
	rm *.so
	rm program





