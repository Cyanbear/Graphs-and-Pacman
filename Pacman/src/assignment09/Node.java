package assignment09;

import java.util.Arrays;

public class Node 
{
	private boolean visited;
	private int value;
	private Node[] edges;
	private Node parent; // Used for backtracking in breadth first search
	
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
	
	public void setEdges(Node[] _edges) { edges = _edges;}
	
	public Node[] getEdges() { return edges; }
	
	public void setParent(Node _parent) {parent = _parent;}
	
	public Node getParent() { return parent; }
	
	public boolean isEdge(Node edge)
	{
		for (Node node : edges)
			if (node.equals(edge))
				return true;
		
		return false;
	}
	
	public boolean addEdge(Node edge)
	{
		if (edge == null || isEdge(edge))
			return false;
		
		edges = Arrays.copyOfRange(edges, 0, edges.length + 1);
		edges[edges.length - 1] = edge;
	
		edge.addEdge(this); // Bidirectional connection
		
		return true;
	}
	
	/**
	 * @param other
	 * 
	 * @return distance between this node and another node
	 * 		   returns a very large int if impossible
	 */
	public int distanceTo(Node previous, Node other)
	{
		if (edges.length == 0 || this.equals(other))
			return 0;
		
		int[] costs = new int[edges.length];
		
		int lowestIndex = 0;
		for (int index = 0; index < edges.length; index++)
		{
			if (edges[index].equals(previous)) continue;
			
			costs[index] = edges[index].distanceTo(this, other);
			
			if (costs[index] < costs[lowestIndex])
				lowestIndex = index;
		}
	
		return costs[lowestIndex] + 1;
	}
	
	public int distanceTo(Node other)
	{
		return distanceTo(this, other);
	}
	
	public String toString()
	{
		return "" + ((char) value);
	}
}
