package br.com.gokustore.address.cache.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.gokustore.address.cache.GokuCache;
import br.com.gokustore.address.cache.InternalValue;

public class GokuCacheImpl<K, V> implements GokuCache<K, V> {

	private Map<K, InternalValue<V>> map;
	private Long timeout;
	
	public GokuCacheImpl(Long timeout) {
		this.timeout = timeout;
		this.purge();
	}
	
	@Override
	public Boolean contains(K k) {
		return this.map.containsKey(k);
	}

	@Override
	public void insert(K k, V v) {
		this.map.put(k, insertNewValue(v));
	}

	@Override
	public void delete(K k) {
		this.map.remove(k);
	}

	@Override
	public void purge() {
		this.map = new HashMap<>();
		
	}

	@Override
	public void purgeExpired() {
		for(K key: recoverExpiredEntries()) {
            this.delete(key);
        }
	}
	
	@Override
	public V get(K k) {
		purgeExpired();
		try {
			return map.get(k).getValue();
		}catch(NullPointerException e) {
			return null;
		}
		
	}

	//Auxiliary methods
	
	private InternalValue<V> insertNewValue(V v){
		LocalDateTime time = LocalDateTime.now();
		InternalValue<V> returnedValue = new InternalValue<V>() {

			@Override
			public V getValue() {
				return v;
			}

			@Override
			public LocalDateTime getCreatedAt() {
				return time;
			}
		};
		
		return returnedValue;
	}
	
	private Set<K> recoverExpiredEntries(){
		return this.map.keySet()
				.parallelStream()
				.filter(this::isEntryExpired)
				.collect(Collectors.toSet());
	}
	
	private Boolean isEntryExpired(K k) {
		return LocalDateTime.now().isAfter(
				this.map.get(k)
				.getCreatedAt()
				.plus(this.timeout, ChronoUnit.MILLIS));
	}
	
}
