import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GroupParser 
{
	private String URL;
	private String forProgram;
	
	public ArrayList<Group> groups = new ArrayList<Group>();
	
	public GroupParser(String forProgram, String URL) throws IOException
	{
		this.setForProgram(forProgram);
		this.setURL(URL);
		this.parse();
	}
	
	public void parse() throws IOException
	{
		Document doc = Jsoup.connect(this.URL).timeout(0).get();
		
		// Retrieve First Group and Siblings of the Group
		
		Elements groups = doc.select("h4");
		groups.remove(0);
		
		for(Element group: groups)
		{
			Group new_group = new Group(group.text(), this.forProgram);
			this.groups.add(new_group);
		}
		
		//System.out.println(this.groups);
		
		// Get Courses in Between two Groups
		
		int array_index = 0;
		
		Element firstH1 = doc.select("h4").get(1);
		Elements siblings = firstH1.siblingElements();
		List<Element> elementsBetween = new ArrayList<Element>();
		
		int group = 0;
		
		for (int i = 1; i < siblings.size(); i++) 
		{
		    Element sibling = siblings.get(i);
		    
		    if("h4".equals(sibling.tagName())) group++;
		    
		    if ((! "h4".equals(sibling.tagName())) && ("program-set".equals(sibling.className())))
		    {
		    	//System.out.println(groups.get(group)+" For Major: " + this.forProgram);
		    	
		    	Elements moreSiblings = sibling.children();
		    	
		    	for (int j = 0; j < moreSiblings.size(); j++)
		    	{
		    		Element innerSibling = moreSiblings.get(j);
		    		if ( "li".equals(innerSibling.tagName()) )
		    		{
		    			Course new_course = new Course(innerSibling.text());
		    			
		    			this.groups.get(group).courses.add(new_course.name_short);
		    			
		    			array_index++;
		    		}
		    				  
		    	}
		    	
		    }
		    
		 }
		
	}

	public String getURL() 
	{
		return URL;
	}

	public void setURL(String uRL) 
	{
		URL = uRL;
	}

	public String getForProgram() 
	{
		return forProgram;
	}

	public void setForProgram(String forProgram) 
	{
		this.forProgram = forProgram;
	}

}
