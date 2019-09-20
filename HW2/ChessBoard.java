import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public  class ChessBoard {
	static String color;
	public static void main(String[] inputs) throws IOException
	{
		//ensures proper amount of arguments given
		// opens a filewriter at the desired txt file location
		if (inputs.length != 2)
			System.exit(1);
		PrintWriter writeme = new PrintWriter(new FileWriter(inputs[1]));
		//reads the input file and tokenizes it before running thorugh each array of strings returned
		for (String[] seshswish : inputfilereader(inputs[0]))
		{
			seshswish[1] = seshswish[1].substring(0, 1);
			LinkedList List = new LinkedList();
			//Factories are too easy, real programmers use huge lines of if-else code that repeats a large amount of identical code
			for(int i = 3; i < seshswish.length; i++)
			{
				//checks the given letter and creates the piece suitable for it as well as assigning it the correct color based on capitilization
				//using a function to check for uppercase is to easy, real programmers use unneccesary .equal calls
				if(i%3 == 0)
				{
					int j = i-1;
					if(seshswish[j].toUpperCase().equals(seshswish[j]))
					{
						color = "black";
					}		
					else
					{
						color = "white";	
					}
					//creates the pieces as needed
					if(seshswish[j].toUpperCase().equals("K")) {
						List.insert(new King(Integer.parseInt(seshswish[j+2]),Integer.parseInt(seshswish[j+1]), color));
					}else if(seshswish[j].toUpperCase().equals("Q")) {
						List.insert(new Queen(Integer.parseInt(seshswish[j+2]),Integer.parseInt(seshswish[j+1]), color));
					}else if(seshswish[j].toUpperCase().equals("N")) {
						List.insert(new Knight(Integer.parseInt(seshswish[j+2]),Integer.parseInt(seshswish[j+1]), color));
					}else if(seshswish[j].toUpperCase().equals("B")) {
						List.insert(new Bishop(Integer.parseInt(seshswish[j+2]),Integer.parseInt(seshswish[j+1]), color));
					}else if(seshswish[j].toUpperCase().equals("R")) {
						List.insert(new Rook(Integer.parseInt(seshswish[j+2]),Integer.parseInt(seshswish[j+1]), color));
					}else if(seshswish[j].toUpperCase().equals("P")) {
						List.insert(new Pawn(Integer.parseInt(seshswish[j+2]),Integer.parseInt(seshswish[j+1]), color));
					}else
						continue;
				}
			}
			//for each line given, calls the actual solver method
			writeme.println(solver(List,Integer.parseInt(seshswish[0]),Integer.parseInt(seshswish[1]) ));
		}


		writeme.close();
	}
	//solves the everything
	public static String solver(LinkedList inputlist, int col, int row)
	{
		String returnme = null;
		node head = inputlist.head;
		node next = head.nextpiece;
		//runs though the list and ensures no two nodes have the same coordinates and declares invalid if they do
		while(head != null )
		{
			while(next != null)
			{

				if(head.piece.row == next.piece.row && head.piece.col == next.piece.col)
					return "Invalid";
				next = next.nextpiece;
			}
			head = head.nextpiece;
			if(head != null)
			next = head.nextpiece;
		}
		//finds the asked for piece, and finds its color, preps the return string with it
		if(inputlist.find(row, col) != null)
		{ 
			if(inputlist.find(row, col).piece.returncolor() == "white")
				returnme = (inputlist.find(row, col).piece.returnclass() + " ");
			if(inputlist.find(row, col).piece.returncolor() == "black")
				returnme = (inputlist.find(row, col).piece.returnclass().toUpperCase() + " ");
		}

		head = inputlist.head;
		//determinse if the desired piece is attacking anything else on the board
		if(inputlist.find(row, col) != null)
			while(head != null )
			{
				if(inputlist.find(row, col).piece.isAttacking(head.piece))

				{
					return returnme += "y";
				}

				head = head.nextpiece;
			}
		if(returnme != null)
			return returnme += "n";

		return "-";
	}

	public static String[][] inputfilereader(String input) throws FileNotFoundException
	{
		// Intakes the input .txt file
		// Creates an arraylist of arrays to hold inputs
		Scanner in = new Scanner(new File(input));
		ArrayList<String[]> InLines = new ArrayList<String[]>();

		// converts input lines into String arrays using tokenization
		// adds each String[] to the arraylist 
		while (in.hasNextLine()) {
			String lines = in.nextLine().trim();
			String[] tokens = lines.split("\\s+");
			InLines.add(tokens);

		}
		in.close();

		// converts the arraylist of String[] to a String[][] because otherwise my code doesnt work and making the entire function use a arraylist would 
		// be better code probably but i am lazy
		return InLines.toArray(new String[InLines.size()][]);

	}


}

