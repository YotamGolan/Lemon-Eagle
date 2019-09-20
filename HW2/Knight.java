
public class Knight extends Chesspiece {
	public String color;

	public Knight(int row, int col, String color) 
	{
		super(row, col);
		this.color = color;
	}

	@Override
	public boolean isAttacking(Chesspiece other) 
	{
		if(other.returncolor().equals(this.color))
			return false;
		else if((row - other.row == 2|| other.row - row == 2) && (col - other.col ==1 || other.col - col == 1))
			return true;
		else if((row - other.row == 1|| other.row - row == 1) && (col - other.col ==2 || other.col - col == 2))
			return true;
		else return false;
	}
	@Override
	public String returnclass() {
		return "n";
	}
	@Override
	public String returncolor() {
		return color;
	}
}
