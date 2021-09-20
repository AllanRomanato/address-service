package br.com.gokustore.address.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.gokustore.security.annotation.Secured;
import br.com.gokustore.security.utils.JwtSecurity;
import br.com.gokustore.utils.exceptions.BadRequestException;
import br.com.gokustore.utils.exceptions.UnauthorizedException;


@Component
public class AuthInterceptor implements HandlerInterceptor{
	
	private static final Logger log = Logger.getLogger(AuthInterceptor.class);
	
	@Autowired
	private JwtSecurity jwtSec;

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Unauthorized, BadRequestException {
		
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		Secured annotation = method.getAnnotation(Secured.class);
		
		if (annotation != null) {
			String auth = request.getHeader("Authorization");
			if (auth != null) {
				String token = auth.trim();
				try {
					DecodedJWT decoded = jwtSec.verifyToken(token);
					request.setAttribute("tokenPayload", decoded.getPayload());
					return true;
				} catch (TokenExpiredException | AlgorithmMismatchException | InvalidClaimException | SignatureVerificationException e) {
					log.info(" ---- INVALID TOKEN "+ e.getMessage() + " ---- ");
					throw new UnauthorizedException("Token Inválido");
				}
			} else {
				log.info(" ---- THE AUTH TOKEN WAS NOT SENT ---- ");
				throw new BadRequestException("Token não enviado.");
			}
		}else {
			// Endpoint publico
			return true;
		}
	}
	
}
