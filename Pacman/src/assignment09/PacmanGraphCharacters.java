package assignment09;

public enum PacmanGraphCharacters 
{
	SPACE(' '), WALL('X'), START('S'), GOAL('G');
	
	char value;
	PacmanGraphCharacters(char _value) { this.value = _value; }
	
	public int getIntValue() { return (int) value; }
}
