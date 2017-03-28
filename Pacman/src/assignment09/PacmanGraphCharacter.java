package assignment09;

/**
 * Defines which characters are what for Pacman Graphs.
 * 
 * @author Jaden Simon and Jordan Newton
 *
 */
public enum PacmanGraphCharacter
{
	SPACE(' '), WALL('X'), START('S'), GOAL('G'), PATH('.');
	
	private char value;
	private PacmanGraphCharacter(char _value) { this.value = _value; }	
	
	public int getIntValue() { return (int) value; }
	public char getCharValue() { return value; }
}
