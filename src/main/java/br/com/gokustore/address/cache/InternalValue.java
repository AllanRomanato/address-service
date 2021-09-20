package br.com.gokustore.address.cache;

import java.time.LocalDateTime;

public interface InternalValue<V> {
	
	V getValue();
    LocalDateTime getCreatedAt();
    
}
