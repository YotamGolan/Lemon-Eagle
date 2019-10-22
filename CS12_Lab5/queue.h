//-----------------------------------------------------------------------------
// queue.h
// Header file for the queue ADT
//-----------------------------------------------------------------------------

typedef struct NodeObj{
	int strint;
	struct NodeObj *next;
} NodeObj;
typedef NodeObj *Node;

typedef struct {
	Node head;
} QueueObj;

void print_vals(Node n, char* q);
void enqueue	(Node n, int i); 
int dequeue 	(Node *n);
