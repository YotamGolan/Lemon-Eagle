
public class Rook extends Chesspiece {
	public String color;

	public Rook(int row, int col, String color) 
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
			return (row == other.row || col == other.col);	
	}
	@Override
	public String returnclass() {
		return "r";
	}
	@Override
	public String returncolor() {
		return color;
	}
}
