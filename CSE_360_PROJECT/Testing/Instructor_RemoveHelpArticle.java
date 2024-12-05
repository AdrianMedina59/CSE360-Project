package Testing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import AdminPage.AdminPageHandler;
import InstructorPage.InstructorPageHandler;

public class Instructor_RemoveHelpArticle 
{
	public void WR1() throws Exception
	{
		String result = InstructorPageHandler.DeleteHelpArticleTest("");
		assertEquals("Title cannot be empty", result);
	}

	@Test
	public void WR2() throws Exception
	{
		String result = InstructorPageHandler.DeleteHelpArticleTest("No Article");
		assertEquals("Article title not found", result);
	}

	@Test
	public void WR3() throws Exception
	{
		String result = InstructorPageHandler.DeleteHelpArticleTest("Article");
		assertEquals("Article has been Deleted", result);
	}
		
	
}



