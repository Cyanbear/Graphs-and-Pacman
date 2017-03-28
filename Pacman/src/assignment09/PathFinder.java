package assignment09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

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
	
	private static Graph readFile(String fileName)
	{
		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			
			String[] dimensions = reader.readLine().split(" ");
			int height = Integer.parseInt(dimensions[0]);
			int width = Integer.parseInt(dimensions[1]);
			
			Graph graph = new Graph(width, height);
			
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
					
					// Wall nodes do not have any paths away from it, because Pacman cannot traverse through walls
					if (currentNode.getValue() != PacmanGraphCharacter.WALL.getIntValue())
					{
						currentNode.addEdge(lastRow[xPos]);
						currentNode.addEdge(lastNode);
						lastRow[xPos] = currentNode;
						lastNode = currentNode;
					} else 
					{
						lastRow[xPos] = null;
						lastNode = null;
					}
					
					graph.addNode(currentNode, xPos, yPos);
				}
			}
					
			reader.close();
			
			return graph;
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
		Graph graph = readFile(inputFileName);
		
		System.out.println(graph);
		
		Iterator<Node> path = graph.breadthFirstSearch(graph.nodes[1][1], graph.nodes[4][4]);
		
		if (path != null)
			while (path.hasNext())
				System.out.println(path.next());
		else
			System.out.println("Path not found!");
	}
	
	public static void main(String[] args)
	{
		solveMaze("TestFiles\\test1.txt", "xxx.txt");
	}
}
