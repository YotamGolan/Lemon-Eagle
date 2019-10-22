//-----------------------------------------------------------------------------
// Yotam Golan, ID:yogolan
//FileReverse is the sole file in this project
//It takes in input .txt files and outputs a named file that is a reverse of it
//File ran by using specified Makefile and then running the created .jar
//-----------------------------------------------------------------------------
import java.io.*;
import java.util.Scanner;
class FileReverse
{
public static void main(String[] inputs) throws IOException
{
	//Checks to make sure 2 arguments were given
int LineNumber = 0;
if(inputs.length < 2)
         System.exit(1);
		 
	//Intakes the input .txt file and creates output .txt file
Scanner in = new Scanner(new File(inputs[0]));
PrintWriter out = new PrintWriter(new FileWriter(inputs[1]));

	//Tokens and trims each line, then uses a StringBuilder to reverse all characters within that string token
while(in.hasNextLine())
	{
	LineNumber++;
	 
	 String lines = in.nextLine().trim()+" ";
	 String[] tokens = lines.split("\\s+");  

	 for(int i=0; i<tokens.length; i++)
	 {
            out.println(new StringBuilder(tokens[i]).reverse().toString());
	 }
	}
	in.close();
	out.close();
}
}

