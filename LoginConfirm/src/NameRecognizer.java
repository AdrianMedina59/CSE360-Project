

public class NameRecognizer {
		private  static int currentCharIndex;   //keeping track of the current index of char in name
		private static char currentChar;        //the current char in the name
		private static boolean ValidName;       //Boolean to check if the name is valid
		private static String inputNameString = "";  //The input line to be processed
		
		
		
		  static boolean isValidName(String name) {
		        // Regular expression to match only letters (uppercase and lowercase) and spaces
		        return name.matches("[a-zA-Z\\s]+");
		    }
 //Method is used to move to the next index of the name
		static boolean isValidEmail(String email) {
	        // Regular expression for validating an email address
	        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	        return email.matches(emailRegex);
	    }
}

