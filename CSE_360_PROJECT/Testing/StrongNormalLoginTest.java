/**
 * <p> Main class for continue login. </p>
 * 
 * <p> Description: Conducts the Strong Normal JUnit testing for the Login handler.</p>
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

import static org.junit.Assert.*;

public class StrongNormalLoginTest {
    
    private static final String[] VALID_USERNAMES = {
        "admin",
        "user123",
        "testuser",
        "Admin123"
    };

    private static final String[] VALID_PASSWORDS = {
        "password123",
        "securePass123",
        "testPassword123",
        "Pass12345678"
    };

    @Test
    public void SN1() {
        // Test all combinations of valid usernames and passwords
        for (String username : VALID_USERNAMES) {
            for (String password : VALID_PASSWORDS) {
                String result = LoginHandler.verifyLogin(username, password);
                if (username.equals("admin") && password.equals("password123")) {
                    assertEquals("", result);
                } else {
                    assertEquals("Invalid username or password", result);
                }
            }
        }
    }

    @Test
    public void SN2() {
        // Test username with exactly 4 characters
        String result = LoginHandler.verifyLogin("user", "password123");
        assertEquals("Invalid username or password", result);
    }

    @Test
    public void SN3() {
        // Test password with exactly 8 characters
        String result = LoginHandler.verifyLogin("admin", "Pass1234");
        assertEquals("Password must be at least 8 characters long", result);
    }

    @Test
    public void SN4() {
        // Test different valid alphanumeric combinations
        String[] validUsernames = {
            "admin123",
            "Admin123",
            "123admin",
            "adminADMIN"
        };

        for (String username : validUsernames) {
            String result = LoginHandler.verifyLogin(username, "password123");
            assertEquals("Invalid username or password", result);
        }
    }
}