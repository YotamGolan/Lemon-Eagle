#include<stdio.h>
#include<stdlib.h>
#include<string.h>
	//uses pointer manipulation to reverse string
void stringReverse(char *s)
{
	int i;
	char *end, *begin, temp;
	end = s;
	begin = s;
	//finds last location in memory of string
	for(i = 0; i < strlen(s) - 1; i++)
      end++;
	//switches current and corresponding pointers in string array, then moves inwards until they meet
	for(i = 0; i < strlen(s)/2; i++)
	{
		temp = *end;
		*end = *begin;
		*begin = temp;
		
		begin++;
		end--;
	}
}

int main(int arg, char* arga[])
{
	FILE* in; //input file handle
	FILE* out; //output file handle
	char word[1000]; //an array of chars to store words from input file
	
	//checks commandline inputs for right amount of arguments
	if(arg !=3)
		{
		printf("Usage: %s <input file> <output file>\n", arga[0]);
		exit(EXIT_FAILURE);
		}
	//opens the input file for reading
	in = fopen(arga[1],"r");
	if(in == NULL)
	{
		printf("Unable to read from file %s\n", arga[1]);
		exit(EXIT_FAILURE);
	}
	//opens the output file
	out = fopen(arga[2], "w");
	if(out==NULL)
	{
		printf("Unable to write to file %s\n", arga[2]);
		exit(EXIT_FAILURE);
	}
	//reads words from input file and prints them reversed to output file
	while( fscanf(in, " %s", word) != EOF )
	{
		stringReverse(word);
		fprintf(out, " %s\n", word);
	}
		//closes both files
	fclose(in);
	fclose(out);

	return(EXIT_SUCCESS);
}





