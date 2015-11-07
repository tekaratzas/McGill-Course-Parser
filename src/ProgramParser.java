
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.jsoup.Jsoup;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.List;

import com.google.gson.Gson;

public class ProgramParser {

	public String PROGRAMS;
	public String GROUPS;
	private ArrayList<Program> programs = new ArrayList<Program>();
	private ArrayList<Group> groups = new ArrayList<Group>();
	
	public ProgramParser() throws IOException
	{
		this.PROGRAMS = this.parse();
		this.GROUPS = this.getGroups();
	}
	
	public String parse() throws IOException
	{
		// Connect to Document
		
		Document doc = Jsoup.connect("https://www.mcgill.ca/study/2012-2013/faculties/engineering/undergraduate/programs").get();
		
		// Select all links with class name "title"
		
		Elements programs = doc.select(".title");
		
		/**
		 *  Iterate through and create instance of program for each Link
		 *  Retrieve each "href" attribute as link to retrieve groups along with courses 
		 **/
		
		for(Element link: programs)
		{
			Program new_program = new Program(link.text());
			
			GroupParser groups_for_program = new GroupParser(new_program.name, link.children().attr("href"));
			
			for(int i=0; i<groups_for_program.groups.size(); i++)
			{
				new_program.groups.add(groups_for_program.groups.get(i).name);
				this.groups.add(groups_for_program.groups.get(i));
			}
			
			
			this.programs.add(new_program);
		}
		
		return new Gson().toJson(this.programs);
	}
	
	public String getGroups()
	{
		return new Gson().toJson(this.groups);
	}
}
