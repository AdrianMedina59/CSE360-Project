package ClassManager;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.VoiceStatus;
import javax.swing.plaf.synth.SynthColorChooserUI;

public class Group {
	private int id;
	private String name;
	private List<schoolClass> classes;
	private String adminInstructor;
	
	public Group()
	{
		this.classes = new ArrayList<>();
	}
	
	
	
	public void addClass(schoolClass c) {
		this.classes.add(c);
	}

	
	//GETTERS AND SETTERS
	public void setAdminInstructor(String name) {
		this.adminInstructor = name;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdminInstructor()
	{
		return adminInstructor;
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
