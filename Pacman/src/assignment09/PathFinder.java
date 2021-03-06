package assignment09;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Generates a solution to a given Pacman graph.
 * 
 * @author Jaden Simon and Jordan Newton
 *
 */

public class PathFinder 
{
	final static int BREADTH_FIRST_SEARCH = 1;
	final static int DEPTH_FIRST_SEARCH = 2;
	final static int A_STAR_SEARCH = 3;	
	
	static int searchType = BREADTH_FIRST_SEARCH;
	
	/**
	 * Creates a new Node form the given character.
	 * 
	 * @param ID        - ID of the new Node
	 * @param character - character to use
	 * 
	 * @return the new node
	 * @throws IOException
	 */
	private static Node nodeFromChar(int ID, char character) throws IOException
	{	
		// Path characters should not be in the input file!
		if (character == PacmanGraphCharacter.PATH.getCharValue())
			throw new IOException("Illegal File Format");
		
		for (PacmanGraphCharacter graphCharacter : PacmanGraphCharacter.values())
		{
			if (character == graphCharacter.getCharValue())
				return new Node(ID, graphCharacter.getIntValue());
		}
		
		// String has no matching graph character; error.
		
		throw new IOException("Illegal File Format");
	}
	
	/**
	 * Returns a grid of the Nodes read from the file.
	 * Edges are also added to the Nodes while the file is being read.
	 * 
	 * @param fileName - file to read
	 * 
	 * @return grid of nodes
	 */
	private static Node[][] readFile(String fileName)
	{
		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			
			String[] dimensions = reader.readLine().split(" ");
			int height = Integer.parseInt(dimensions[0]);
			int width = Integer.parseInt(dimensions[1]);
			
			Node[][] grid = new Node[width][height];
			
			Node[] lastRow = new Node[width]; // Stores the previous row nodes
			for (int yPos = 0; yPos < height; yPos++)
			{
				char[] rowValues = reader.readLine().toCharArray();
				
				// Make sure lines do not contain characters beyond the given width.
				if (rowValues.length > width)
				{
					reader.close();
					throw new IOException("Illegal File Format");
				}
				
				Node lastNode = null;
				
				for (int xPos = 0; xPos < width; xPos++)
				{				
					Node currentNode = nodeFromChar(xPos + (yPos * width), rowValues[xPos]);
					
					// Make sure side nodes are only walls
					if (yPos == 0 || yPos == height - 1 || xPos == 0 || xPos == width - 1)
						if (currentNode.getValue() != PacmanGraphCharacter.WALL.getIntValue())
						{
							reader.close();
							throw new IOException("Illegal File Format");
						}
					
					// Wall nodes do not have any edges away from it
					// because Pacman cannot traverse through walls
					if (currentNode.getValue() != PacmanGraphCharacter.WALL.getIntValue())
					{
						currentNode.addEdge(lastRow[xPos]);
						currentNode.addEdge(lastNode);
						lastNode = currentNode;
						lastRow[xPos] = currentNode;
					} else 
					{
						lastNode = null;
						lastRow[xPos] = null;
					}
					
					grid[xPos][yPos] = currentNode;
				}
			}
					
			reader.close();
			
			return grid;
		} catch (IOException error) 
		{
			System.out.println("Something went wrong reading " + fileName);
			error.printStackTrace();
			System.exit(1);
			
			return null;
		}
	}
	/**
	 * Writes a (Pacman) Graph to a file.
	 * 
	 * @param fileName - name of the file to write to
	 * @param output   - graph to use 
	 */
	private static void writeFile(String fileName, Graph output)
	{
		try
		{
			PrintWriter writer = new PrintWriter(new File(fileName));
			
			// Output the graph
			writer.print(output.nodes[0].length + " " + output.nodes.length + "\n");
			writer.print(output + "\n");
			
			writer.close();
		} catch (IOException error)
		{
			System.out.println("Something went wrong writing to " + fileName);
			error.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Calls path finding methods on a Graph built from the input file.
	 * After a path is found (or not found), the final Graph is written to an output file.
	 * 
	 * @param inputFileName  - file to read from
	 * @param outputFileName - file to write to
	 */
	public static void solveMaze(String inputFileName, String outputFileName)
	{
		Node[][] grid = readFile(inputFileName);
		Node start = null;
		Node goal = null;
		Graph graph = new Graph(grid.length, grid[0].length);
		
		// Iterate over the returned grid
		for (int xPos = 0; xPos < grid.length; xPos++)
			for (int yPos = 0; yPos < grid[0].length; yPos++)
			{
				Node current = grid[xPos][yPos];
				
				// Add the node to the graph
				graph.addNode(current, xPos, yPos);
				
				// Check for goal/start nodes
				if (current.getValue() == PacmanGraphCharacter.GOAL.getIntValue())
					goal = current;
				else if (current.getValue() == PacmanGraphCharacter.START.getIntValue())
					start = current;
			}
						
		System.out.println("Unsolved graph: ");
		System.out.println(graph + "\n");
		
		double startTime = System.nanoTime();
		
		System.out.print("Solved graph: ");
		
		// Solve the graph depending on the search type
		switch(searchType)
		{
		case BREADTH_FIRST_SEARCH : System.out.println(graph.breadthFirstSearch(start, goal));
									break;
		case DEPTH_FIRST_SEARCH : 	System.out.println(graph.depthFirstSearch(start, goal));
									break;
		case A_STAR_SEARCH : 	 	System.out.println(graph.AStarSearch(start, goal));
									break;
		default :					System.out.print("INVALID SEARCH TYPE!");
									return;
		}
		
		System.out.println(graph + "\n");
		System.out.println("Took " + (System.nanoTime() - startTime) / 1e6 + " milliseconds.");
		System.out.println("A total of " + graph.visitedCount() + " Nodes were checked.");
		System.out.println("The distance between the goal and the start is " + graph.manhattanDistance(goal, start));
		
		writeFile(outputFileName, graph);
	}
}
