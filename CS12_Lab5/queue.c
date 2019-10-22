//Queue.c
//yogolan@ucsc.edu
//Implements queue.h methods for lab5

#include "queue.h"
#include<stdio.h>
#include<stdlib.h>
#include<string.h>

void print_vals(Node n, char* QueueString)
{
		while(n != NULL)
		{
			sprintf(QueueString + strlen(QueueString), "%d ", n->strint);
			n = n->next;
		}
}

int dequeue(Node *n)
{
	Node ncop = *n;
	*n = (*n)->next;
	int val = ncop->strint;
	free(ncop);
	return val;
}

void enqueue(Node n, int i)
{
	Node node = calloc(1,sizeof(NodeObj));
	node->strint = i;

	while(n->next != NULL)
		{
			n = n->next;
		}
	n->next = node;
	
}