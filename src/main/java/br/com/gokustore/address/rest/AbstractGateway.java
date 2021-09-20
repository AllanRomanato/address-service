package br.com.gokustore.address.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.gokustore.address.dto.response.Envelope;
import br.com.gokustore.utils.exceptions.BadRequestException;
import br.com.gokustore.utils.exceptions.InternalServerErrorException;
import br.com.gokustore.utils.exceptions.NotFoundException;
import br.com.gokustore.utils.exceptions.UnauthorizedException;

public abstract class AbstractGateway {
	
	@ExceptionHandler(InternalServerErrorException.class)
	public ResponseEntity<Envelope> interalErrorException(InternalServerErrorException e){
		Envelope env = createErrorMsg(e.getMessage());
		return new ResponseEntity<Envelope>(env, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Envelope> notFoundExeception(NotFoundException e){
		Envelope env = createErrorMsg(e.getMessage());
		return new ResponseEntity<Envelope>(env, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Envelope> badRequestHandler(BadRequestException e){
		Envelope env = createErrorMsg(e.getMessage());
		return new ResponseEntity<Envelope>(env, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<Envelope> unauthorizedHandler(UnauthorizedException e){
		Envelope env = createErrorMsg(e.getMessage());
		return new ResponseEntity<Envelope>(env, HttpStatus.UNAUTHORIZED);
	}
	
	private Envelope createErrorMsg(String message) {
		Envelope env = new Envelope();
		env.setSuccess(false);
		env.setError(message);
		
		return env;
	}
}
