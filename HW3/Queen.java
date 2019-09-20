
public class Queen extends Chesspiece {
	public String color;
	public Queen(int row, int col , String color) 
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
			return (row == other.row || col == other.col || Math.abs(col - other.col) == Math.abs(row - other.row));	
	}
	@Override
	public String returnclass() {
		return "q";
	}
	@Override
	public String returncolor() {
		return color;
	}
	@Override
	public void setRow(int x)
	{
		row = x;
	}
	@Override
	public void setCol(int x)
	{
		col = x;
	}
}
