package assignment09;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a 2 dimensional graph using a Node subclass.
 * Specifically used for Pacman path-finding (not generalized).
 * 
 * @author Jaden Simon and Jordan Newton
 *
 */


public class Graph 
{		
	Node[][] nodes; // Nodes are stored in a 2-D array, though they are
				    // only traversed using the array when outputting as a String.
	
	public Graph() { this(10, 10); }
	
	public Graph(int xSize, int ySize)
	{
		this.nodes = new Node[xSize][ySize];
	}
	
	/**
	 * Adds a Node to the Graph at the given position.
	 * 
	 * @param newNode- node to add
	 * @param posX   - x position
	 * @param posY   - y position
	 */
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
	 * Distance function for Graph nodes. Uses Node IDs.
	 * 
	 * @param node1
	 * @param node2
	 * 
	 * @return cost to move
	 */
	private int AStarCostMethod(Node node1, Node node2)
	{
		int node1PosX = node1.getID() % nodes.length;
		int node2PosX = node2.getID() % nodes.length;		
		int node1PosY = node1.getID() / nodes.length;
		int node2PosY = node2.getID() / nodes.length;
		
		return (Math.abs(node2PosX - node1PosX) + Math.abs(node2PosY - node1PosY)) * 1001;
	}
	
	/**
	 * A* algorithm. Uses a heuristic function to find the distance between nodes.
	 * This is a best-first search algorithm.
	 * 
	 * In this implementation of the algorithm, the cost of moving from one node to the next is
	 * 1000, while the estimated cost from moving from one node to another is the Manhattan distance
	 * times 1001. 
	 * 
	 * @param start - start node
	 * @param goal  - goal node
	 * 
	 * @return the length of the path
	 */
	public int AStarSearch(Node start, Node goal)
	{
		int graphSize = nodes.length * nodes[0].length;
		int[] gScore = new int[graphSize];
		int[] fScore = new int[graphSize];
		
		Arrays.fill(gScore, Integer.MAX_VALUE);
		Arrays.fill(fScore, Integer.MAX_VALUE);
		
		// Using an ArrayList as our priority queue.
		ArrayList<Node> queue = new ArrayList<> (graphSize);
		
		// Start with the start node
		queue.add(start);
		gScore[start.getID()] = 0;
		fScore[start.getID()] = AStarCostMethod(start, goal);
		
		// While our queue has nodes
		while(queue.size() != 0)
		{						
			Node current = queue.remove(queue.size() - 1);
			
			if (current.equals(goal))
				return buildPath(current);
			
			current.setVisited(true);
			
			// For every unvisited neighbor of current,
			// calculate their fScore and gScores and add them to the queue
			// if they are viable options.
			for (Node neighbor : current.getEdges())
				if (!neighbor.isVisited())
				{					
					int tentativeGScore = gScore[current.getID()] + 1000;
					
					if (tentativeGScore >= gScore[neighbor.getID()]) continue;
					
					neighbor.setParent(current);
					gScore[neighbor.getID()] = tentativeGScore;
					fScore[neighbor.getID()] = tentativeGScore + AStarCostMethod(neighbor, goal);
										
					// Add this new node to queue, sorting by fScore (descending order).
					int stopIndex = 0;
					
					while (stopIndex < queue.size())
						if (fScore[queue.get(stopIndex++).getID()] <= fScore[neighbor.getID()])
							break;
					
					if (stopIndex == queue.size())
						queue.add(neighbor);
					else if (stopIndex == 0)
						queue.add(0, neighbor);
					else
						queue.add(stopIndex - 1, neighbor);					
				}
		}
		
		return -1; // No path
	}
	
	/**
	 * Depth-first search. The first path found that leads to the goal is
	 * returned. 
	 * 
	 * @param start - start node
	 * @param goal  - goal node
	 * 
	 * @return the length of the path
	 */
	public int depthFirstSearch(Node start, Node goal)
	{
		if (start.equals(goal))
			return buildPath(start);
		
		start.setVisited(true);
		
		for (Node neighbor : start.getEdges())
		{
			if (!neighbor.isVisited())
			{
				neighbor.setParent(start);
				
				int path = depthFirstSearch(neighbor, goal);
				if (path != -1) return path;
			}
		}
				
		return -1; // No path
	}
	
	/**
	 * Searches the Graph, starting from start and finding goal.
	 * Once found, the values of the path-nodes are overwritten with
	 * whatever character PATH is defined in PacmanGraphCharacter.java.
	 * 
	 * @param start - start node
	 * @param goal  - goal node
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
				result += nodes[xPos][yPos].toGraphString();
			if (yPos < nodes[0].length - 1)
				result += "\n";
		}
		
		return result;
	}
}
