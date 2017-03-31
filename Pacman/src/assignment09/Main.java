package assignment09;

import java.util.Random;

/**
 * Runs the PathFinder class to generate solutions to Pacman mazes.
 * Has fields to modify the behavior of PathFinder.java.
 * 
 * @author Jaden Simon and Jordan Newton
 *
 */

public class Main 
{
	// Change variables here to affect PathFinder.java
	
	// Random Maze variables
	final static boolean USE_RANDOM_MAZE    = false;	
	final static int RANDOM_MAZE_WIDTH      = -1; 	   	// -1 if random width
	final static int RANDOM_MAZE_HEIGHT     = -1;	    // -1 if random height
	final static double RANDOM_MAZE_DENSITY = -1;		// -1 if random density (0 to 1 otherwise)
	
	// Path finding method
	// Options: BREADTH_FIRST_SEARCH, DEPTH_FIRST_SEARCH, A_STAR_SEARCH
	final static int PATH_FINDING_METHOD = PathFinder.BREADTH_FIRST_SEARCH;
	
	// Files names
	final static String INPUT_FILE_NAME  = "TestFiles\\test1.txt";
	final static String OUTPUT_FILE_NAME = "TestFiles\\test1Output.txt";
	
	public static void main(String[] args)
	{
		Random generator = new Random(System.currentTimeMillis());
		PathFinder.searchType = PATH_FINDING_METHOD;
		
		if (USE_RANDOM_MAZE)
		{
			int width      = RANDOM_MAZE_WIDTH < 0   ? generator.nextInt(249) + 2       : RANDOM_MAZE_WIDTH;
			int height 	   = RANDOM_MAZE_HEIGHT < 0  ? generator.nextInt(249) + 2       : RANDOM_MAZE_HEIGHT;
			double density = RANDOM_MAZE_DENSITY < 0 ? generator.nextInt(1001) / 1000.0 : RANDOM_MAZE_DENSITY;


			MazeGenerator.generateMaze("randomMaze.txt", width, height, density);
			PathFinder.solveMaze("randomMaze.txt", "randomMazeOutput.txt");
		} else 
		{
			PathFinder.solveMaze(INPUT_FILE_NAME, OUTPUT_FILE_NAME);
		}
	}

}
