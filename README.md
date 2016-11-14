# FineProp
FineProp - Load properties from file based in annotations.

## Usage
How it works?
Easy... :)

See example below.

Default propertie file -> prop.fineprop in your project folder

you can set propertie file path calling

```
FineProp.finePropFile(newFilePath);
```

```
SEND_MAIL=true
EMAILS=gusthavo.souzapereira@gmail.com
```
```
// work with private, protected and other modifier

@Propertie(key = "SEND_MAIL")
protected static Boolean SEND_MAIL;

@Propertie(key = "EMAIL")
public static String EMAIL = null;

// work with String[] :)
@PropertieArray(key = "EMAILS")
private static List<String> EMAILS_LIST = null;

public static void main(String[] args) {

		try {
			FineProp.loadProp(FinePropTest.class);	
		} catch (Exception e) {
			// something is wrong
		}
}

```
or work whith object

```
public FinePropTest() {
	FineProp.loadProp(this);
}

```

## Java 7
Java 7 or remove Try with resources see:
https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html

## License
Copyright 2016 Gustavo de Souza. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
