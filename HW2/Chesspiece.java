
public abstract class Chesspiece 
{
	public int row,col;
	public String color;

	public Chesspiece(int row, int col)
	{
		this.row = row;
		this.col = col;
	}
	//uses the pieces hard coded attack patterns to check if it hits another chess piece given
	public abstract boolean isAttacking(Chesspiece other);
	//returns the class of the piece, always lower case
	public abstract String returnclass();
	//returns the color of the piece, always lower case
	public abstract String returncolor();

}
