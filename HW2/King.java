
public class King extends Chesspiece {
	public String color;

	public King(int row, int col, String color)
	{
		super(row, col);
		this.color = color;
	}

	@Override
	public boolean isAttacking(Chesspiece other) 
	{
		if(other.returncolor().equals(this.color))
			return false;
		else
		{
			if((row - other.row == 1|| other.row - row == 1) && (col - other.col ==1 || other.col - col == 1))
				return true;
			else if((row - other.row == 0|| other.row - row == 0) && (col - other.col ==1 || other.col - col == 1))
				return true;
			else if((row - other.row == 1|| other.row - row == 1) && (col - other.col ==0 || other.col - col == 0))
				return true;
			else return false;
		}
	}

	@Override
	public String returnclass() {
		return "k";
	}
	@Override
	public String returncolor() {
		return color;
	}
}


