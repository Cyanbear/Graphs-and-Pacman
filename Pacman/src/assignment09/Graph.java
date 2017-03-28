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
	static class Node 
	{
		boolean visited;
		int value;
		Node[] edges;
		
		public Node() { this(0); }
		
		public Node(int _value)
		{
			this.edges = new Node[0];
			this.value = _value;
		}
		
		public boolean isEdge(Node edge)
		{
			for (Node node : edges)
				if (node.equals(edge))
					return true;
			
			return false;
		}
		
		public boolean addEdge(Node edge)
		{
			if (isEdge(edge))
				return false;
			
			edges = Arrays.copyOfRange(edges, 0, edges.length + 1);
			edges[edges.length - 1] = edge;
		
			edge.addEdge(this); // Bidirectional connection
			
			return true;
		}
		
		public String toString()
		{
			return "" + value;
		}
	}
	
	Node[][] nodes;
	
	public Graph() { this(10, 10); }
	
	public Graph(int xSize, int ySize)
	{
		this.nodes = new Node[xSize][ySize];
	}
	
	/**
	 * Returns an array containing all non-diagonal neighbors of the specified position.
	 * 
	 * @param posX - x-axis position
	 * @param posY - y-axis position
	 * 
	 * @return the neighbor iterator
	 */
	private Node[] getNeighbors(int posX, int posY)
	{
		ArrayDeque<Node> array = new ArrayDeque<>();
		
		// Right neighbor
		if (posX + 1 < nodes.length)
			if (nodes[posX + 1][posY] != null)
				array.add(nodes[posX + 1][posY]);
		
		// Left neighbor
		if (posX - 1 > 0)
			if (nodes[posX - 1][posY] != null)
				array.add(nodes[posX - 1][posY]);
		
		// Top neighbor
		if (posY + 1 < nodes[0].length)
			if (nodes[posX][posY + 1] != null)
				array.add(nodes[posX][posY + 1]);
		
		// Bottom neighbor
		if (posY - 1 > 0)
			if (nodes[posX][posY - 1] != null)
				array.add(nodes[posX][posY - 1]);
		
		Node[] nodes = new Node[array.size()];
		array.toArray(nodes);
		
		return nodes; 
	}
	
	public void addNode(Node newNode, int posX, int posY)
	{
		if (nodes[posX][posY] != null) // Node already exists, just replace the value.
			nodes[posX][posY].value = newNode.value;
		else
		{
			nodes[posX][posY] = newNode;
			
			for (Node neighbor : getNeighbors(posX, posY))
				newNode.addEdge(neighbor);
		}
		
	}
	
	public Iterator<Node> breadthFirstSearch(Node start, Node goal)
	{
		if (start == null)
			start = nodes[0][0];
		
		ArrayDeque<Node> queue= new ArrayDeque<>();
		queue.add(start);
		start.visited = true;
		
		while (queue.size() != 0)
		{
			Node current = queue.removeLast();
			
			if (current == goal)
				return queue.iterator();
			
			for (Node neighbor : current.edges)
				if(!neighbor.visited)
				{
					queue.add(neighbor);
					neighbor.visited = true;
				}
		}
		
		return null; // No path found
	}
	
	public static void main(String[] args)
	{		
		Graph graph = new Graph();
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
			{
				int value = 0;
				
				if (i == 0 || j == 0 || i == 9 || j == 9)
					value = PacmanGraphCharacters.WALL.getIntValue();
				
				Node newNode = new Node(value);
				
				graph.addNode(newNode, i, j);
			}
		
		Iterator<Node> path = graph.breadthFirstSearch(graph.nodes[0][0], graph.nodes[9][9]);
		
		System.out.println(graph.nodes[0][0]);
		
		if (path != null)
			while (path.hasNext())
				System.out.println(path.next());
	}
}
