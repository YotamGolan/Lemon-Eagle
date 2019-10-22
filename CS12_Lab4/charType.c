#include<stdio.h>
#include<ctype.h>
#include<stdlib.h>
#include<string.h>
//Take in a string of Chars and sorts into
//Alphabetic
//Numeric
//Punctuactual
//Whitespactual

void extract_chars(char* s, char* a, char* d, char* p, char* w)
{
	//Set fire to the memory
	memset(a,0,256);
	memset(d,0,256);
	memset(p,0,256);
	memset(w,0,256);
	//Watch it burn
	
	//As I assign the char to the desired array
	for(int i = 0; i < strlen(s); i++)
	{
		if(isalpha(s[i]))
			a[strlen(a)] = s[i];
		if(isdigit(s[i]))
			d[strlen(d)] = s[i];
		if(ispunct(s[i]))
			p[strlen(p)] = s[i];
		if(isspace(s[i]))
			w[strlen(w)] = s[i];
	}
}
int main(int argc, char **argv)
{
	//Assign stack memory
	FILE *input;
	FILE *output;
	char line[256];
	int LineNumber = 0;
	
	//Assign heap memory
	char *alpha = calloc(256, sizeof(char));
	char *digits = calloc(256, sizeof(char));
	char *punct = calloc(256, sizeof(char));
	char *space = calloc(256, sizeof(char));
	
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
	//reads lines from input file
	while( fgets(line, 256, input) != NULL)
	{
		LineNumber++;
		extract_chars(line, alpha, digits, punct, space);
	//Prints out split chars as desired by Sesh (Amen)	
		fprintf(output, "Line %d contains:\n", LineNumber);
			
		if(strlen(alpha) == 1)
			fprintf(output, "%lu alphabetic character: %s\n", strlen(alpha), alpha);
		else
			fprintf(output, "%lu alphabetic characters: %s\n", strlen(alpha), alpha);
		if(strlen(digits) == 1)
			fprintf(output, "%lu numeric character: %s\n", strlen(digits), digits);
		else
			fprintf(output, "%lu numeric characters: %s\n", strlen(digits), digits);
		if(strlen(punct) == 1)
			fprintf(output, "%lu punctuation character: %s\n", strlen(punct), punct);
		else
			fprintf(output, "%lu punctuation characters: %s\n", strlen(punct), punct);
		if(strlen(space) == 1)
			fprintf(output, "%lu whitespace character: %s\n", strlen(space), space);
		else
			fprintf(output, "%lu whitespace characters: %s\n", strlen(space), space);

	}
		//closes both files
	fclose(input);
	fclose(output);
		//frees all assigned heap memory
	free(alpha);
	free(digits);
	free(punct);
	free(space);

}
	