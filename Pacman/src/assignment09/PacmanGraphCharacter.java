package assignment09;

public enum PacmanGraphCharacter
{
	SPACE(' '), WALL('X'), START('S'), GOAL('G');
	
	private char value;
	private PacmanGraphCharacter(char _value) { this.value = _value; }	
	
	public int getIntValue() { return (int) value; }
	public char getCharValue() { return value; }
}
