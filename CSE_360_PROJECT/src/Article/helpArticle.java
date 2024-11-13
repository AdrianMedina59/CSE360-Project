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

public class helpArticle {
	private char[] title;          //title char array
	private byte[] Encryptedbody;          //char array for body of article
 
	private EncryptionHelper encryptionHelper;  //making use of encryption package
	
	//constructor for article
	public helpArticle(String title,String body ) throws Exception {
		 this.encryptionHelper = new EncryptionHelper();
		//making title into char array
		this.title = title.toCharArray();
	
		//making the given body contents into bytes to encypt
		byte[] bodyBytes = body.getBytes();
		//using title to generate Iv
		byte[] iv = EncryptionUtils.getInitializationVector(this.title);
		//encrypt the body
		this.Encryptedbody = encryptionHelper.encrypt(bodyBytes, iv);
			
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
	
	public void DisplayArticle(PrintWriter writer) throws Exception {
	    writer.println("Title: " + new String(this.title));
	    writer.println(); 
	    writer.println("Body: " + getDecryptBody());
	    writer.println();  
	}
	
	//function used to display all content of article
	 public void displayContent() throws Exception {
	        System.out.println("Title: " + new String(this.title));
	        System.out.println("Decrypted Body: " + getDecryptBody());  // Decrypted body
	    }
	 

	public byte[] getEncryptedBody() {
		// TODO Auto-generated method stub
		return Encryptedbody;
	}


	
	public String getBody() throws Exception {
        return getDecryptBody(); // Ensure this method is defined
    }
	 
	 
}