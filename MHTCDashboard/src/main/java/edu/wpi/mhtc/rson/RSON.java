package edu.wpi.mhtc.rson;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 
 * @author ted
 *
 * please do not scroll
 */
public class RSON
{	
	@SuppressWarnings("unchecked")
	public static <T extends Object> StringBuilder parse(Object obj, Class<?> clazz, int... depth) throws IllegalArgumentException, IllegalAccessException
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
		else if (clazz == Integer.class || clazz == Boolean.class ||
				 clazz == Float.class   || clazz == Double.class)
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
			Map<String,T> map = (Map<String,T>)obj;
			List<ImmutableComparableEntry<String, T>> entries = asSortedList(map);
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
			List<String> non_transient_vars = new LinkedList<String>();
			for(Field f : allFields)
			{
				StringBuilder tem = new StringBuilder();
				if (Modifier.isPrivate(f.getModifiers()))
				{
					f.setAccessible(true);
				}
				// dont copy transient fields (duh)
				if (!Modifier.isTransient(f.getModifiers()))
				{
					tem.append(isArray ? "":f.getName()+":");
					tem.append(parse(f.get(clazz.cast(obj)), f.getType(), depth[0]+1));
					non_transient_vars.add(tem.toString());
				}
			}
			
			String last = non_transient_vars.get(non_transient_vars.size()-1);
			for(String s : non_transient_vars)
			{
				result.append(s);
				if (s != last)
				{
					result.append(", ");
				}
			}
		}
		
		return result.append('}');
	}
	
	
	/**
	 * 
	 * @param o any object, however it cannot contain any native types, or any autoboxed classes excluding
	 * 			Integer and Boolean
	 * @return the JSON string serialization of the class (5 levels deep)
	 * @throws ParseException if something goes wrong. shouldn't happen if you refrain from using native types
	 * 							and autoboxed types excluding Integer and Boolean
	 */
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
	
	
	/**
	 * 
	 * @param c the map to convert
	 * @return the map as a sorted list of entries
	 */
	public static <T> List<ImmutableComparableEntry<String, T>> asSortedList(Map<String, T> c)
	{
		Set<Entry<String, T>> entries = c.entrySet();
		
		List<Entry<String, T>> list = new ArrayList<Entry<String, T>>(entries);
		List<ImmutableComparableEntry<String, T>> result = new LinkedList<ImmutableComparableEntry<String, T>>();
		
		for(Entry<String, T> e : list)
		{
			result.add(new ImmutableComparableEntry<String, T>(e));
		}
		
		Collections.sort(result, new Comparator<ImmutableComparableEntry<String, T>>(){

			@Override
			public int compare(ImmutableComparableEntry<String, T> arg0,
					ImmutableComparableEntry<String, T> arg1) {
				return arg0.compareTo(arg1);
			}
			
		});
		
		return result;
	}
	
	
	
	/**
	 * all muh <?>
	 * @author ted... I am so sorry
	 *
	 * @param <X> something comparable
	 * @param <T> anything
	 */
	private static class ImmutableComparableEntry<X extends Comparable<? super X>, T> implements Entry<X, T>, Comparable<ImmutableComparableEntry<X, T>>
	{
		private final X x;
		private final T t;
		
		/**
		 * default copy constructor for ISE
		 * 
		 * @param entry the entry to convert to comparable
		 */
		public ImmutableComparableEntry(Entry<X, T> entry)
		{
			x = entry.getKey();
			t = entry.getValue();
		}
		
		@Override
		public int compareTo(ImmutableComparableEntry<X, T> o) {
			return this.x.compareTo(o.getKey());
		}

		@Override
		public X getKey() {
			return x;
		}

		@Override
		public T getValue() {
			return t;
		}

		@Override
		public T setValue(T value) {
			throw new RuntimeException("no.");
		}
		
	}
}
