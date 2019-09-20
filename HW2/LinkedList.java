public class LinkedList {
	public node head;
	//moves through the nodes until it finds the node with the desired coordinates, or returns a null
	public node find(int r, int c)
	{
		node current = head;
		while(current!= null)
		{
			if(current.piece.row == r && current.piece.col == c)
			{
				return current;
			}
			current = current.nextpiece;
		}
		return null;
	}
	//adds a node to the linklist and makes it the head
	public  void insert(Chesspiece newchess)
	{
		node latest = new node(newchess);
		latest.nextpiece = head;
		head = latest;
	}
}
