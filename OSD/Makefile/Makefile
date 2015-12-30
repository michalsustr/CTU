LIB_SOURCES=nsd.c nd.c
LIB_OBJ=$(LIB_SOURCES:%.c=%.o)
EXECUTABLE=app

all: lib app

lib:
	gcc -fPIC -g -c -Wall $(LIB_SOURCES)
	gcc -Wall -shared -Wl,-soname,libnsd.so -o libnsd.so nsd.o nd.o
	
app: app.c
	gcc -Wall -o $(EXECUTABLE) app.c -L. -lnsd -lm
	
clean:
	rm -f *.o
	rm -f $(EXECUTABLE)
	rm -f nsd libnsd.so

