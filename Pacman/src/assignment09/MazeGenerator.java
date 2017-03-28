package assignment09;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Generates a random Pacman maze to the specific fileName.
 * May or may not be solvable.
 * 
 * @author Jaden Simon and Jordan Newton
 *
 */

public class MazeGenerator 
{
	private static Random generator = new Random(System.currentTimeMillis());
	
	/**
	 * Generates a file with a random Pacman maze.
	 * May or may not have a solution.
	 * 
	 * @param fileName   - name of the output file
	 * @param width      - width of the maze
	 * @param height     - height of the maze
	 * @param wallChance - chance of a wall to appear (between 0 to 1)
	 */
	public static void generateMaze(String fileName, int width, int height, double wallChance)
	{			
		char[][] grid = new char[width][height];
		
		// Generate start/goal 
		int startX = generator.nextInt(width - 2) + 1;
		int startY = generator.nextInt(height - 2) + 1;
		int goalX = (startX + generator.nextInt(width)) % (width - 2) + 1;
		int goalY = (startY + generator.nextInt(height)) % (height - 2) + 1;
		grid[startX][startY] = PacmanGraphCharacter.START.getCharValue();
		grid[goalX][goalY] = PacmanGraphCharacter.GOAL.getCharValue();
		
		// Fill in the rest of the grid
		for (int xPos = 0; xPos < width; xPos++)
			for (int yPos = 0; yPos < height; yPos++)
				if (grid[xPos][yPos] == (char) 0)
				{
					if (yPos == 0 || yPos == height - 1 || xPos == 0 || xPos == width - 1)
						grid[xPos][yPos] = PacmanGraphCharacter.WALL.getCharValue();
					else 
					{
						grid[xPos][yPos] = (generator.nextInt(1000) > (int)(wallChance * 1000)) ? 
											PacmanGraphCharacter.SPACE.getCharValue() :
											PacmanGraphCharacter.WALL.getCharValue();
					}
				}

		// Write to file
		try
		{
			PrintWriter writer = new PrintWriter(new File(fileName));
			
			writer.print(height + " " + width + "\n");
			
			for (int yPos = 0; yPos < height; yPos++)
			{
				for (int xPos = 0; xPos < width; xPos++)
					writer.print(grid[xPos][yPos]);
				if (yPos < height - 1)
					writer.print("\n");
			}
			
			writer.close();
		} catch (IOException error)
		{
			System.out.println("Something went wrong writing to " + fileName);
			error.printStackTrace();
			System.exit(1);
		}
	}
}
