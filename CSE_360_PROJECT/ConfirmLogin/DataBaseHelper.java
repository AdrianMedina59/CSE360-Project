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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.NotActiveException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Article.Article;
import Article.helpArticle;
import ClassManager.Group;
import ClassManager.schoolClass;
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
				createArticlesTable();
				createHelpArticlesTable();
				createGeneralGroupsTable();
				createClassesTable();
				createClassStudentsTable();
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
		
		// Create the articles table if it doesn't exist

		

		private void createArticlesTable() throws SQLException {
		    String createTableSQL = "CREATE TABLE IF NOT EXISTS articles (" +
		                            "id INT AUTO_INCREMENT PRIMARY KEY, " +
		                            "title VARCHAR(255) NOT NULL, " +
		                            "Authors VARCHAR(1000), " +
		                            "abstractText VARCHAR(1000), " +
		                            "keywords VARCHAR(1000), " +
		                            "Body VARCHAR(1000), " +
		                            "references VARCHAR(1000), " +
		                            "role VARCHAR(20), " +
		                            "deleted BOOLEAN DEFAULT FALSE)";
		    
		    try (Statement stmt = connection.createStatement()) {
		        stmt.execute(createTableSQL);
		    }
		}
		private void createHelpArticlesTable() throws SQLException{
			String createTableSQL = "CREATE TABLE IF NOT EXISTS help_articles (" +
									"id INT AUTO_INCREMENT PRIMARY KEY, " +
									"title VARCHAR(255) NOT NULL, " +
									"Body VARCHAR(1000), " +
		                            "role VARCHAR(20), " +
		                            "deleted BOOLEAN DEFAULT FALSE)";
			try (Statement stmt = connection.createStatement()) {
		        stmt.execute(createTableSQL);
		        System.out.println("is it working");
		    }
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
		
		
		

		public void insertArticle(Article article, String role) throws Exception {
		    String insertSQL = "INSERT INTO articles (title, authors, abstractText, keywords, Body, references, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
		    try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
		        pstmt.setString(1, article.getTitle());
		        pstmt.setString(2, String.join(",", article.getAuthors()));
		        pstmt.setString(3, article.getAbstractText());
		        pstmt.setString(4, String.join(",", article.getKeywords()));
		        pstmt.setString(5, article.getBody());
		        pstmt.setString(6, String.join(",", article.getReferences()));
		        pstmt.setString(7, role); // Set the role
		        pstmt.executeUpdate();
		        System.out.println("Inserting article with title: " + article.getTitle());
		        System.out.println("Article Body: " + article.getBody());
		    }
		}
		
		
		public void insertHelpArticle(helpArticle helpArticle, String role) throws Exception {
		    String insertSQL = "INSERT INTO help_articles (title, body, role) VALUES (?, ?, ?)";
		    try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
		        pstmt.setString(1, helpArticle.getTitle());
		        pstmt.setString(2, helpArticle.getBody());
		        pstmt.setString(3, role); // Set the role
		        pstmt.executeUpdate();
		        System.out.println("Inserting help article with title: " + helpArticle.getTitle());
		        System.out.println("Help article Body: " + helpArticle.getBody());
		        System.out.println("hi guys");
		    }

		}

		public int getUserIdByName(String fullName) throws SQLException {
		    String query = "SELECT id FROM users WHERE CONCAT(FirstName, ' ', LastName) = ?";
		    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, fullName);
		        ResultSet rs = pstmt.executeQuery();
		        if (rs.next()) {
		            return rs.getInt("id");
		        }
		    }
		    return -1; // Return -1 if not found
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
	    
	    /*
	     * 
	     *  THIS PORTION DEALS WITH CLASSES AND GROUPS
	     * 
	     */
	    
	    // method to create the GeneralGroups table
	    private void createGeneralGroupsTable() throws SQLException {
	        String createTableSQL = "CREATE TABLE IF NOT EXISTS generalGroups (" +
	                                "id INT AUTO_INCREMENT PRIMARY KEY, " +
	                                "AdminInstructor VARCHAR(255), " +
	                                "name VARCHAR(255) NOT NULL UNIQUE)";
	        System.out.println("Executing table creation query: " + createTableSQL);
	        statement.execute(createTableSQL);
	        System.out.println("Table created successfully (if it didn't already exist).");
	    }
	    public String getGroupNameByAdminInstructor(String adminInstructor) throws SQLException {
	        String query = "SELECT name FROM generalGroups WHERE AdminInstructor = ? LIMIT 1";
	        String groupName = null;

	        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	            pstmt.setString(1, adminInstructor); 

	            try (ResultSet resultSet = pstmt.executeQuery()) {
	                if (resultSet.next()) {
	                    groupName = resultSet.getString("name"); // Get the group name
	                }
	            }
	        }

	        return groupName;
	    }

	    public int getGroupIdByAdminInstructor(String adminInstructor) throws SQLException {
	        String query = "SELECT id FROM generalGroups WHERE AdminInstructor = ?";
	        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	            pstmt.setString(1, adminInstructor);
	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getInt("id");
	                }
	            }
	        }
	        return -1; // Return -1 if no group found
	    }
	    //saving group in database function
	    public void saveGroup(Group group) throws SQLException {
	        String insertSQL = "INSERT INTO generalGroups (AdminInstructor,name) VALUES (?, ?)";
	        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
	            pstmt.setString(2, group.getName());
	            pstmt.setString(1, group.getAdminInstructor());
	            pstmt.executeUpdate();

	            // Retrieve the generated group ID
	            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    int groupId = generatedKeys.getInt(1);
	                    group.setId(groupId); // Set the ID for the group object
	                } else {
	                    throw new SQLException("Failed to insert group, no ID obtained.");
	                }
	            }
	        }
	    }
	    
	    public void printGeneralGroups() {
	        String query = "SELECT * FROM generalGroups";  // Query to select all rows from generalGroups
	        try (PreparedStatement pstmt = connection.prepareStatement(query);
	             ResultSet rs = pstmt.executeQuery()) {

	            // Print column names (for better understanding of the result)
	            System.out.println("id | AdminInstructor | name");
	            System.out.println("--------------------------------");

	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String adminInstructor = rs.getString("AdminInstructor");
	                String name = rs.getString("name");

	                // Print each row's data
	                System.out.printf("%d | %s | %s%n", id, adminInstructor, name);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	 // Method to create classes table
	    private void createClassesTable() throws SQLException {
	        String createTableSQL = "CREATE TABLE IF NOT EXISTS classes ("
	                + "id INT AUTO_INCREMENT PRIMARY KEY, "            // Auto-increment for sequential IDs
	                + "name VARCHAR(255) NOT NULL, "                   // Class name cannot be null
	                + "generalGroupId INT, "                           // Reference to generalGroups table
	                + "Instructor VARCHAR(255) NOT NULL, "				//Instructor
	                + "FOREIGN KEY (generalGroupId) REFERENCES generalGroups(id) " // Foreign key constraint
	                + "ON DELETE SET NULL ON UPDATE CASCADE)";         // Handle deletion and updates for FK
	        statement.execute(createTableSQL);
	    }
	    
	    public void saveClasses(schoolClass schoolClass) throws SQLException {
	        String insertSQL = "INSERT INTO classes (name, generalGroupId, Instructor) VALUES (?, ?, ?)";
	        
	        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
	            // Set the values for the prepared statement
	            pstmt.setString(1, schoolClass.getName());                 // Class name
	            pstmt.setInt(2, schoolClass.getGeneralGroupId());          // Group ID
	            pstmt.setString(3, schoolClass.getInstructor());       	   //instructor	
	            
	            // Execute the query
	            pstmt.executeUpdate();
	            
	            // Retrieve the auto-generated ID
	            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    int classId = generatedKeys.getInt(1);             // Get the generated class ID
	                    schoolClass.setID(classId);                       // Set the ID for the schoolClass object
	                } else {
	                    throw new SQLException("Failed to insert class, no ID obtained.");
	                }
	            }
	        }
	    }
	 // Method to get all class names from the database (modified from original function)
	    public List<String> getClassNamesByDepartment(int departmentId) throws SQLException {
	        List<String> classNames = new ArrayList<>();
	        String query = """
	            SELECT 
	                c.name AS className
	            FROM 
	                classes c
	            JOIN 
	                generalGroups g ON c.generalGroupId = g.id
	            WHERE 
	                g.id = ?;
	        """;

	        try (PreparedStatement pstmt = getConnection().prepareStatement(query)) {
	            // Set the department ID in the prepared statement
	            pstmt.setInt(1, departmentId);

	            // Execute the query
	            try (ResultSet resultSet = pstmt.executeQuery()) {
	                // Loop through the results and add class names to the list
	                while (resultSet.next()) {
	                    classNames.add(resultSet.getString("className"));
	                }
	            }
	        }

	        return classNames;
	    }
	    
	    public List<String> getInstructorClasses(String instructor) throws SQLException {
	        List<String> classList = new ArrayList<>();
	        String query = "SELECT name FROM classes WHERE Instructor = ?";

	        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	            pstmt.setString(1, instructor);
	            
	            try (ResultSet resultSet = pstmt.executeQuery()) {
	                while (resultSet.next()) {
	                    classList.add(resultSet.getString("name"));
	                }
	            }
	        }

	        return classList;
	    }
	    //returns a resultSet of students for departments
	    public ResultSet getStudentsByDepartment(int departmentId) throws SQLException {
	    	   String query = """
	    		        SELECT 
	    		            u.id AS studentId,
	    		            CONCAT(u.FirstName, ' ', u.LastName) AS studentName,
	    		            GROUP_CONCAT(c.name ORDER BY c.name ASC SEPARATOR ', ') AS enrolledClasses
	    		        FROM 
	    		            classStudents cs
	    		        JOIN 
	    		            users u ON cs.userId = u.id
	    		        JOIN 
	    		            classes c ON cs.classId = c.id
	    		        JOIN 
	    		            generalGroups g ON c.generalGroupId = g.id
	    		        WHERE 
	    		            g.id = ?
	    		        GROUP BY 
	    		            u.id, studentName
	    		        ORDER BY 
	    		            studentName ASC;
	    		    """;
	        PreparedStatement pstmt = connection.prepareStatement(query);
	        pstmt.setInt(1, departmentId);
	        return pstmt.executeQuery();
	    }
	    
	    public List<String> getStudentNames() throws SQLException {
	        List<String> studentNames = new ArrayList<>();
	        String query = "SELECT CONCAT(FirstName, ' ', LastName) AS fullName FROM users WHERE role = 'Student'";

	        try (PreparedStatement pstmt = getConnection().prepareStatement(query);
	             ResultSet resultSet = pstmt.executeQuery()) {
	            
	            while (resultSet.next()) {
	                studentNames.add(resultSet.getString("fullName"));
	            }
	        }

	        return studentNames;
	    }
	    public int getClassIdByName(String className) throws SQLException {
	        String query = "SELECT id FROM classes WHERE name = ?";
	        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	            pstmt.setString(1, className);
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) {
	                return rs.getInt("id");
	            }
	        }
	        return -1; // Return -1 if not found
	    }

	    public void removeClass(int classId) throws SQLException {
	        // First, delete the students from the class in the classStudents table
	        String deleteStudentsQuery = """
	            DELETE FROM classStudents WHERE classId = ?
	        """;

	        try (PreparedStatement pstmt = connection.prepareStatement(deleteStudentsQuery)) {
	            pstmt.setInt(1, classId);

	            int rowsDeleted = pstmt.executeUpdate();
	            if (rowsDeleted > 0) {
	                System.out.println("Successfully removed students from class with ID: " + classId);
	            } else {
	                System.out.println("No students found in class with ID: " + classId);
	            }
	        }

	        // Then, remove the class from the classes table
	        String deleteClassQuery = """
	            DELETE FROM classes WHERE id = ?
	        """;

	        try (PreparedStatement pstmt = connection.prepareStatement(deleteClassQuery)) {
	            pstmt.setInt(1, classId);

	            int rowsDeleted = pstmt.executeUpdate();
	            if (rowsDeleted > 0) {
	                System.out.println("Successfully removed class with ID: " + classId);
	            } else {
	                System.out.println("Class with ID: " + classId + " not found.");
	            }
	        }
	    }
	    
	    public ResultSet getClassesByDepartment(int departmentId) throws SQLException {
	        // SQL query to retrieve class ID, name, and instructor by department
	        String query = """
	            SELECT 
	                c.id AS classId, 
	                c.name AS className, 
	                c.Instructor AS instructorName
	            FROM 
	                classes c
	            JOIN 
	                generalGroups g ON c.generalGroupId = g.id
	            WHERE 
	                g.id = ?;
	        """;
	        
	        // Print the query being executed
	        System.out.println("Executing query: " + query);

	        // Prepare the statement to prevent SQL injection
	        PreparedStatement pstmt = connection.prepareStatement(query);
	        pstmt.setInt(1, departmentId);

	        // Execute the query and return the ResultSet
	        return pstmt.executeQuery();
	    }
	    public void printClassesTable() throws SQLException {
	        String query = "SELECT c.id, c.name, c.generalGroupId,c.Instructor, g.name AS groupName " +
	                       "FROM classes c " +
	                       "LEFT JOIN generalGroups g ON c.generalGroupId = g.id";

	        try (PreparedStatement pstmt = connection.prepareStatement(query);
	             ResultSet rs = pstmt.executeQuery()) {

	            System.out.println("Classes Table:");
	            System.out.println("ID\tClass Name\tGroup ID\tInstructor");

	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String className = rs.getString("name");
	                int groupId = rs.getInt("generalGroupId");
	                String groupName = rs.getString("groupName");
	                String instructor = rs.getString("Instructor");

	                System.out.printf("%d\t%s\t%d\t%s\t%s%n", id, className, groupId, groupName,instructor);
	            }
	        }
	    }
	    //method to create the students class table
	    private void createClassStudentsTable() throws SQLException {
	        String createTableSQL = "CREATE TABLE IF NOT EXISTS classStudents ("
	                + "id INT AUTO_INCREMENT PRIMARY KEY, "
	                + "userId INT NOT NULL, "
	                + "classId INT NOT NULL, "
	                + "FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE, "
	                + "FOREIGN KEY (classId) REFERENCES classes(id) ON DELETE CASCADE, "
	                + "UNIQUE(userId, classId))"; // Ensures no duplicate enrollments
	        try (PreparedStatement pstmt = connection.prepareStatement(createTableSQL)) {
	            pstmt.execute();
	            System.out.println("classStudents table created successfully.");
	        }
	    }
	    
	    public void enrollStudentInClass(int userId, int classId) throws SQLException {
	        String checkSQL = "SELECT COUNT(*) FROM classStudents WHERE userId = ? AND classId = ?";
	        String insertSQL = "INSERT INTO classStudents (userId, classId) VALUES (?, ?)";

	        try (PreparedStatement checkStmt = connection.prepareStatement(checkSQL);
	             PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {

	            // Check if the student is already enrolled in the class
	            checkStmt.setInt(1, userId);
	            checkStmt.setInt(2, classId);
	            ResultSet rs = checkStmt.executeQuery();
	            if (rs.next() && rs.getInt(1) > 0) {
	                System.out.println("Student is already enrolled in the class.");
	                return;
	            }

	            //enroll the student
	            insertStmt.setInt(1, userId);
	            insertStmt.setInt(2, classId);
	            insertStmt.executeUpdate();
	            System.out.println("Student enrolled successfully.");
	        }
	    }
	    
	    public void removeStudentFromClass(int studentId, int classId) throws SQLException {
	        // Query to delete the student from the specified class
	        String deleteQuery = """
	            DELETE FROM classStudents WHERE userId = ? AND classId = ?
	        """;

	        try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
	            pstmt.setInt(1, studentId);
	            pstmt.setInt(2, classId);

	            int rowsDeleted = pstmt.executeUpdate();
	            if (rowsDeleted > 0) {
	                System.out.println("Successfully removed student with ID " + studentId + " from class with ID: " + classId);
	            } else {
	                System.out.println("No association found for student ID " + studentId + " in class with ID: " + classId);
	            }
	        }
	    }

	    public List<String> getStudentsInClass(String className) throws SQLException {
	        List<String> studentNames = new ArrayList<>();

	        String query = "SELECT u.FirstName, u.LastName FROM classStudents cs " +
	                       "JOIN users u ON cs.userId = u.id " +
	                       "JOIN classes c ON cs.classId = c.id " +
	                       "WHERE c.name = ?";

	        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	            pstmt.setString(1, className);
	            ResultSet rs = pstmt.executeQuery();

	            while (rs.next()) {
	                String fullName = rs.getString("FirstName") + " " + rs.getString("LastName");
	                studentNames.add(fullName);
	            }
	        }
	        
	        return studentNames;
	    }
	    public void printClassStudentsTable() throws SQLException {
	        String query = """
	            SELECT cs.id, u.FirstName, u.LastName, c.name
	            FROM classStudents cs
	            JOIN users u ON cs.userId = u.id
	            JOIN classes c ON cs.classId = c.id
	        """;

	        try (Statement stmt = connection.createStatement();
	             ResultSet rs = stmt.executeQuery(query)) {
	            
	            System.out.println("ClassStudents Table:");
	            System.out.println("------------------------------------");
	            System.out.printf("%-5s %-20s %-20s%n", "ID", "Student Name", "Class Name");
	            System.out.println("------------------------------------");

	            while (rs.next()) {
	            	int id = rs.getInt("id");
	                String firstName = rs.getString("FirstName");
	                String lastName = rs.getString("LastName");
	                String className = rs.getString("name");

	                System.out.printf("%-20s %-20s %-20s %-20s\n", id, firstName, lastName, className);
	            }

	            System.out.println("------------------------------------");
	        }
	    }
	    /*
	     * 
	     * 
	     * 
	     */
	    
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
		
		public String getClassCategory(String username) throws SQLException {
		    String query = "SELECT classCategory FROM users WHERE username = ?";
		    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, username);  // Set the username in the query
		        ResultSet resultSet = pstmt.executeQuery();

		        if (resultSet.next()) {
		            return resultSet.getString("classCategory");  // Return the classCategory
		        } else {
		            return null; // User not found, handle accordingly
		        }
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
		
		public ResultSet getArticles() throws SQLException {
			String query = "SELECT * FROM articles WHERE deleted = FALSE";
			return statement.executeQuery(query);
		}
		
		public ResultSet getArticlesD() throws SQLException {
		    String query = "SELECT * FROM articles WHERE deleted = true";
		    Statement stmt = connection.createStatement();
		    return stmt.executeQuery(query);
		}
		
		
		public ResultSet getHelpArticles() throws SQLException {
		    String query = "SELECT * FROM help_articles WHERE deleted = FALSE"; // Exclude deleted articles
		    return statement.executeQuery(query);
		}
		
		// In DataBaseHelper.java
		public ResultSet getHelpArticlesD() throws SQLException {
		    String query = "SELECT * FROM help_articles WHERE deleted = true";
		    Statement stmt = connection.createStatement();
		    return stmt.executeQuery(query);
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
		
	    public boolean isValidArticle(String title, String keyword) throws SQLException
        {
            String query = "SELECT * FROM articles WHERE title LIKE ? AND keywords LIKE ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, "%" + title + "%");
                pstmt.setString(2, "%" + keyword+ "%");
                ResultSet resultSet = pstmt.executeQuery();

                //if there's a result, the username and password are valid

                return resultSet.next();
            }
        }
	    
	    public boolean isValidArticleTitle(String title) throws SQLException
        {
            String query = "SELECT * FROM articles WHERE title LIKE ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, "%" + title + "%");
                ResultSet resultSet = pstmt.executeQuery();

                //if there's a result, the username and password are valid

                return resultSet.next();
            }
        }
	    
	    public boolean isValidHelpArticleTitle(String title) throws SQLException
	    {
	    	String query = "SELECT * FROM help_articles WHERE title LIKE ?";
	    	try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, "%" + title + "%");
                ResultSet resultSet = pstmt.executeQuery();

                //if there's a result, the username and password are valid

                return resultSet.next();
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
		public String getUser(String username) throws SQLException 
		{
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
		
		
		
		public boolean deleteUser(String username) throws SQLException 
		{
		    String query = "DELETE FROM users WHERE username = ?";
		    
		    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, username);  // Set the username in the query
		        int affectedRows = pstmt.executeUpdate();  // Execute the delete statement
		        
		        // If affectedRows is greater than 0, it means a user was deleted
		        return affectedRows > 0;
		    }
		}
		
				

		public static Article getArticleByName(Connection connection, String title) throws Exception {
		    String query = "SELECT * FROM articles WHERE title = ?";
		    
		    // Ensure the provided connection is not null
		    if (connection == null) {
		        throw new SQLException("Connection cannot be null.");
		    }

		    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, title); // Set the title parameter
		        
		        try (ResultSet resultSet = pstmt.executeQuery()) {
		            if (resultSet.next()) {
		                // Retrieve fields from the result set
		                String titleFromDb = resultSet.getString("title");
		                String[] authors = resultSet.getString("authors").split(",");
		                String abstractText = resultSet.getString("abstractText");
		                String[] keywords = resultSet.getString("keywords").split(",");
		                String body = resultSet.getString("Body");
		                String[] references = resultSet.getString("references").split(",");

		                // Create and return the Article object
		                return new Article(titleFromDb, authors, abstractText, keywords, body, references);
		            } else {
		                System.out.println("Article not found.");
		                return null;
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        throw e; // Re-throw the exception for handling at a higher level
		    }
		}
		
		public static helpArticle getHelpArticleByName(Connection connection, String title) throws Exception {
		    String query = "SELECT * FROM help_articles WHERE title = ?";
		   
		    if (connection == null) {
		        throw new SQLException("Connection cannot be null.");
		    }

		    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, title); // Set the title parameter
		        
		        try (ResultSet resultSet = pstmt.executeQuery()) {
		            if (resultSet.next()) {
		                String titleFromDb = resultSet.getString("title");
		                String body = resultSet.getString("Body");

		                // Create and return the Article object
		                return new helpArticle(titleFromDb, body);
		            } else {
		                System.out.println("Article not found.");
		                return null;
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        throw e; // Re-throw the exception for handling at a higher level
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
		
		
		public String getGroup(String Group) throws SQLException {
			String query = "SELECT role FROM users WHERE username = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, Group);  // Set the username in the query
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
		
		public List<String> getGroups() throws SQLException {
			List<String> groupNames = new ArrayList<>();
			String query = "SELECT name as groupNames FROM generalGroups";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {  
		        ResultSet resultSet = pstmt.executeQuery();
		        
		        while (resultSet.next()) {
		        	groupNames.add(resultSet.getString("groupNames"));
		        }
		    }
			return groupNames;
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
		
		
		// Method to display all articles in the articles table
		public void displayArticles() throws SQLException {
		    String query = "SELECT * FROM articles"; // SQL query to retrieve all articles
		    
		    try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(query)) {
		        // Print column headers for clarity
		        System.out.println("ID | Title | Authors | Abstract | Keywords | References| role | body");
		        System.out.println("----------------------------------------------------------");
		        
		        // Loop through the result set and print each article's details
		        while (resultSet.next()) {
		            int id = resultSet.getInt("id");
		            String title = resultSet.getString("title");
		            String authors = resultSet.getString("authors");
		            String abstractText = resultSet.getString("abstractText");
		            String keywords = resultSet.getString("keywords");
		            String references = resultSet.getString("references");
		            String role = resultSet.getString("role");
		            String body = resultSet.getString("Body");
		         
		            		
		            
		            // Print the article details
		            System.out.println(id + " | " + title + " | " + authors + " | " + abstractText + " | " + keywords + " | " + references + " | " + role + "| " + body );
		       
		    }
		}
		}
		
		public void displayHelpArticles() throws SQLException {
		    String query = "SELECT * FROM help_articles"; // SQL query to retrieve all help articles
		    try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(query)) {
		        // Print column headers for clarity
		        System.out.println("ID | Title | Body | Role");
		        System.out.println("---------------------------------");
		        
		        // Loop through the result set and print each help article's details
		        while (resultSet.next()) {
		            int id = resultSet.getInt("id");
		            String title = resultSet.getString("title");
		            String body = resultSet.getString("Body");
		            String role = resultSet.getString("role");  // Retrieve the role

		            // Print the help article details, including the role
		            System.out.println(id + " | " + title + " | " + body + " | " + role);
		        }
		    }
		}

		
		

		 public ResultSet getInstructorArticles() throws SQLException {
		        String query = "SELECT * FROM Articles WHERE role = ?";

		        PreparedStatement preparedStatement = connection.prepareStatement(query);
		        preparedStatement.setString(1, "Instructor");

		        return preparedStatement.executeQuery();
		    }
		 
		 public ResultSet getAdminArticles() throws SQLException {
		        String query = "SELECT * FROM Articles WHERE role = ?";

		        PreparedStatement preparedStatement = connection.prepareStatement(query);
		        preparedStatement.setString(1, "Admin");

		        return preparedStatement.executeQuery();
		    }
		 


			
		public String getArticle(String title) throws SQLException 
		
		{
			 String query = "SELECT username FROM users WHERE username = ?";
			    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			        pstmt.setString(1, title);  // Set the title in the query
			        ResultSet resultSet = pstmt.executeQuery();
			        
			        if (resultSet.next()) {
			            // Return the found the article title
			            return resultSet.getString("title");
			        } else {
			            // Return null or a message if the artcle title is not found
			            return "Article title not found.";
			        }
			    }
			    
		}
		
		

		
				
		// Method to delete an article based on its title
		public boolean deleteArticle(String title) throws SQLException {
		    String deleteSQL = "UPDATE articles SET deleted = TRUE WHERE title = ?";
		    
		    try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
		        pstmt.setString(1, title); // Set the article title in the query

		        // Execute the delete statement
		        int affectedRows = pstmt.executeUpdate();

		        // If affectedRows is greater than 0, it means the article was deleted
		        return affectedRows > 0;
		    }
		}
		public boolean restoreArticle(String title) throws SQLException {
		    String updateSQL = "UPDATE articles SET deleted = FALSE WHERE title = ?";
		    try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
		        pstmt.setString(1, title); // Set the article title in the query
		        int affectedRows = pstmt.executeUpdate();
		        return affectedRows > 0;
		    }
		}

		
		public boolean deleteHelpArticle(String title) throws SQLException {
		    String updateSQL = "UPDATE help_articles SET deleted = TRUE WHERE title = ?";
		    try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
		        pstmt.setString(1, title); // Set the article title in the query
		        int affectedRows = pstmt.executeUpdate();
		        return affectedRows > 0;
		    }
		}
		public boolean restoreHelpArticle(String title) throws SQLException {
		    String updateSQL = "UPDATE help_articles SET deleted = FALSE WHERE title = ?";
		    try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
		        pstmt.setString(1, title); // Set the article title in the query
		        int affectedRows = pstmt.executeUpdate();
		        return affectedRows > 0;
		    }
		}
		


		public void backupArticles() {
		    String backupFile = "articles_backup.csv"; // Path to the backup file
		    String query = "SELECT id, title, authors, abstractText, keywords, Body, references, role, deleted FROM articles";

		    try (BufferedWriter writer = new BufferedWriter(new FileWriter(backupFile))) {
		        // Write CSV header
		        writer.write("id,title,authors,abstractText,keywords,Body,references,role,deleted");
		        writer.newLine();

		        // Execute the query to fetch all articles from the database
		        Statement stmt = connection.createStatement();
		        ResultSet resultSet = stmt.executeQuery(query);

		        // Write each article's data to the file
		        while (resultSet.next()) {
		            int id = resultSet.getInt("id");
		            String title = resultSet.getString("title");
		            String authors = resultSet.getString("authors");
		            String abstractText = resultSet.getString("abstractText");
		            String keywords = resultSet.getString("keywords");
		            String body = resultSet.getString("Body");
		            String references = resultSet.getString("references");
		            String role = resultSet.getString("role");
		            boolean deleted = resultSet.getBoolean("deleted");

		            writer.write(String.format("%d,%s,%s,%s,%s,%s,%s,%s,%b", id, title, authors, abstractText, keywords, body, references, role, deleted));
		            writer.newLine();
		        }

		        System.out.println("Articles backup completed successfully.");
		        
		        // Clean up resources
		        resultSet.close();
		        stmt.close();
		    } catch (SQLException | IOException e) {
		        e.printStackTrace();
		    }
		}

	
}
	
	 
