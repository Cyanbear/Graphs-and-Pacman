package assignment09;

import java.util.Arrays;

public class Node 
{
	private boolean visited;
	private int value;
	private Node[] edges;
	
	public Node() { this(0); }
	
	public Node(int _value)
	{
		this.edges = new Node[0];
		this.value = _value;
	}
	
	public void setVisited(boolean _visited) { visited = _visited; }
	
	public boolean isVisited() { return visited; }
	
	public void setValue(int _value) { value = _value; }
	
	public int getValue() { return value; }
	
	public Node[] getEdges() { return edges; }
	
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
