package edu.wpi.mhtc.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author ted
 *
 * @param <K>
 * @param <V>
 */
public class Cache<K, V>
{
	private final int max;
	private Map<K, CacheList<K, V>> cache = new HashMap<K, CacheList<K, V>>();
	private CacheList<K, V> head = null, tail = head;

	/**
	 * 
	 * @param max the maximum size of the cache
	 */
	public Cache(int max)
	{
		this.max = max;
	}

	/**
	 * clear the cache
	 */
	public void clear() {
		cache.clear();
		head = null;
		tail = head;
	}
	
	/**
	 * 
	 * @param key the key in the kvp
	 * @return the value of the kvp
	 */
	public V get(K key) {
		if (!cache.containsKey(key))
		{
			return null;
		}
		
		CacheList<K, V> ce = cache.get(key);
		if (ce.equals(head))
		{
			head = ce.before;
		}
		tail = ce.pushToEnd(tail);
		return ce.v;
		
	}

	/**
	 * 
	 * @return whether the cache is empty
	 */
	public boolean isEmpty() {
		return cache.isEmpty();
	}

	/**
	 * 
	 * @param key the key in kvp to be inserted
	 * @param val the val in the kvp to be inserted
	 * @return whether anything was removed when the kvp was inserted
	 */
	public boolean put(K key, V val)
	{
		CacheList<K, V> ce = new CacheList<K, V>(val, key);
		if (tail == null)
		{ // and therefore head == null
			cache.put(key, ce);
			head = ce;
			tail = ce;
			return false;
		}
		
		if (!cache.containsKey(key))
		{
			cache.put(key, ce);
		}
		
		if (ce.equals(head))
		{
			head = head.after;
		}
		
		tail = ce.pushToEnd(tail);
		
		if (size() > max)
		{
			cache.remove(head.k);
			head = head.before;
			head.after = null;
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param key the key in the kvp to remove
	 * @return the removed value in the kvp
	 */
	public V remove(K key)
	{
		CacheList<K, V> ce = cache.remove(key);
		if (ce == null)
		{
			return null;
		}
		return ce.remove().v;
	}


	/**
	 * 
	 * @return the size of the cache
	 */
	public int size() {
		return cache.size();
	}
	
	/**
	 * 
	 * @author ted
	 *
	 * @param <L>
	 * @param <B>
	 */
	private class CacheList<L, B>
	{
		//leave public since we're in a private class anyways
		public CacheList<L, B> after, before;
		public B v;
		public L k;
		
		/**
		 * 
		 * @param v
		 * @param k
		 */
		public CacheList(B v, L k)
		{
			this.v = v;
			this.k = k;
		}
		
		/**
		 * 
		 * @param oldEnd the old end to append this to
		 * @return
		 */
		public CacheList<L, B> pushToEnd(CacheList<L, B> oldEnd)
		{
			merge(oldEnd, remove());
			return this;
		}
		
		/**
		 * 
		 * @return this element, removed from it's spot
		 */
		public CacheList<L, B> remove()
		{
			merge(this.after, this.before);
			return this;
		}

		/**
		 * 
		 * @param a a list element (after)
		 * @param b another list element (before)
		 */
		private void merge(CacheList<L, B> a, CacheList<L, B> b) {
			if (a != null)
			{
				a.before = b;
			}
			if (b != null)
			{
				b.after = a;
			}
		}
		
		@SuppressWarnings("rawtypes")
		@Override
		public boolean equals(Object o)
		{
			if (o instanceof CacheList)
			{
				if (v == null)
				{
					return (((CacheList)o).v)==null;
				}
				else
				{
					return v.equals(((CacheList)o).v);
				}
			}
			return false;
		}
	}


	
}
