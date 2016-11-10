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

package br.com.gustavo.fineprop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import br.com.gustavo.fineprop.annotations.Propertie;
import br.com.gustavo.fineprop.annotations.PropertieArray;
import br.com.gustavo.fineprop.exception.FinePropException;

public class FineProp {

	protected static String filePath = System.getProperty("user.dir") + "/prop.fineprop";
	protected static Properties properties;
	
	public static void loadProp (Class<?> cl) throws IllegalArgumentException, IllegalAccessException, SecurityException, FileNotFoundException, IOException, InstantiationException {
		loadProp(cl.getDeclaredFields(), cl);
	}
	
	private static void loadProp (Field[] fields, Object obj) throws FileNotFoundException, IOException, IllegalArgumentException, IllegalAccessException, InstantiationException {

		loadFile();

		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];

			if (f.isAnnotationPresent(Propertie.class))
				setField(f, obj, getProp(((Propertie) f.getAnnotation(Propertie.class)).key()));
			else if (f.isAnnotationPresent(PropertieArray.class)) {
				PropertieArray p = f.getAnnotation(PropertieArray.class);
				
				String prop = getProp(p.key());
				if (prop != null)
					if (prop.contains(p.separator()))
						setValue(f, obj, prop.split(p.separator()));
					else
						setValue(f, obj, prop);
			}
		}
	}
	
	public static void loadProp(Object obj) throws FileNotFoundException, IOException, IllegalArgumentException, IllegalAccessException, InstantiationException, SecurityException {

		if (obj == null)
			return;

		loadProp(obj.getClass().getFields(), obj);
	}
	
	private static String getProp(String key) {
		return properties.getProperty(key);
	}
	
	private static void loadFile () throws FileNotFoundException, IOException {
		Properties prop = new Properties();

		try (FileInputStream fis = new FileInputStream(new File(FineProp.filePath))) {
			prop.load(fis);
		}
		FineProp.properties = prop;
	}
	
	private static void set(Field f, Object source, Object value) throws IllegalArgumentException, IllegalAccessException {
		boolean acc = f.isAccessible();
		f.setAccessible(true);
		f.set(source, value);
		f.setAccessible(acc);
	}

	private static void setField (Field f, Object source, Object value) throws IllegalArgumentException, IllegalAccessException {
		set(f, source, cast(f.getType(), value));
	}

	// TODO add support to Collections....
	private static void setValue(Field f, Object source, Object value) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		
		
		if (List.class.isAssignableFrom(f.getType())) {
			
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) f.getType().newInstance();
			
			if (value instanceof String [])
				list.addAll(Arrays.asList((String[])value));
			else
				list.add((String) value);
			
			value = list;
		} 

		set(f, source, value);
	}

	private static Object cast(Class<?> type, Object value) {
		if (value instanceof String)
			return c(type, (String) value);
		return type.cast(value);
	}

	private static Object c (Class<?> type, String value) {

		if (value == null || value.isEmpty())
			return type.isPrimitive() ? 0 : null;

		if (type == Boolean.class || type == boolean.class)
			return value.equalsIgnoreCase("true");		
		else if (type == int.class || type == Integer.class)
			return Integer.parseInt(value);
		else if (type == long.class || type == Long.class)
			return Long.parseLong(value);
		else if (type == double.class || type == Double.class)
			return Long.parseLong(value);
		else if (type == float.class || type == Float.class)
			return Float.parseFloat(value);
		else if (type == short.class || type == Short.class)
			return Short.parseShort(value);
		else if (type == byte.class || type == Byte.class)
			return Byte.parseByte(value);

		return value;
	}
	
	public static void finePropFile(String newFilePath) throws FinePropException {

		if (newFilePath == null || newFilePath.isEmpty())
			throw new FinePropException("file path cannot be null...");
		
		if (!new File(newFilePath).exists())
			throw new FinePropException("file don't exists " + newFilePath);
		
		FineProp.filePath = newFilePath;
	}
}