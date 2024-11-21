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


import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Weak Normal testing class for LoginHandler
 * Tests valid input values only, one variable at a time
 * Enhanced with more comprehensive valid input testing
 */
public class WeakNormalLoginTest {
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "password123";
    
    @Test
    public void WN1() {
        String result = LoginHandler.verifyLogin(VALID_USERNAME, VALID_PASSWORD);
        assertEquals("", result);
    }

    @Test
    public void WN2() {
        String result = LoginHandler.verifyLogin(VALID_USERNAME, "wrongpassword");
        assertEquals("Invalid username or password", result);
    }

    @Test
    public void WN3() {
        String result = LoginHandler.verifyLogin("wronguser", VALID_PASSWORD);
        assertEquals("Invalid username or password", result);
    }

    @Test
    public void WN4() {
        // Testing with a valid username and a longer valid password
        String result = LoginHandler.verifyLogin(VALID_USERNAME, "password123456");
        assertEquals("Invalid username or password", result);
    }

    @Test
    public void WN5() {
        // Testing with a longer valid username format
        String result = LoginHandler.verifyLogin("administrator", VALID_PASSWORD);
        assertEquals("Invalid username or password", result);
    }

    @Test
    public void WN6() {
        // Testing with a valid numeric username
        String result = LoginHandler.verifyLogin("12345678", VALID_PASSWORD);
        assertEquals("Invalid username or password", result);
    }

    @Test
    public void WN7() {
        // Testing with a valid alphanumeric username
        String result = LoginHandler.verifyLogin("admin123", VALID_PASSWORD);
        assertEquals("Invalid username or password", result);
    }

    @Test
    public void WN8() {
        // Testing with uppercase valid username
        String result = LoginHandler.verifyLogin("ADMIN", VALID_PASSWORD);
        assertEquals("Invalid username or password", result);
    }

    @Test
    public void WN9() {
        // Testing with mixed case valid username
        String result = LoginHandler.verifyLogin("Admin", VALID_PASSWORD);
        assertEquals("Invalid username or password", result);
    }

    @Test
    public void WN10() {
        // Testing with exactly 4 character username (minimum valid length)
        String result = LoginHandler.verifyLogin("user", VALID_PASSWORD);
        assertEquals("Invalid username or password", result);
    }
}