package br.com.gustavo.FineProp;

import java.io.FileNotFoundException;
import java.io.IOException;

import br.com.gustavo.fineprop.FineProp;
import br.com.gustavo.fineprop.annotations.Propertie;
import br.com.gustavo.fineprop.annotations.PropertieArray;
import br.com.gustavo.fineprop.exception.FinePropException;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@Propertie(key = "SEND_MAIL")
	private static Boolean SEND_MAIL;
	@Propertie(key = "TIME_TO_SEND")
	private static int TIME_TO_SEND;

	@PropertieArray(key = "EMAILS")
	private static String[] EMAILS = null;

	public AppTest() throws FileNotFoundException, IllegalArgumentException, IllegalAccessException, IOException, FinePropException {
		// default file in project
		FineProp.loadProp(this);
//		FineProp.finePropFile("pathToFile");
	}

	public static void main(String[] args)
			throws FileNotFoundException, IllegalArgumentException, IllegalAccessException, IOException, FinePropException {
		
		new AppTest();

		System.out.println(SEND_MAIL + "");
		System.out.println(TIME_TO_SEND + "");
		
		for (int i = 0; i < EMAILS.length; i++)
			System.out.println(EMAILS[i]);	
	}
}
