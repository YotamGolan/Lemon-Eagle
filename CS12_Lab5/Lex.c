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
	List L = newList();	
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
	int fileLength = 0;
	while( fgets(line, 256, input) != NULL)
	{
		fileLength++;
	}
	char inputArray[filelength][160];
	int counter = 0;
	while( fgets(line, 256, input) != NULL)
	{
		inputArray[counter] = line;
		str data;
		moveFront(L);
		//intakes every word
			if(L->first == NULL){
				prepend(L,counter);
			}
			else{
				if(strcmp(line,inputArray[get(L)]) <= 0 )
				{
					insertBefore(L,counter);
				}else{
					moveNext(L);
					if(L->index == -1)
						append(L,counter);
				}
				
			}
	}
	printList(stdout,L); 
   printf("\n");
	free(inputArray);
}