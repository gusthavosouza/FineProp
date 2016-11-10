//Copyright 2016 Gustavo de Souza. All Rights Reserved.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

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
	protected static Boolean SEND_MAIL;
	@Propertie(key = "TIME_TO_SEND")
	private static int TIME_TO_SEND;
	@PropertieArray(key = "EMAILS")
	public static String EMAILS = null;

	public AppTest() throws FileNotFoundException, IllegalArgumentException, IllegalAccessException, IOException,
			FinePropException {
		// FineProp.loadProp(this);
		// FineProp.finePropFile("pathToFile");
	}

	public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException,
			IllegalAccessException, IOException, FinePropException, SecurityException, InstantiationException {

		FineProp.loadProp(AppTest.class);

		System.out.println(SEND_MAIL + "");
		System.out.println(TIME_TO_SEND + "");
		System.out.println(EMAILS);
	}
}
