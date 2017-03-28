package assignment09;
import java.util.ArrayDeque;
import java.util.Iterator;

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
	 * Dijkstra's algorithm.
	 * Similar to breadth first search but guarantees a shortest path.
	 * 
	 * @param start - start node
	 * @param goal - goal node
	 * 
	 * @return the length of the path
	 */
	
	/**
	 * Searches the Graph, starting from start and finding goal.
	 * Once found, the values of the path-nodes are overwritten with
	 * whatever character PATH is defined in PacmanGraphCharacter.java.
	 * 
	 * DOES NOT GUARANTEE A SHORTEST PATH
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
			{	
				int pathLength = 0;
				
				while(true)
				{
					current = current.getParent();
					pathLength++;
					
					if (current.getParent() == null) 
						return pathLength; // Found the start
					
					current.setValue(PacmanGraphCharacter.PATH.getIntValue());
				}
			}
			
			for (Node neighbor : current.getEdges())
				if(!neighbor.isVisited())
				{
					queue.add(neighbor);
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
				result += nodes[xPos][yPos] + " ";
			result += "\n";
		}
		
		return result;
	}
}
