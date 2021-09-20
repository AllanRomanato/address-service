package br.com.gokustore.address.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gokustore.address.cache.GokuCache;
import br.com.gokustore.address.cache.impl.GokuCacheImpl;

@Configuration
public class GokuCacheConfig {

	@Value("${application.cache-timeout}")
	private Long timeout;
	
	@Bean
	public <K, V> GokuCache<K, V> loadCache(){
		return new GokuCacheImpl<>(timeout);
	}
}