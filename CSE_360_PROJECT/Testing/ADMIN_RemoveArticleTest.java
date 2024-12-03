package Testing;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Before;

import AdminPage.*;
public class ADMIN_RemoveArticleTest {

@Test
public void WR1() throws SQLException
{
	String result = AdminPageHandler.DeleteArticleTest("");
	assertEquals("Title cannot be empty", result);
}

@Test
public void WR2() throws SQLException
{
	String result = AdminPageHandler.DeleteArticleTest("Not an Article");
	assertEquals("Article title not found", result);
}

@Test
public void WR3() throws SQLException
{
	String result = AdminPageHandler.DeleteArticleTest("Article");
	assertEquals("Article has been Deleted", result);
}
	
}
