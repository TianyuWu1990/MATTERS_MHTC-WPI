package edu.wpi.mhtc.rson;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class RSON
{
	@SuppressWarnings("unchecked")
	public static StringBuilder parse(Object obj, Class<?> clazz, int... depth) throws IllegalArgumentException, IllegalAccessException
	{
		Field[] allFields = clazz.getDeclaredFields();
		Class<?>[] interfaces = clazz.getInterfaces();
		
		StringBuilder result = new StringBuilder();
		if (depth.length == 0)
		{
			depth = new int[1];
			depth[0] = 0;
		}
		
		boolean isArray = false;
		boolean isMap   = false;
		
		// safe guard. never recurse that deeply.
		if (depth.length>1 && depth[0]>5)
		{
			return result;
		}
		else if (clazz == Integer.class || clazz == Boolean.class)
		{
			return result.append(obj);
		}
		else if (clazz == String.class)
		{
			return result.append('"').append(obj).append('"');
		}
		
		// determine whether this is a list;
		for(int i=0; i<interfaces.length; i++)
		{
			System.out.println(interfaces[i].toString());
			if (interfaces[i] == java.util.List.class)
			{
				isArray = true;
				i = interfaces.length;
			}
			else if (interfaces[i] == Map.class)
			{
				isMap = true;
				i = interfaces.length;
			}
		}
		
		
		if (isArray)
		{
			result = result.append('[');
			List<?> list = (List<?>)obj;
			int counter = list.size();
			for(Object o : list)
			{
				result.append(parse(o, o.getClass(), depth[0]+1));
				if (counter!=1) result.append(',');
				counter--;
			}
			result = result.append(']');
			return result;
		}
		

		result = result.append('{');
		
		if (isMap)
		{
			Map<String,?> map = (Map<String,?>)obj;
			Set<?> entries = map.entrySet();
			int counter = entries.size();
			for(Object o : entries)
			{
				Entry<String,?> entry = (Entry<String,?>)o;
				String name = entry.getKey();
				Object data = entry.getValue();
				result.append(name);
				result.append(':');
				result.append(parse(data, data.getClass(), depth[0]+1));
				if (counter!=1) result.append(',').append(' ');
				counter--;
			}
		}
		else
		{
			for(Field f : allFields)
			{
				if (Modifier.isPrivate(f.getModifiers()))
				{
					f.setAccessible(true);
				}
				// dont copy transient fields (duh)
				if (!Modifier.isTransient(f.getModifiers()))
				{
					result.append(isArray ? "":f.getName()+":");
					result.append(parse(f.get(clazz.cast(obj)), f.getType(), depth[0]+1));
					
					if (f != allFields[allFields.length-1]) // everything but last item
					{
						result.append(',').append(' ');
					}
				}
			}
		}
		
		
		
		result = result.append('}');
		
		
		
		
		
		return result;
	}
	
	
	public static String parse(Object o) throws ParseException
	{
		try
		{
			return parse(o, o.getClass(), 0).toString();
		}
		catch (IllegalArgumentException e)
		{
			throw new ParseException(e.getStackTrace());
		}
		catch (IllegalAccessException e)
		{
			throw new ParseException(e.getStackTrace());
		}
	}
}
