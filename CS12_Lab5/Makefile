FLAGS = -std=c99 -Wall
SOURCES = queueClient.c queue.c
EXEBIN = queueClient

all: $(EXEBIN)

$(EXEBIN) : OBJ
	gcc -o $(EXEBIN) *.o
OBJ:
	gcc -c $(FLAGS) $(SOURCES)
clean :
	rm -f $(EXEBIN) *.o
check :
	valgrind --leak-check=full ./queueClient test-input.txt o.txt