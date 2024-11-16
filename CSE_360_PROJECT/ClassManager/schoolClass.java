package ClassManager;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class schoolClass {

	private int id;
	private int generalGroupId;
	private String name;
	private String instructor;
	private List<String> Students;
	private String category;
	
	
	public void schoolClass( String name,String instructor, String category)
	{
	
		this.name = name;
		this.instructor = instructor;
		this.category = category;
		this.Students = new ArrayList<>();
	}
	
	public void addStudent(String s) {
		this.Students.add(s);
	}
	
	
	
	
	// SETTERS AND GETTERS
	
	public String getCategory()
	{
		return category;
	}
	
	
	
	public String getName()
	{
		return name;
	}
	
	public String getInstructor()
	{
		return instructor;
	}
	

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCategory(String category)
	{
		this.category = category;
		
	}

	public void setGeneralID(int generalGroupId) {
		this.generalGroupId = generalGroupId;
		
	}

	public int getGeneralGroupId() {
		return generalGroupId;
	}
	public void setID(int id)
	{
		this.id = id;
	}
	public int getID()
	{
		return id;
	}
}
