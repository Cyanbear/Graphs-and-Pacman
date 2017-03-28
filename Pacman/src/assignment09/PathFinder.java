package assignment09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PathFinder 
{
	private static Node nodeFromChar(char character) throws IOException
	{	
		for (PacmanGraphCharacter graphCharacter : PacmanGraphCharacter.values())
		{
			if (character == graphCharacter.getCharValue())
				return new Node(graphCharacter.getIntValue());
		}
		
		// String has no matching graph character; error.
		
		throw new IOException("Invalid character used!");
	}
	
	/**
	 * Returns a grid of the Nodes read from the file.
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
			
			Node[] lastRow = new Node[width];
			for (int yPos = 0; yPos < height; yPos++)
			{
				char[] rowValues = reader.readLine().toCharArray();
				
				// Make sure lines do not contain characters beyond the given width.
				if (rowValues.length > width)
				{
					reader.close();
					throw new IOException("Extra characters occured reading line.");
				}
				
				Node lastNode = null;
				
				for (int xPos = 0; xPos < width; xPos++)
				{				
					Node currentNode = nodeFromChar(rowValues[xPos]);
					
					// Make sure side nodes are only walls
					if (yPos == 0 || yPos == height - 1 || xPos == 0 || xPos == width - 1)
						if (currentNode.getValue() != PacmanGraphCharacter.WALL.getIntValue())
						{
							reader.close();
							throw new IOException("Graph sides should exclusively be made of wall characters.");
						}
					
					// Wall nodes do not have any edges away from it
					// because Pacman cannot traverse through walls
					if (currentNode.getValue() != PacmanGraphCharacter.WALL.getIntValue())
					{
						currentNode.addEdge(lastNode);
						currentNode.addEdge(lastRow[xPos]);
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
	
	public static void solveMaze(String inputFileName, String outputFileName)
	{
		Node[][] grid = readFile(inputFileName);
		Node start = null;
		Node goal = null;
		Graph graph = new Graph(grid.length, grid[0].length);
		
		for (int xPos = 0; xPos < grid.length; xPos++)
			for (int yPos = 0; yPos < grid[0].length; yPos++)
			{
				Node current = grid[xPos][yPos];
				
				graph.addNode(current, xPos, yPos);
				
				if (current.getValue() == PacmanGraphCharacter.GOAL.getIntValue())
					goal = current;
				else if (current.getValue() == PacmanGraphCharacter.START.getIntValue())
					start = current;
			}
		
		System.out.println(start.distanceTo(goal));
		
		System.out.println("Unsolved graph: ");
		System.out.println(graph);
		
		System.out.println("Solved graph: " + graph.breadthFirstSearch(start, goal));
		System.out.println(graph);
	}
	
	public static void main(String[] args)
	{
		solveMaze("TestFiles\\test1.txt", "xxx.txt");
	}
}
