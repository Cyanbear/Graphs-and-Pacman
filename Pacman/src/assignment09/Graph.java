package assignment09;
import java.util.ArrayDeque;

/**
 * Represents a 2 dimensional graph using a Node subclass.
 * Specifically used for Pacman path-finding (not generalized).
 * 
 * @author Jaden Simon
 *
 */


public class Graph 
{		
	Node[][] nodes; // Nodes are stored in a 2-D array, though they are
				    // not traversed using it.
	
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
	
	/**
	 * Uses a GOAL node to build a path from it.
	 * 
	 * @param goal - node to build from
	 * 
	 * @return the length of the path
	 */
	private int buildPath(Node goal)
	{
		int pathLength = 0;
		Node current = goal;
		
		while(true)
		{
			current = current.getParent();
			pathLength++;
			
			if (current.getParent() == null) 
				return pathLength; // Found the start
			
			current.setValue(PacmanGraphCharacter.PATH.getIntValue());
		}
	}
	
	/**
	 * Depth-first search. The first path found that leads to the goal is
	 * returned. 
	 * 
	 * @param start - start node
	 * @param goal - goal node
	 * 
	 * @return the length of the path
	 */
	public int depthFirstSearch(Node start, Node goal)
	{
		// TODO: need to fix this algorithm
		if (start.equals(goal))
			return buildPath(start);
		
		start.setVisited(true);
		
		for (Node neighbor : start.getEdges())
			if (!neighbor.isVisited())
			{
				neighbor.setParent(start);
				
				return depthFirstSearch(neighbor, goal); // Recursion
			}
				
		return -1; // No path
	}
	
	/**
	 * Searches the Graph, starting from start and finding goal.
	 * Once found, the values of the path-nodes are overwritten with
	 * whatever character PATH is defined in PacmanGraphCharacter.java.
	 * 
	 * @param start - start node
	 * @param goal - goal node
	 * 
	 * @return the length of the path
	 */
	public int breadthFirstSearch(Node start, Node goal)
	{
		if (start == null || goal == null)
		{
			System.out.println("Start or goal is missing from the graph!");
			System.exit(1);
		}
		
		ArrayDeque<Node> queue = new ArrayDeque<>();
		queue.add(start);
		start.setVisited(true);
		
		while (queue.size() != 0)
		{
			Node current = queue.removeLast();
			
			// Found goal, backtrack to create a path.
			if (current == goal)
				return buildPath(current);
			
			for (Node neighbor : current.getEdges())
				if(!neighbor.isVisited())
				{
					queue.addFirst(neighbor);
					neighbor.setParent(current);
					neighbor.setVisited(true);
				}
		}
		
		return -1; // No path
	}
	
	public String toString()
	{
		String result = "";
		
		for (int yPos = 0; yPos < nodes[0].length; yPos++)
		{
			for (int xPos = 0; xPos < nodes.length; xPos++)
				result += nodes[xPos][yPos];
			result += "\n";
		}
		
		return result;
	}
}
