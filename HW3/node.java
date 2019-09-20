public class node 
{
	public Chesspiece piece;
	public node nextpiece;
	public node(Chesspiece newpiece)
	{
		piece = newpiece;
		nextpiece = null;
	}
}
