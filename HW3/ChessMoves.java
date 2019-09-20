import java.io.*;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Point;


public  class ChessMoves {
	static String color;
	public static void main(String[] inputs) throws IOException
	{
		int passthrough = 0;
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
			for(int i = 0; i < seshswish.length; i++)
			{
				//checks the given letter and creates the piece suitable for it as well as assigning it the correct color based on capitilization
				//using a function to check for uppercase is to easy, real programmers use unneccesary .equal calls
				if(seshswish[i].equals(":"))
				{
					passthrough = i;	
				}
				if (seshswish[i].matches("[a-zA-z]{1}"))
				{
					int j = i;
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
						//writeme.println("k" +""+ Integer.parseInt(seshswish[j+2]) +""+ Integer.parseInt(seshswish[j+1]) + color);
						List.insert(new King(Integer.parseInt(seshswish[j+2]),Integer.parseInt(seshswish[j+1]), color));
					}else if(seshswish[j].toUpperCase().equals("Q")) {
						//writeme.println("q" +""+ Integer.parseInt(seshswish[j+2]) +""+ Integer.parseInt(seshswish[j+1]) + color);
						List.insert(new Queen(Integer.parseInt(seshswish[j+2]),Integer.parseInt(seshswish[j+1]), color));
					}else if(seshswish[j].toUpperCase().equals("N")) {
						List.insert(new Knight(Integer.parseInt(seshswish[j+2]),Integer.parseInt(seshswish[j+1]), color));
					}else if(seshswish[j].toUpperCase().equals("B")) {
						//writeme.println("b" +""+ Integer.parseInt(seshswish[j+2]) +""+ Integer.parseInt(seshswish[j+1]) + color);
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

			writeme.println(solver(List,passthrough, seshswish));
		}


		writeme.close();
	}
	//solves the everything
	public static String solver(LinkedList inputlist, int ipos, String[] line)
	{
		int lastcolor = 0;
		int k = 0;
		//Happy big for loop that breaks every command segment (4 number pieces) and decides if they are a valid command
		for(int i = ipos + 1; i < line.length; i++)
		{	
			if(k%4 == 0)
			{

				int j = i;
				node found = inputlist.find(Integer.parseInt(line[j+1]),Integer.parseInt(line[j]));		
				node maybefound = inputlist.find(Integer.parseInt(line[j+3]),Integer.parseInt(line[j+2]));		
				if(found == null)
					//emptyIllegal
					return (line[j] + " " + line[j+1] + " " + line[j+2] + " " + line[j+3] + " " + "illegal");

				if(maybefound != null)
					if(found.piece.returncolor().equals(maybefound.piece.returncolor()) && found != maybefound)
						//colorIllegal
						return (line[j] + " " + line[j+1] + " " + line[j+2] + " " + line[j+3] + " " + "illegal");

				if(canMove(inputlist,found.piece,Integer.parseInt(line[j+2]),Integer.parseInt(line[j+3])))
				{
					found.piece.setCol(Integer.parseInt(line[j+2]));
					found.piece.setRow(Integer.parseInt(line[j+3]));
					if(found.piece.returncolor().equals("white"))
					{
						if(lastcolor == 1)
							//turnllegal
							return (line[j] + " " + line[j+1] + " " + line[j+2] + " " + line[j+3] + " " + "illegal");
						lastcolor = 1;
					}
					if(found.piece.returncolor().equals("black"))
					{
						if(lastcolor == 0)
							//turnllegal
							return (line[j] + " " + line[j+1] + " " + line[j+2] + " " + line[j+3] + " " + "illegal");
						lastcolor = 0;

					}
					if(kingCheck(inputlist,lastcolor))
					{
						//king illegal
						return (line[j] + " " + line[j+1] + " " + line[j+2] + " " + line[j+3] + " " + "illegal");
					}
						
					// 2 3 3 3
				}
				else
				{
					//unmovableillegal
					return (line[j] + " " + line[j+1] + " " + line[j+2] + " " + line[j+3] + " " + "illegal");
				}
				if(found == maybefound)
				{
					//not moved illegal
					return (line[j] + " " + line[j+1] + " " + line[j+2] + " " + line[j+3] + " " + "illegal");
				}
			}

			k++;
		}


		return "legal" ; 
	}
//determines if the piece can move to where it is desired
	public static boolean canMove(LinkedList inputlist, Chesspiece piece, int gocol, int gorow)
	{
		node dest = inputlist.find(gorow, gocol);
		Pawn Frank = new Pawn(gorow,gocol,"Fake Color");

		//if not moving, always true
		if(piece.col == gocol && piece.row == gorow)
			return true;
		//Pawn special handling because it moves weird
		if(dest != null)
		{
			if(piece.returnclass().equals("p"))
			{
					if(piece.isAttacking(dest.piece))
					{
						inputlist.delete(dest);
						return true;
					}						
					return false;
			}
		}
		else
		{
			if(piece.returnclass().equals("p"))
			{
				if(piece.returncolor().equals("black"))
				{
				return (gorow - piece.row == -1);				
				}
				else
				{
				return (gorow - piece.row == 1);				
				}
			}
		}
		//uses the attack function with an imaginary piece at target space to determine if its possible to move
		if(piece.isAttacking(Frank))
		{
			if(dest != null)
			{
				if(piece.isAttacking(dest.piece))
					if(!isBlocked(inputlist,piece,gocol,gorow))
					{
						inputlist.delete(dest);
						return true;
					}

			}
			else
				return !isBlocked(inputlist,piece,gocol,gorow);
		}			
		return false;
	}
	//generates a 2d point vector and incremements by it to determine if anything is in the way
	public static boolean isBlocked(LinkedList inputlist, Chesspiece piece, int gocol, int gorow)
	{
		//knights are unblockable
		if(piece.returnclass().equals("n"))
			return false;
		//POINT!
		Point start = new Point(piece.col, piece.row);
		Point dest = new Point(gocol, gorow);
		Point dir = new Point((int)Math.signum(dest.x-start.x), (int)Math.signum(dest.y-start.y));
		Point cur = new Point(start.x+dir.x, start.y+dir.y);
		while(!cur.equals(dest))
		{
			//Something here?
			if(inputlist.find(cur.y,cur.x) != null)
			{
				//something in between
				return true;
			}
			cur.x = cur.x + dir.x;
			cur.y = cur.y + dir.y;

		}
		//nothing in between
		return false;
	}
	//reads and processes input file
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
			String process = lines.replaceAll(":"," :");
			String[] tokens = process.split("\\s+");
			InLines.add(tokens);

		}
		in.close();

		// converts the arraylist of String[] to a String[][] because otherwise my code doesnt work and making the entire function use a arraylist would 
		// be better code probably but i am lazy
		return InLines.toArray(new String[InLines.size()][]);

	}
	
	//checks if the king is in check every turn by color
	public static boolean kingCheck(LinkedList List, int color)
	{
		String strcolor;
		boolean found = false;
		node kingnode = List.head;
		node currnode = List.head;
		if(color == 1)
			strcolor = "white";
		else
			strcolor = "black";
		//finds the desired king piece in the list
		while(kingnode != null)
		{
			if(kingnode.piece.returnclass().equals("k") && kingnode.piece.returncolor().equals(strcolor))
			{
				found = true;
			break;
			}
			kingnode = kingnode.nextpiece;
		}
		//determines if the king has been checked by the move
		if(found)
			while(currnode != null)
			{
				if(currnode != kingnode && canMove(List, currnode.piece, kingnode.piece.col, kingnode.piece.row))
					return true;
				currnode = currnode.nextpiece;
			}
		return false;
	}
}