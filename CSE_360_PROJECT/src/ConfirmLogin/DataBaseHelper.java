package ConfirmLogin;
/**
 * <p> DataBaseHelper class. </p>
 * 
 * <p> Description: This is the main database class that communicates with the data base uses SQL.</p>
 * 
 * <p> Copyright: Adrian Medina © 2024 </p>
 * 
 * @author Adrian Medina
 * 
 * @version 1.00		2024-10-06
 *  
 */



import java.awt.TextField;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class DataBaseHelper {
	
	// JDBC driver name and database URL 
		static final String JDBC_DRIVER = "org.h2.Driver";   
		static final String DB_URL = "jdbc:h2:~/DataBase"; 
		private Statement statement = null; 
		private Connection connection = null;
		
	//  Database credentials 
		static final String USER = "sa"; 
		static final String PASS = ""; 
		
		
		//Method that connects the database
		public void connectToDatabase() throws SQLException {
			try {
				Class.forName(JDBC_DRIVER); // Load the JDBC driver
				System.out.println("Connecting to database...");
				connection = DriverManager.getConnection(DB_URL, USER, PASS);
				statement = connection.createStatement(); 
				createTables();  // Create the necessary tables if they don't exist
				createPasscodeTable();
			} catch (ClassNotFoundException e) {
				System.err.println("JDBC Driver not found: " + e.getMessage());
			}
		}
		
		//creates table for database which houses the password, names and email
		private void createTables() throws SQLException {
			String userTable = "CREATE TABLE IF NOT EXISTS users ("
					+ "id INT AUTO_INCREMENT PRIMARY KEY, "
					+ "FirstName VARCHAR(255) NOT NULL, "
					+ "PreferredName VARCHAR(255) NOT NULL, "
					+ "MiddleName VARCHAR(255) NOT NULL, "
					+ "LastName VARCHAR(255) NOT NULL, "
					+ "email VARCHAR(255) UNIQUE, "
					+ "username VARCHAR(255) UNIQUE, "
					+ "password VARCHAR(255), "
					+ "role VARCHAR(20))";
			statement.execute(userTable);
		}
		
		// Method to update a user's role based on their username
		public boolean updateUserRole(String username, String newRole) throws SQLException {
		    // SQL query to update the user's role
		    String updateSQL = "UPDATE users SET role = ? WHERE username = ?";
		    
		    try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
		        pstmt.setString(1, newRole); // Set the new role
		        pstmt.setString(2, username); // Set the username

		        // Execute the update statement
		        int affectedRows = pstmt.executeUpdate();

		        // If affectedRows is greater than 0, it means the role was updated
		        return affectedRows > 0;
		    }
		}
		
		
		 // Create the passcodes table if it does not exist
	    private void createPasscodeTable() throws SQLException {
	        String createTableSQL = "CREATE TABLE IF NOT EXISTS passcodes ("
	                                + "id INT AUTO_INCREMENT PRIMARY KEY, "
	                                + "category VARCHAR(255), "
	                                + "passcode VARCHAR(255)"
	                                + ")";
	        try (Statement stmt = connection.createStatement()) {
	            stmt.execute(createTableSQL);
	        }
	    }
	 // Method to get the number of users in the database
	    public int getNumberOfUsers() throws SQLException {
	        String query = "SELECT COUNT(*) AS total FROM users";
	        try (Statement stmt = connection.createStatement();
	             ResultSet resultSet = stmt.executeQuery(query)) {
	            if (resultSet.next()) {
	                return resultSet.getInt("total"); // Get the count of users
	            }
	        }
	        return 0; // Return 0 if no users are found or in case of an issue
	    }
	 // Insert a new passcode into the passcodes table
	    public void insertPasscode(String category, String passcode) throws SQLException {
	        String insertSQL = "INSERT INTO passcodes (category, passcode) VALUES (?, ?)";
	        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
	            pstmt.setString(1, category);
	            pstmt.setString(2, passcode);
	            pstmt.executeUpdate();
	        }
	    }
		
		
	    // Retrieve a passcode by category
	    public String getPasscodeByCategory(String category) throws SQLException {
	        String selectSQL = "SELECT passcode FROM passcodes WHERE category = ?";
	        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
	            pstmt.setString(1, category);
	            ResultSet resultSet = pstmt.executeQuery();
	            if (resultSet.next()) {
	                return resultSet.getString("passcode");
	            }
	        }
	        return null;  // Return null if no passcode is found
	    }
	    
	 // Debugging method to print all passcodes
	    public void printPasscodes() throws SQLException {
	        String selectSQL = "SELECT * FROM passcodes";
	        try (Statement stmt = connection.createStatement()) {
	            ResultSet resultSet = stmt.executeQuery(selectSQL);
	            while (resultSet.next()) {
	                System.out.println("ID: " + resultSet.getInt("id")
	                        + ", Category: " + resultSet.getString("category")
	                        + ", Passcode: " + resultSet.getString("passcode"));
	            }
	        }
	    }
	    
	    // Delete a passcode by category
	    public void deletePasscode(String category) throws SQLException {
	        String deleteSQL = "DELETE FROM passcodes WHERE category = ?";
	        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
	            pstmt.setString(1, category);
	            pstmt.executeUpdate();
	        }
	    }
	    public boolean validPasscode(String passcode) throws SQLException {
	        String query = "SELECT passcode FROM passcodes WHERE passcode = ?";
	        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	            pstmt.setString(1, passcode);
	            ResultSet resultSet = pstmt.executeQuery();

	            // If the resultSet contains a result, the passcode exists
	            return resultSet.next();
	        }
	    }
	    public boolean hasPasscodes() {
	        // Implement your database query to check if there are passcodes
	        // Return true if there are passcodes, otherwise return false
	        try (Connection connection = getConnection(); // Your method to get a DB connection
	             Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM passcodes")) {
	             
	            if (resultSet.next()) {
	                return resultSet.getInt(1) > 0; // If count is greater than 0, return true
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false; // Default to false if an exception occurs
	    }
	    
	    public void deleteOnePasscode() throws SQLException {
	        String deleteSQL = "DELETE FROM passcodes WHERE id = (SELECT id FROM passcodes LIMIT 1)";
	        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
	            pstmt.executeUpdate();
	        }
	    }
	    
	    public ResultSet getPasscodes() throws SQLException {
	        String query = "SELECT category, passcode FROM passcodes";
	        return statement.executeQuery(query);
	    }
	    
	 // Update a passcode by category
	    public void updatePasscode(String category, String newPasscode) throws SQLException {
	        String updateSQL = "UPDATE passcodes SET passcode = ? WHERE category = ?";
	        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
	            pstmt.setString(1, newPasscode);
	            pstmt.setString(2, category);
	            pstmt.executeUpdate();
	        }
	    }
		//registers the users into the data base
		public void register(String FirstName, String PreferredName, String MiddleName, String lastName, String email, String username,String password, String role) throws SQLException {
		    String insertUser = "INSERT INTO users (FirstName, PreferredName, MiddleName, LastName, email,username, password, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		    try (PreparedStatement pstmt = connection.prepareStatement(insertUser)) {
		        pstmt.setString(1, FirstName);
		        pstmt.setString(2, PreferredName);
		        pstmt.setString(3, MiddleName);
		        pstmt.setString(4, lastName);
		        pstmt.setString(5, email);
		        pstmt.setString(6, username);
		        pstmt.setString(7, password);
		        pstmt.setString(8, role);
		        pstmt.executeUpdate();
		    }
		}

		// Method to check if a given passcode is in the "reset" category
		public boolean isPasscodeInResetCategory(String passcode) throws SQLException {
		    String query = "SELECT COUNT(*) FROM passcodes WHERE passcode = ? AND category = 'reset'";
		    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        // Set the passcode parameter in the query
		        pstmt.setString(1, passcode);

		        // Execute the query
		        ResultSet resultSet = pstmt.executeQuery();

		        if (resultSet.next()) {
		            // If count is greater than 0, the passcode exists in the "reset" category
		            return resultSet.getInt(1) > 0;
		        }
		    }

		    // Return false if no match found
		    return false;
		}

		// Method to update the password for a specified user
		public boolean updatePassword(String username, String newPassword) throws SQLException {
		    // SQL query to update the user's password
		    String updateSQL = "UPDATE users SET password = ? WHERE username = ?";
		    
		    try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
		        pstmt.setString(1, newPassword); // Set the new password
		        pstmt.setString(2, username);     // Set the username

		        // Execute the update statement
		        int affectedRows = pstmt.executeUpdate();

		        // If affectedRows is greater than 0, it means the password was updated
		        return affectedRows > 0;
		    }
		}
		
		// Check if the database is empty
		public boolean isDatabaseEmpty() throws SQLException {
			String query = "SELECT COUNT(*) AS count FROM users";
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next()) {
				return resultSet.getInt("count") == 0;
			}
			return true;
		}
		
		//closing connection to data base
		public void closeConnection() {
			try{ 
				if(statement!=null) statement.close(); 
			} catch(SQLException se2) { 
				se2.printStackTrace();
			} 
			try { 
				if(connection!=null) connection.close(); 
			} catch(SQLException se){ 
				se.printStackTrace(); 
			} 
		}
		
		// Method to get all users from the database
		public ResultSet getUsers() throws SQLException {
		    String query = "SELECT * FROM users";
		    // Execute the query and return the ResultSet
		    return statement.executeQuery(query);
		}
		//returns connection
		public Connection getConnection()
		{
			return connection;
		}
		
		//prints out the whole data base for debugging
		public void PrintUserTables() throws SQLException{
			String query = "Select * FROM users";
			try(Statement stmt = connection.createStatement();
				ResultSet resultSet = stmt.executeQuery(query)){
				
				System.out.println("Users Tabel: ");
				System.out.println("ID | FirstName | PreferredName | MiddleName | LastName | Email | Username| password | Role");
				
				while(resultSet.next()) {
					int id = resultSet.getInt("id");
					String firstName = resultSet.getString("FirstName");
					String preferredName = resultSet.getString("PreferredName");
			        String middleName = resultSet.getString("MiddleName");
			        String lastName = resultSet.getString("LastName");
			        String email = resultSet.getString("email");
			        String username = resultSet.getString("username");
			        String password = resultSet.getString("password");
			        String role = resultSet.getString("role");
			        
			        System.out.printf("%d | %s | %s | %s | %s | %s | %s| %s | %s%n",
		                    id, firstName, preferredName, middleName, lastName, email, username,password, role);

				}
			}
		}
		
		
		// Method to validate user credentials
		public boolean isValidUser(String username, String password) throws SQLException {
		    String query = "SELECT * FROM users WHERE username = ? AND password = ?";
		    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, username);
		        pstmt.setString(2, password);
		        ResultSet resultSet = pstmt.executeQuery();

		        // If there's a result, the username and password are valid
		        return resultSet.next();
		    }
		}
		
		//method gets the username in database
		public String getUser(String username) throws SQLException {
		    String query = "SELECT username FROM users WHERE username = ?";
		    
		    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, username);  // Set the username in the query
		        ResultSet resultSet = pstmt.executeQuery();
		        
		        if (resultSet.next()) {
		            // Return the found username
		            return resultSet.getString("username");
		        } else {
		            // Return null or a message if the user is not found
		            return "User not found.";
		        }
		    }
		}
		
		public boolean deleteUser(String username) throws SQLException {
		    String query = "DELETE FROM users WHERE username = ?";
		    
		    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, username);  // Set the username in the query
		        int affectedRows = pstmt.executeUpdate();  // Execute the delete statement
		        
		        // If affectedRows is greater than 0, it means a user was deleted
		        return affectedRows > 0;
		    }
		}
		

		public String getRole(String User) throws SQLException {
			String query = "SELECT role FROM users WHERE username = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, User);  // Set the username in the query
		        ResultSet resultSet = pstmt.executeQuery();
		        
		        if (resultSet.next()) {
		            // Return the role if the user is found
		            return resultSet.getString("role");
		        } else {
		            // Return null or throw an exception if the user is not found
		            return null;
		        }
		    }
		}
		
		
		
		//method is used to retrieve first name with give user name
		public String getFirstNameByUsername(String username) throws SQLException {
		    String query = "SELECT FirstName FROM users WHERE username = ?";
		    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, username);  // Set the username in the query
		        ResultSet resultSet = pstmt.executeQuery();
		        
		        if (resultSet.next()) {
		            // Return the first name if the user is found
		            return resultSet.getString("FirstName");
		        } else {
		            // Return a message or null if the user is not found
		            return "User not found.";
		        }
		    }
		}
}
