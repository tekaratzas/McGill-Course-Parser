import java.util.ArrayList;

public class Group 
{
	public String name;
	public String program;
	public ArrayList<String> courses = new ArrayList<String>();
	
	public Group(String name, String program)
	{
		this.name = name;
		this.program = program;
	}
}
