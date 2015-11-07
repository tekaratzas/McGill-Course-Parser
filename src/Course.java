import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

public class Course {
	
	public String name_short;
	public String name_long="";
	public String overview;
	
	public String[] pre_requisites;
	public String[] co_requisites;
	public String[] restrictions;
	
	public int credits;
	//, String name_long, String overview, String[] pre_requisites,
	//String[] co_requisites,String[] restrictions, int credits
	
	public Course(String name_short)
	{
		String[] course =  name_short.split((Pattern.quote("(")));
		String[] name = course[0].split(" ");
		this.name_short = name[0] + " " +  name[1];
		
		for(int i = 2; i<name.length; i++)
		{
			this.name_long += name[i] + " ";
		}
		this.credits = course[1].charAt(0) - '0';
	}
	
	
	
	
	
	
	
	
	
	public void parse() throws IOException
	{
		Document doc = Jsoup.connect("https://www.mcgill.ca/study/2015-2016/faculties/engineering/undergraduate/programs/bachelor-engineering-beng-electrical-engineering").get();
		
		ArrayList<Course> classes = new ArrayList<Course>();
		
		Elements classes_el = doc.getElementsByClass("program-course-title");
		
		for(Element course : classes_el)
		{
			Course new_class= new Course(course.text());
			classes.add(new_class);
		}
		
		Elements desc = doc.select(".program-course-description-inner p");
		int index = 0;
		int array_index =0;
		int track=1;
		
		for(Element over : desc)
		{
			if(over.text().equals("Administered by: Centre for Continuing Education")) track++;
			if(index%4 == track)
			{
				if(array_index<classes.size()) classes.get(array_index).overview = over.text();
				array_index++;
			}
			
			index++;
		}
		
		
		array_index = 0;
		  Element firstH1 = doc.select("h4").get(1);
		  Elements siblings = firstH1.siblingElements();
		  List<Element> elementsBetween = new ArrayList<Element>();
		  int group = 1;
		  for (int i = 1; i < siblings.size(); i++) 
		  {
		    Element sibling = siblings.get(i);
		    
		    if("h4".equals(sibling.tagName())) group++;
		    
		    if ((! "h4".equals(sibling.tagName())) && ("program-set".equals(sibling.className())))
		    {
		    	Elements moreSiblings = sibling.children();
		    	for (int j = 0; j < moreSiblings.size(); j++)
		    	{
		    		Element innerSibling = moreSiblings.get(j);
		    		if ( "li".equals(innerSibling.tagName()) )
		    		{
		    			//if(array_index<classes.size())classes.get(array_index).group = doc.select("h4").get(group).text();
		    			array_index++;
		    		}
		    				 
		    	}
		    }
		    
		  }
		 
		for(Course cou : classes)
		{
			System.out.println("Class: "+cou.name_short + " overview: "+cou.overview);
		}
		
		String json = new Gson().toJson(classes);
		
		System.out.println(json);
	}
	
}
