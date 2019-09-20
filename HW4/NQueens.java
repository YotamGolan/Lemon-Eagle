//-----------------------------------------------------------------------------
//Yotam Golan, ID:yogolan
//NQueens is the comprehensive file reader, file writer and recurisive solver function for the NQueen problem
//It takes in the .txt that that is the first argument and outputs a .txt that is the secound argument
//File ran by using Makefile and then running the created .jar
//-----------------------------------------------------------------------------
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class NQueens {

	public static void main(String[] inputs) throws IOException {
		PrintWriter writeme = new PrintWriter(new FileWriter(inputs[1]));
		// Checks to make sure 2 arguments were given
		// Initilizes a printwriter to turn the Queen[] in a .txt file that is readable
		if (inputs.length < 2)
			System.exit(1);
		// sends the input file to the filereader function
		// cycles through all the int[]'s return by the filereader and solves each one.
		for (int[] seshswish : inputfilereader(inputs[0])){
				Queen[] seshstruth = new Queen[seshswish.length/2];
			for(int i = 0; i < seshstruth.length; i ++)
			{
				seshstruth[i] = new Queen(seshswish[i*2+2],seshswish[i*2+1]);
			}
			//creates a solution object, then tries to solve it given given conditions
			NQueensSolution solution = new NQueensSolution(seshswish[0], seshstruth);
            Queen[] FinalSolution = solution.solver();
			
			//prints out final answer depending on results from solver
			if (FinalSolution == null)
				writeme.print("No solution");
			else
				for(Queen Corgis : FinalSolution)
				{
					//I like corgis
					writeme.print(Corgis.col + " ");
					writeme.print(Corgis.row + " ");
				}
				writeme.println();
					
		}
		writeme.close();
	}
	
		// creates the Queen[] that will hold the board solution 
		// adds the initial queen to the array, moving it -1 -1 due to my code working form index 0+
		// calls the recursive function
	
	public static int[][] inputfilereader(String input) throws FileNotFoundException {
		// Intakes the input .txt file
		// Creates an arraylist of arrays to hold input
		Scanner in = new Scanner(new File(input));
		ArrayList<int[]> InLines = new ArrayList<int[]>();

		// converts input lines into int arrays using tokenization
		// adds each int[] to the arraylist 
		while (in.hasNextLine()) {
			String lines = in.nextLine().trim();
			String[] tokens = lines.split("\\s+");

			int[] tokenInts = new int[tokens.length];
			for (int i = 0; i < tokens.length; i++)
				tokenInts[i] = Integer.parseInt(tokens[i]);
			InLines.add(tokenInts);

		}
		in.close();
		
		// converts the arraylist of int[] to a int[][] because otherwise my code doesnt work and making the entire function use a arraylist would 
		// be better code probably but i am lazy
		return InLines.toArray(new int[InLines.size()][]);

	}

}