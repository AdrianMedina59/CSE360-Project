package Article;
import eHandler.*;
import java.util.Arrays;

import org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;

import java.io.PrintWriter;
import java.security.KeyStore.PrivateKeyEntry;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p> helper class for articles object. </p>
 * 
 * <p> Description: Contains all the atributes of a article.</p>
 * 
 * <p> Copyright: Adrian Medina © 2024 </p>
 * 
 * @author Adrian Medina
 * 
 * @version 1.00		2024-10-20
 *  
 */

public class Article {
	private char[] title;          //title char array
	private char[][] authors;      //List of char arrays of authors
	private char[] abstractText;   //List of char arrays of abstract Text
	private char[][] keywords;    //List of char arrays of keywords
	private byte[] Encryptedbody;          //char array for body of article
	private char[][]refrences;    //list of char arrays for references
 
	private EncryptionHelper encryptionHelper;  //making use of encryption package
	
	//constructor for article
	public Article(String title,String[] authors,String abstractText, String[] keywords,String body, String[] refrences) throws Exception {
		 this.encryptionHelper = new EncryptionHelper();
		//making title into char array
		this.title = title.toCharArray();
		//making a new char array for the authors 
		this.authors = new char[authors.length][];
		for(int i =0; i < authors.length; i++)
		{
			this.authors[i]= authors[i].toCharArray();  //making each author into a char array and storing them
		}
		//setting the abstract text into a char array
		this.abstractText = abstractText.toCharArray();
		this.keywords = new char[keywords.length][];
		for(int i = 0; i < keywords.length;i++)
		{
			this.keywords[i]= keywords[i].toCharArray(); //making each keyword into a char array and storing them 
		}
		
		//making the given body contents into bytes to encypt
		byte[] bodyBytes = body.getBytes();
		//using title to generate Iv
		byte[] iv = EncryptionUtils.getInitializationVector(this.title);
		//encrypt the body
		this.Encryptedbody = encryptionHelper.encrypt(bodyBytes, iv);
		
		//making refrences into a new char array using refrences length
		this.refrences = new char[refrences.length][];
		//setting each entry inside the array into a char array
		for(int i =0; i < refrences.length; i++)
		{
			this.refrences[i] = refrences[i].toCharArray();
		}
	}
	
	public String getDecryptBody() throws Exception{
		//using the title for the iv
		byte[] iv = EncryptionUtils.getInitializationVector(this.title);
		//decrypting the body text
		byte[] decryptedBody = encryptionHelper.decrypt(this.Encryptedbody, iv);
		return new String(decryptedBody); //returning the decrypted body
	}
	
	public  String getTitle()
			{
			return new String(this.title);
			}
	public String[] getAuthors()
	{
		String[] authorNames = new String[authors.length];
		for(int i =0; i < authors.length;i++)
		{
			authorNames[i]= new String(authors[i]); 
		}
		return authorNames;
	}
	
	public void DisplayArticle(PrintWriter writer) throws Exception {
	    writer.println("Title: " + new String(this.title));

	    // Display authors without trailing comma
	    writer.print("Authors: ");
	    for (int i = 0; i < this.authors.length; i++) {
	        writer.print(new String(this.authors[i]));
	        if (i < this.authors.length - 1) {
	            writer.print(", ");  
	        }
	    }
	    writer.println(); 

	    // Display abstract text
	    writer.println("Abstract Text: " + new String(this.abstractText));

	    // Display keywords without trailing comma
	    writer.print("Keywords: ");
	    for (int i = 0; i < this.keywords.length; i++) {
	        writer.print(new String(this.keywords[i]));
	        if (i < this.keywords.length - 1) {
	            writer.print(", ");  
	        }
	    }
	    writer.println();  // Move to the next line after keywords

	    // Display decrypted body text
	    writer.println("Body: " + getDecryptBody());

	  
	    writer.print("References: ");
	    for (int i = 0; i < this.refrences.length; i++) {
	        writer.print(new String(this.refrences[i]));
	        if (i < this.refrences.length - 1) {
	            writer.print(", ");  
	        }
	    }
	    writer.println();  
	}
	
	//function used to display all content of article
	 public void displayContent() throws Exception {
	        System.out.println("Title: " + new String(this.title));
	        System.out.println("Authors: ");
	        for (char[] author : this.authors) {
	            System.out.println(" - " + new String(author));
	        }
	        System.out.println("Abstract: " + new String(this.abstractText));
	        System.out.println("Keywords: ");
	        for (char[] keyword : this.keywords) {
	            System.out.println(" - " + new String(keyword));
	        }
	        System.out.println("Decrypted Body: " + getDecryptBody());  // Decrypted body
	        System.out.println("References: ");
	        for (char[] reference : this.refrences) {
	            System.out.println(" - " + new String(reference));
	        }
	    }
	 
	public boolean  articleMatch(String uhh) {
		
	return false;
		
	}
	
	public boolean updateArticles(String uhh) {
		return false;
		
	}


	public String getAbstractText() {
		return new String(this.abstractText);
	}


	public String[] getKeywords() {
		String[] Keywords = new String[keywords.length];
		for(int i =0; i < keywords.length;i++)
		{
			Keywords[i]= new String(keywords[i]); 
		}
		return Keywords;
	}


	public byte[] getEncryptedBody() {
		// TODO Auto-generated method stub
		return Encryptedbody;
	}


	public String[] getReferences() {
		String[] links = new String[refrences.length];
		for(int i =0; i < refrences.length;i++)
		{
			links[i]= new String(refrences[i]); 
		}
		return links;
	}
	
	public String getBody() throws Exception {
        return getDecryptBody(); // Ensure this method is defined
    }
	 
	 
}
