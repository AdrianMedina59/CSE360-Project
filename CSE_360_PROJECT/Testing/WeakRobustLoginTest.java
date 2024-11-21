/**
 * <p> Main class for continue login. </p>
 * 
 * <p> Description: Conducts the Weak Normal JUnit testing for the Login handler.</p>
 * 
 * <p> Copyright: Yash Mulimani © 2024 </p>
 * 
 * @author Yash Mulimani
 * 
 * @version 1.00		2024-11-20
 *  
 */
package Testing;

import org.junit.Test;

import LoginPage.LoginHandler;

import org.junit.Before;
import static org.junit.Assert.*;

public class WeakRobustLoginTest {
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "password123";
    
    @Test
    public void WR1() {
        String result = LoginHandler.verifyLogin("", VALID_PASSWORD);
        assertEquals("Username cannot be empty", result);
    }

    @Test
    public void WR2() {
        String result = LoginHandler.verifyLogin(VALID_USERNAME, "");
        assertEquals("Password cannot be empty", result);
    }

    @Test
    public void WR3() {
        String result = LoginHandler.verifyLogin(null, VALID_PASSWORD);
        assertEquals("Username cannot be empty", result);
    }

    @Test
    public void WR4() {
        String result = LoginHandler.verifyLogin(VALID_USERNAME, null);
        assertEquals("Password cannot be empty", result);
    }

    @Test
    public void WR5() {
        String result = LoginHandler.verifyLogin("adm", VALID_PASSWORD);
        assertEquals("Username must be at least 4 characters long", result);
    }

    @Test
    public void WR6() {
        String result = LoginHandler.verifyLogin("a", VALID_PASSWORD);
        assertEquals("Username must be at least 4 characters long", result);
    }

    @Test
    public void WR7() {
        String result = LoginHandler.verifyLogin(VALID_USERNAME, "pass123");
        assertEquals("Password must be at least 8 characters long", result);
    }

    @Test
    public void WR8() {
        String result = LoginHandler.verifyLogin(VALID_USERNAME, "p");
        assertEquals("Password must be at least 8 characters long", result);
    }

    @Test
    public void WR9() {
        String result = LoginHandler.verifyLogin("admin@123", VALID_PASSWORD);
        assertEquals("Username must contain only letters and numbers", result);
    }

    @Test
    public void WR10() {
        String result = LoginHandler.verifyLogin("admin 123", VALID_PASSWORD);
        assertEquals("Username must contain only letters and numbers", result);
    }

    @Test
    public void WR11() {
        String result = LoginHandler.verifyLogin("admin.123", VALID_PASSWORD);
        assertEquals("Username must contain only letters and numbers", result);
    }

    @Test
    public void WR12() {
        String result = LoginHandler.verifyLogin("admin#123", VALID_PASSWORD);
        assertEquals("Username must contain only letters and numbers", result);
    }

    @Test
    public void WR13() {
        String result = LoginHandler.verifyLogin("admin😊", VALID_PASSWORD);
        assertEquals("Username must contain only letters and numbers", result);
    }

    @Test
    public void WR141() {
        String result = LoginHandler.verifyLogin("adminé", VALID_PASSWORD);
        assertEquals("Username must contain only letters and numbers", result);
    }

    @Test
    public void WR14() {
        // Testing with an extremely long username
        String longUsername = "a".repeat(100);
        String result = LoginHandler.verifyLogin(longUsername, VALID_PASSWORD);
        assertEquals("Username must contain only letters and numbers", result);
    }

    @Test
    public void WR15() {
        // Testing with an extremely long password
        String longPassword = "a".repeat(100);
        String result = LoginHandler.verifyLogin(VALID_USERNAME, longPassword);
        assertEquals("Invalid username or password", result);
    }

    @Test
    public void WR16() {
        String result = LoginHandler.verifyLogin(VALID_USERNAME, "password 123");
        assertEquals("Invalid username or password", result);
    }

    @Test
    public void WR17() {
        String result = LoginHandler.verifyLogin(VALID_USERNAME, VALID_PASSWORD);
        assertEquals("", result);
    }

    @Test
    public void WR18() {
        // Testing with exactly 4 characters (boundary case)
        String result = LoginHandler.verifyLogin("user", VALID_PASSWORD);
        assertEquals("Invalid username or password", result);
    }

    @Test
    public void WR19() {
        // Testing with exactly 8 characters (boundary case)
        String result = LoginHandler.verifyLogin(VALID_USERNAME, "exactly8");
        assertEquals("Invalid username or password", result);
    }
}