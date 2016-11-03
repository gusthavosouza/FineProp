package br.com.gustavo.fineprop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

import br.com.gustavo.fineprop.annotations.Propertie;
import br.com.gustavo.fineprop.annotations.PropertieArray;
import br.com.gustavo.fineprop.exception.FinePropException;

public class FineProp {

	protected static String filePath = System.getProperty("user.dir") + "/prop.fineprop";
	protected static Properties properties;
	
	public static void loadProp(Object obj) throws FileNotFoundException, IOException, IllegalArgumentException, IllegalAccessException {

		if (obj == null)
			return;

		loadProp();

		Field fields[] = obj.getClass().getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];

			if (f.isAnnotationPresent(Propertie.class))
				setField(f, obj, getProp(((Propertie) f.getAnnotation(Propertie.class)).key()));
			else if (f.isAnnotationPresent(PropertieArray.class)) {
				PropertieArray p = f.getAnnotation(PropertieArray.class);
				setValue(f, obj, getProp(p.key()).split(p.separator()));
			}
		}
	}
	
	private static String getProp(String key) {
		return properties.getProperty(key);
	}
	
	private static void loadProp () throws FileNotFoundException, IOException {
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

	private static void setValue(Field f, Object source, Object[] value) throws IllegalArgumentException, IllegalAccessException {
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
	
	public static void propFile(String newFilePath) throws FinePropException {

		if (newFilePath == null || newFilePath.isEmpty())
			throw new FinePropException("file path cannot be null...");
		
		if (!new File(newFilePath).exists())
			throw new FinePropException("file don't exists " + newFilePath);
		
		FineProp.filePath = newFilePath;
	}
}