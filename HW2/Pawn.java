
public class Pawn extends Chesspiece {
	public String color;
	
	public Pawn(int row, int col, String color) 
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
			if(color == "black")
			return (other.row - row == -1 && Math.abs(col - other.col) == 1);
			else
				return (other.row - row == 1 && Math.abs(col - other.col) == 1);
	}
	@Override
	public String returnclass() {
		return "p";
	}
	@Override
	public String returncolor() {
		return color;
	}

}
