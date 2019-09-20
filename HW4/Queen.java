//-----------------------------------------------------------------------------
// Yotam Golan, ID:yogolan
//Queen is the class for the Queen object used in NQueens
//It simply tracks the Queens row and column location and comparing to other queen objects to ensure it can be placed at that coordinate
//Used by running Makefile1, no .jar is created but it is still required and accessed by NQueens.jar
//-----------------------------------------------------------------------------
public class Queen {
	public int row, col;
	//Simple constructor that handles the location of the Queen in the imaginary board
	public Queen(int r, int c) {
		row = r;
		col = c;
	}

	//Runs simple math to check if Queens have the same row or column
	//If the product of division between the column and row of the two Queens, then they are diagonal from on another and thus make the space unsafe to place 
	public boolean isAttacking(Queen other) {
		return (row == other.row || col == other.col || Math.abs(col - other.col) == Math.abs(row - other.row));
	}

}
