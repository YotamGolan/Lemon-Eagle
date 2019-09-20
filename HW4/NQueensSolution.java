//-----------------------------------------------------------------------------
//Yotam Golan, ID:yogolan
//NQueensSolution solves the problem
//-----------------------------------------------------------------------------
import java.util.Stack;

class NQueensSolution {
	private final int board;
	private final Queen[] givenQueens;
	
	public NQueensSolution(int boardsize, Queen[] QueenBees)
	{
		board = boardsize;
		givenQueens = QueenBees;
	}
	
	public Queen[] solver()
	{
		int row = 1;
		int col = 1;
		//checks for preexisting placement fighting
		 for (int i = 0; i < givenQueens.length - 1; i++)
            for (int j = i + 1; j < givenQueens.length; j++)
				//royalStack.push(queenbee);
                if (givenQueens[i].isAttacking(givenQueens[j]))
					return null;
		//nothing is on fire, so can proceed
		//initialize a new stack of queens to track solution progress
		Stack<Queen> royalStack = new Stack<Queen>();
		//adds initial queens to stack
		for (Queen queenBee : givenQueens)
            royalStack.push(queenBee);
		
		 while (row <= board) { 
		 //skips row if its already got a queen occupying it initially
		 // only on first pass
		 boolean isOccupied = false;
            for (Queen queenBee : givenQueens)
                if (row == queenBee.row) {
                    isOccupied = true;
                    row++;
                    break;
                }
				if (isOccupied)
                continue;
			
		/*
		if (BoardSPaceCorrect(royalStack, row)) {
                royalStack.push(row);
                board[royalStack.size() - 1] = row;
                row = 0;
            } else {
                row++;
		*/
		
		boolean addedQueen = false;;
		//Makes a queen for an available spot, the compares it to the other
		//queens to determine if its possible
		  for (;col <= board; col++) 
		  {
                Queen Princess = new Queen(row, col);
                boolean possible = true;
                for (Queen queenBee : royalStack)
                    if (queenBee.isAttacking(Princess)) {
                        possible = false;
                        break;
                    }
					
				if(possible)
				{
					royalStack.push(Princess);
					addedQueen = true;
					break;
				}
		  }
		  //added a queen so move on to next possible row and try again
		  if(addedQueen)
		  {
			  row++;
			  col = 1;
		  }
		  /*
        if (royalStack.size() == board) {
            printSolution(royalStack);
			*/
        
		  else
		  {
			  //things have caught fire, no solution return a null
			  if(royalStack.size() == givenQueens.length)
				  return null;
			  //things havent caught fire, keep trying!
			  Queen failedMonarch = royalStack.pop();
			  col = failedMonarch.col+1;
			  row = failedMonarch.row;
		  }
		}
		
				//Sesh has tried to cheese a failure, board invalid
				if(royalStack.empty())
					return null;
				//robochecker fails me if its unsorted
				return sortedForPoints(royalStack.toArray(new Queen[board]));
	}
		 //sorts the array by col so I actually get points
		 private Queen[] sortedForPoints(Queen[] queenArray)
		 {
			 for(int i = 0; i < queenArray.length; i ++)
			 {
				 int lowest = queenArray[i].col;
				 int overLowest = i;
				 
				//finds the lowest
				for(int j = i+1; j<queenArray.length; j++)
					if(queenArray[j].col < lowest)
						{
							lowest = queenArray[j].col;
							overLowest = j;
						}
				//swap queens
				Queen temp = queenArray[i];
				queenArray[i] = queenArray[overLowest];
				queenArray[overLowest] = temp;
			 }
			 return queenArray;
		 }	
}