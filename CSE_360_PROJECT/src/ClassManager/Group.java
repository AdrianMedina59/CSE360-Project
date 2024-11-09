package ClassManager;

import java.util.List;

import javax.swing.plaf.synth.SynthColorChooserUI;

public class Group {
	private int id;
	private String name;
	private List<schoolClass> classes;
	
	public void addClass(schoolClass c) {
		this.classes.add(c);
	}

	
	//GETTERS AND SETTERS
	public void setId(int id)
	{
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
}
