package br.com.gokustore.address.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import br.com.gokustore.address.interceptor.AuthInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport{

	@Autowired
	private AuthInterceptor interceptor;
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }
}
