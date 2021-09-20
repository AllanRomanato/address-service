package br.com.gokustore.address.cache;

public interface GokuCache<K, V> {
	
	void insert(K key, V value);
	void delete(K key);
	void purge();
	void purgeExpired();
	Boolean contains(K key);
	V get(K key);
}
