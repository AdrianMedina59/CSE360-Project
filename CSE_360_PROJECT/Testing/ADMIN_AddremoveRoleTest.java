package Testing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Before;

import AdminPage.*;
public class ADMIN_AddremoveRoleTest {

	@Test
	public void WR1() throws SQLException
	{
		String result = AdminPageHandler.changeRoleTest("", "Student");
		assertEquals("Username cannot be empty", result);
	}
	
	@Test
	public void W2() throws SQLException
	{
		String result = AdminPageHandler.changeRoleTest("Noah", "");
		assertEquals("Role cannot be empty", result);
	}
	
	@Test
	public void W3() throws SQLException
	{
		String result = AdminPageHandler.changeRoleTest("Adam", "Instructor");
		assertEquals("Username is not found in database!", result);
	}
	
	@Test
	public void W4() throws SQLException
	{
		String result = AdminPageHandler.changeRoleTest("Noah", "Instructor");
		assertEquals("Role has been changed", result);
	}
}
