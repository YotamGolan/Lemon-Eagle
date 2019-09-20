import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Bard {
	public static void main(String[] inputs) throws IOException {
		PrintWriter writeme = new PrintWriter(new FileWriter(inputs[1]));
		// Checks to make sure 2 arguments were given
		// Initilizes a printwriter to turn the Queen[] in a .txt file that is readable
		if (inputs.length < 2)
			System.exit(1);
		// sends the input file to the Hasher function
		// cycles through all the int[]'s return by the Hasher and solves each one.
		Hasher hashed = new Hasher();
		//writeme.print(hashed.spearHash.toString());
		for (int[] seshswish : inputfilereader(inputs[0]))
		{	
			writeme.print(hashed.findIt(seshswish[0], seshswish[1]));
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

}


