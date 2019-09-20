//-----------------------------------------------------------------------------
// Yotam Golan, ID:yogolan
//NQueens is the comprehensive file reader, file writer and recurisive solver function for the NQueen problem
//It takes in the .txt that that is the first argument and outputs a .txt that is the secound argument
//File ran by using Makefile and then running the created .jar
//-----------------------------------------------------------------------------
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class NQueens {
	public static void main(String[] inputs) throws IOException {
		// Checks to make sure 2 arguments were given
		// Initilizes a printwriter to turn the Queen[] in a .txt file that is readable
		if (inputs.length < 2)
			System.exit(1);
		PrintWriter writeme = new PrintWriter(new FileWriter(inputs[1]));
		// sends the input file to the filereader function
		// cycles through all the int[]'s return by the filereader and solves each one.
		// calls the recursive function "SolveMeFather" and saves its output as the solution for that particular int[]
		for (int[] seshswish : inputfilereader(inputs[0])) {
			Queen[] seshstruth = SolveMeFather(seshswish[0], seshswish[2], seshswish[1]);
			// Checks if a solution was returned, if not then prints no solution
			if (seshstruth == null)
				writeme.print("No solution");
			// if solution exists, prints out all the Queen Bees
			// prints null should my code catch fire and shove null into the queen indexes
			// prints each queens coordinates then starts a new line
			else
				for (Queen bee : seshstruth)
					if (bee != null) {
						writeme.print(bee.col +1 + " ");
						writeme.print(bee.row +1 + " ");
					}
					else
						writeme.print("null");
			writeme.print("\n");
		}
		writeme.close();
	}

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
	
		// creates the Queen[] that will hold the board solution 
		// adds the initial queen to the array, moving it -1 -1 due to my code working form index 0+
		// calls the recursive function
	public static Queen[] SolveMeFather(int Board, int row, int col) {
		Queen[] Palace = new Queen[Board];
		Palace[col-1] = new Queen(row - 1, col - 1);
		return FillerUp(Palace, 0, col-1);

	}
		// the actual solving part of the code
		// takes in the existing board state, the column and the column of the initial queen
	public static Queen[] FillerUp(Queen[] AlsoAPalace, int k, int inputcol) {
		int a = AlsoAPalace.length;
		int row;
		// base case, returns the Queen[] when the column leaves the board
		if (k == a )
			return AlsoAPalace;
		// skips the column of the initial queen
		else if (k == inputcol)
		{
			return FillerUp(AlsoAPalace, k + 1, inputcol);
		} 
		// runs through the row vertically, for each space checking if it is possible to place a queen there by comparing to every other queen in the array
		// if it is possible, it adds the queen to the Queen[] and calls the recursive function again
		// if the function returns a null it will delete the queen from the Queen[] and continue
		// if the call doesnt return a null, then the code worked and a solution was found, and it will bubble it upwards
		// if the for loop ends with nothing returned, it will return a null as there was also no solution
		else 
		{
			for (row = 0; row < a; row++) 
			{
				Queen Bee = new Queen(row, k);
				boolean possible = true;
				for (Queen q : AlsoAPalace) 
				{
					if (q != null)
						if (q.isAttacking(Bee))
							possible = false;
				}
				if (possible) 
				{
					AlsoAPalace[k] = Bee;
					Queen[] something = FillerUp(AlsoAPalace, k + 1, inputcol);
					if (something == null)
					{
						AlsoAPalace[k] = null;
						continue;
					}
					else
						return something;

				}
				
				
			}
			return null;
		}

	}

}