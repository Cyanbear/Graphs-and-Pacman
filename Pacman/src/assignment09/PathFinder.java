package assignment09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PathFinder 
{
	private static Node nodeFromChar(char character)
	{	
		for (PacmanGraphCharacter graphCharacter : PacmanGraphCharacter.values())
		{
			if (character == graphCharacter.getCharValue())
				return new Node(graphCharacter.getIntValue());
		}
		
		// String has no matching graph character; error.
		
		System.out.println("Invalid input file!");
		System.exit(1);
		return null;
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
				String[] rowValues = reader.readLine().split(" ");
				Node lastNode = null;
				
				for (int xPos = 0; xPos < width; xPos++)
				{
					String currentValue = rowValues[xPos];
					
					if (currentValue.length() != 1)
					{
						System.out.println("File is incorrect!");
						System.exit(1);
					} else 
					{
						Node currentNode = nodeFromChar(currentValue.charAt(0));
						
						currentNode.addEdge(lastRow[xPos]);
						currentNode.addEdge(lastNode);
						
						lastRow[xPos] = currentNode;
						lastNode = currentNode;
						
						graph.addNode(currentNode, xPos, yPos);
					}
				}
			}
					
			return graph;
		} catch (IOException e) 
		{
			System.out.println("Something went wrong reading " + fileName);
			System.exit(1);
			
			return null;
		}
	}
	
	public static void solveMaze(String inputFileName, String outputFileName)
	{
		Graph graph = readFile(inputFileName);
		
	}
}
