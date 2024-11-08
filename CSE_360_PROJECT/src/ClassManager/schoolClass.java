package ClassManager;

import java.util.StringJoiner;

public class schoolClass {

	private int id;
	private String name;
	private Category category;
	
	
	public void schoolClass(int id, String name, Category category)
	{
		this.id = id;
		this.name = name;
		this.category = category;
	}
	
	
	
	
	
	
	// SETTERS AND GETTERS
	
	public Category getCategory()
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
	public void setCategory(Category category)
	{
		this.category = category;
		
	}
}
