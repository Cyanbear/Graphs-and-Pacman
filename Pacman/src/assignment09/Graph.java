package assignment09;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Represents a 2 dimensional graph using a Node subclass.
 * 
 * @author Jaden Simon
 *
 */


public class Graph 
{		
	Node[][] nodes;
	
	public Graph() { this(10, 10); }
	
	public Graph(int xSize, int ySize)
	{
		this.nodes = new Node[xSize][ySize];
	}
	
	public void addNode(Node newNode, int posX, int posY)
	{
		if (nodes[posX][posY] != null) // Node already exists, just replace the value.
			nodes[posX][posY].setValue(newNode.getValue());
		else
			nodes[posX][posY] = newNode;	
	}
	
	public Iterator<Node> breadthFirstSearch(Node start, Node goal)
	{
		if (start == null)
			start = nodes[0][0];
		
		ArrayDeque<Node> queue= new ArrayDeque<>();
		queue.add(start);
		start.setVisited(true);
		
		while (queue.size() != 0)
		{
			Node current = queue.removeLast();
			
			if (current == goal)
				return queue.iterator();
			
			for (Node neighbor : current.getEdges())
				if(!neighbor.isVisited())
				{
					queue.add(neighbor);
					neighbor.setVisited(true);
				}
		}
		
		return null; // No path found
	}
}
