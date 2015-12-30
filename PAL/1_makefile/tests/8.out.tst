all: hello
hello: main.o hello.o
	g++ main.o hello.o -o hello
main.o: main.cpp functions.h
	g++ -c main.cpp
hello.o: hello.cpp functions.h
	g++ -c hello.cpp
#hello2.o: hello2.cpp functions.h
#	g++ -c hello2.cpp
hello.cpp:
	echo '#include ' > hello.cpp
	echo '#include "functions.h"' >> hello.cpp
	echo 'void print_hello(void){ cout << "Hello World!"; }' >> hello.cpp
main.cpp:
	echo '#include ' > main.cpp
	echo '#include "functions.h"' >> main.cpp
	echo 'int main() { print_hello();' >> main.cpp
	echo 'cout << endl; return 0; } ' >> main.cpp
#hello2.cpp:
#	echo '#include ' > hello2.cpp
#	echo '#include "functions.h"' >> hello2.cpp
#	echo 'void print_hello(void){ cout << "Hello All!"; }' >> hello2.cpp
functions.h:
	echo 'void print_hello(void);' > functions.h
