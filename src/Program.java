import java.util.ArrayList;
import java.util.regex.Pattern;

public class Program 
{
	
	public String name;
	public String type;
	public ArrayList<String> groups = new ArrayList<String>();
	public int credits;
	
	public Program(String input)
	{
		
		String[] pieces = input.split((Pattern.quote("(")));
		this.name = pieces[0];
		this.credits = Integer.parseInt(pieces[1].split(" ")[0]);
		
		String type = input.split(" ")[0];
		
		if(type.equals("Minor"))
		{
			this.type = "minor";
		}
		
		else if(type.equals("Honours"))
		{
			this.type = "honours";
		}
		
		else
		{
			this.type = "major";
		}
		
	}
}
