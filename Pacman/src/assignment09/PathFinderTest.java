package assignment09;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.junit.Test;

/**
 * JUnit test cases for PathFinder.java.
 * 
 * @author Jaden Simon and Jordan Newton
 *
 */

public class PathFinderTest 
{
	/**
	 * Compares the equality of two files.
	 * @throws IOException 
	 */
	private boolean compareFiles(File file1, File file2) throws IOException
	{
		List<String> file1Lines = Files.readAllLines(file1.toPath());
		List<String> file2Lines = Files.readAllLines(file2.toPath());
		
		int path1Count = 0;
		int path2Count = 0;
		
		for (int index = 0; index < file1Lines.size(); index++)
		{
			path1Count += countDotsInString(file1Lines.get(index));
			path2Count += countDotsInString(file2Lines.get(index));
		}
		
		return path1Count == path2Count;
	}
	
	private int countDotsInString(String line)
	{
		int count = 0;
		
		for (int pos = 0; pos < line.length(); pos++)
			if (line.charAt(pos) == PacmanGraphCharacter.PATH.getCharValue())
				count++;
		
		return count;
	}
	
	private File getFileFromArray(File[] files, String fileName)
	{
		for (int index = 0; index < files.length; index++)
			if (files[index].getName().equals(fileName))
				return files[index];
		
		return null;
	}
	
	@Test
	public void testSolveMazeUsingTestFiles() 
	{
		File testFolder = new File("JUnitTestMazes");
		File[] testFiles = testFolder.listFiles();
		
		
		try
		{
			// Run through all test files using names that contain Sol as the solution
			for (File file : testFiles)
			{
				// Found file to test
				if (!file.getName().endsWith("Sol.txt"))
				{
					System.out.println("Testing " + file.getPath() + "\n");
					
					PathFinder.solveMaze(file.getPath(), "temp.txt");
					File output = new File("temp.txt");
					File solution = getFileFromArray(testFiles, 
							file.getName().substring(0, file.getName().length() - 4) + "Sol.txt");
					
					assert(compareFiles(output, solution));
				}
			}
		} catch (IOException error)
		{
			System.out.println("Error reading test files!");
			error.printStackTrace();
		}
	}

}

