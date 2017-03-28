package assignment09;

import java.util.Arrays;

/**
 * A Node to be used in a Graph.
 * 
 * @author Jaden Simon and Jordan Newton
 *
 */

public class Node 
{
	private boolean visited;
	private int value;
	private int ID;
	private Node[] edges;
	private Node parent; // Used for backtracking 
	
	public Node() { this(0, 0); }
	public Node(int _ID) { this(_ID, 0); }
	
	public Node(int _ID, int _value)
	{
		this.edges = new Node[0];
		this.ID = _ID;
		this.value = _value;
	}
	
	// Bunch of getters/setters
	// Could of just been made public , but that's not OOP!
	public void setVisited(boolean _visited) { visited = _visited; }
	
	public boolean isVisited() { return visited; }
	
	public void setValue(int _value) { value = _value; }
	
	public int getValue() { return value; }
	
	public void setEdges(Node[] _edges) { edges = _edges;}
	
	public Node[] getEdges() { return edges; }
	
	public void setParent(Node _parent) {parent = _parent;}
	
	public Node getParent() { return parent; }
	
	public int getID() { return ID; }
	
	/**
	 * @param edge - edge to check
	 * 
	 * @return true if edge is an edge of this Node
	 */
	public boolean isEdge(Node edge)
	{
		for (Node node : edges)
			if (node.equals(edge))
				return true;
		
		return false;
	}
	
	/**
	 * Adds an edge to this Node. Null values are ignored.
	 * If the parameter is already an edge of this node, ignore it.
	 * 
	 * @param edge - node to add as an edge
	 * 
	 * @return true if the edge was successfully added
	 */
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
	 * @return character representation of the Node
	 */
	public String toGraphString()
	{
		return "" + (char) value;
	}
	
	public String toString()
	{
		String string = "(Node ID: " + ID + " | Edges: ";
		
		if (edges.length == 0)
			return string + "none )";
		
		for (int index = 0; index < edges.length - 1; index++)
			string += edges[index].ID + ", ";
		
		return string + edges[edges.length - 1].ID + ")";
	}
}
