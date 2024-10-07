
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
					+ "password VARCHAR(255), "
					+ "role VARCHAR(20))";
			statement.execute(userTable);
		}
		
		//registers the users into the data base
		public void register(String FirstName, String PreferredName, String MiddleName, String lastName, String email, String password, String role) throws SQLException {
		    String insertUser = "INSERT INTO users (FirstName, PreferredName, MiddleName, LastName, email, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
		    try (PreparedStatement pstmt = connection.prepareStatement(insertUser)) {
		        pstmt.setString(1, FirstName);
		        pstmt.setString(2, PreferredName);
		        pstmt.setString(3, MiddleName);
		        pstmt.setString(4, lastName);
		        pstmt.setString(5, email);
		        pstmt.setString(6, password);
		        pstmt.setString(7, role);
		        pstmt.executeUpdate();
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
		//returns connection
		public Connection getConnection()
		{
			return connection;
		}
}
