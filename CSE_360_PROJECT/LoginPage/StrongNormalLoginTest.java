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
package LoginPage;

import org.junit.Test;
import static org.junit.Assert.*;

public class StrongNormalLoginTest {

    @Test
    public void SN1_validAdminCredentials() {
        // Test correct username and password
        String result = LoginHandler.verifyLogin("admin", "password123");
        assertEquals("", result); // Expect no error for valid credentials
    }

    @Test
    public void SN2_invalidUsername() {
        // Test username with exactly 4 characters but not valid
        String result = LoginHandler.verifyLogin("user", "password123");
        assertEquals("Invalid username or password", result); // Username doesn't match "admin"
    }

    @Test
    public void SN3_shortPassword() {
        // Test password with exactly 8 characters but not matching
        String result = LoginHandler.verifyLogin("admin", "Pass1234");
        assertEquals("Invalid username or password", result); // Password doesn't match "password123"
    }

    @Test
    public void SN4_invalidUsernamesWithValidPassword() {
        // Test different valid alphanumeric usernames but incorrect for the system
        String[] invalidUsernames = {
            "admin123", "Admin123", "123admin", "adminADMIN"
        };

        for (String username : invalidUsernames) {
            String result = LoginHandler.verifyLogin(username, "password123");
            assertEquals("Invalid username or password", result);
        }
    }
    
    @Test
    public void SN5_emptyUsername() {
        // Test empty username
        String result = LoginHandler.verifyLogin("", "password123");
        assertEquals("Username cannot be empty", result);
    }

    @Test
    public void SN6_emptyPassword() {
        // Test empty password
        String result = LoginHandler.verifyLogin("admin", "");
        assertEquals("Password cannot be empty", result);
    }

    @Test
    public void SN7_invalidCharacterInUsername() {
        // Test username with invalid characters
        String result = LoginHandler.verifyLogin("adm!n", "password123");
        assertEquals("Username must contain only letters and numbers", result);
    }

    @Test
    public void SN8_shortUsername() {
        // Test username shorter than 4 characters
        String result = LoginHandler.verifyLogin("adm", "password123");
        assertEquals("Username must be at least 4 characters long", result);
    }
}
