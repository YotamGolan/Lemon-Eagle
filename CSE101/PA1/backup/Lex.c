#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include "List.h"

int main(int argc, char **argv)
{
	//checks commandline inputs for right amount of arguments
	if(argc != 3)
		{
		printf("Usage: %s <input file> <output file>\n", argv[0]);
		exit(EXIT_FAILURE);
		}
	//Assign stack memory
	FILE *input = fopen(argv[1],"r+");
	FILE *output = fopen(argv[2], "w+");
	char line[256];
	int fileLength = 0;
	int x;
	List L = newList();	
	int counter = 0;

	//opens the input file for reading
	//fprintf(output,"%s\n", "Opened");
	if(input == NULL)
	{
		printf("Unable to read from file %s\n", argv[1]);
		exit(EXIT_FAILURE);
	}
	//opens the output file
	//fprintf(output,"%s\n", "Opened");
	if(output==NULL)
	{
		printf("Unable to write to file %s\n", argv[2]);
		exit(EXIT_FAILURE);
	}

	for(x = getc(input); x != EOF; x = getc(input))
	{
		if(x == '\n')
		fileLength++;
		//fprintf(output,"%d\n",x);
	}
	char inputArray[fileLength][160];
	//fprintf(output,"1\n");
	//fprintf(output,"%d\n",fileLength);	

	if(input != NULL)
	rewind(input);

 	while( fgets(line, 256, input) != NULL)
	{
		
		//fprintf(output,"%d\n", counter);
		strcpy(inputArray[counter], line);
		//intakes every word
			if(length(L) <= 0){
				prepend(L,counter);
				moveFront(L);
				//fprintf(output,"%s","first");	
			}
			else
			{
				for(moveFront(L); index(L)>=0; moveNext(L))
				{	
						if(strcmp(line,inputArray[get(L)]) <= 0 )
						{
							insertBefore(L,counter);
							//fprintf(output,"%s",line);
							//fprintf(output,"%s",inputArray[get(L)]);
							break;
							//fprintf(output,"%s","before");
												
						}					
				}
				if(index(L) == -1)
				{
					append(L,counter);
					//fprintf(output,"%s","appended");
					//fprintf(output,"%d",get(L));		
						
				}
		}
			counter++;
	}
	//if(1)
	//return 0;
	//fprintf(output,"%d",counter);
	for(moveFront(L); index(L)>=0; moveNext(L))
	{	
//
		fprintf(output,"%s", inputArray[get(L)]);
		//fprintf(output,"%d",get(L));
	}
	fclose(input);
	fclose(output);
	//free(inputArray);
}