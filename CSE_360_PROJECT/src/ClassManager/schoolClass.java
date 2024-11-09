package ClassManager;

import java.util.StringJoiner;

public class schoolClass {

	private int id;
	private String name;
	private Group category;
	
	
	public void schoolClass(int id, String name, Group category)
	{
		this.id = id;
		this.name = name;
		this.category = category;
	}
	
	
	
	
	
	
	// SETTERS AND GETTERS
	
	public Group getCategory()
	{
		return category;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	
	public void setId(int id)
	{
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCategory(Group category)
	{
		this.category = category;
		
	}
}
