#include "List.h"
#include<stdio.h>
#include<stdlib.h>
#include<string.h>

int main(int argc, char **argv)
{
	//Assign stack memory
	FILE *input;
	FILE *output;
	char line[256];
	Node head = NULL;
	//checks commandline inputs for right amount of arguments
	if(argc != 3)
		{
		printf("Usage: %s <input file> <output file>\n", argv[0]);
		exit(EXIT_FAILURE);
		}
	//opens the input file for reading
	input = fopen(argv[1],"r");
	if(input == NULL)
	{
		printf("Unable to read from file %s\n", argv[1]);
		exit(EXIT_FAILURE);
	}
	//opens the output file
	output = fopen(argv[2], "w");
	if(output==NULL)
	{
		printf("Unable to write to file %s\n", argv[2]);
		exit(EXIT_FAILURE);
	}
	
	while( fgets(line, 256, input) != NULL)
	{
		int val;
		char *QueueString = calloc(512, sizeof(char));
		//if its an e command, enqueues
		if(line[0] == 'e')
		{
			val = strtol(line+2, NULL, 10);
			if(head == NULL){
				head = malloc(sizeof(NodeObj));
				head->strint = val;
				head->next = NULL;
			}
			else{
				enqueue(head,val);
			}
			fprintf(output,"enqueued %d\n",val);
		}
		//if its d dequeues
		if(line[0] == 'd')
		{
			if(head == NULL){
				fprintf(output,"empty\n");
			}
			else{
				val = dequeue(&head);
				fprintf(output,"%d\n", val);
			}
		}
		//if its p prints everything
		if(line[0] == 'p'){
			print_vals(head,QueueString);
			fprintf(output, "%s\n",QueueString);
		}
		free(QueueString);
	}
	free(head);
}