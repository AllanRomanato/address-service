package br.com.gokustore.address.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gokustore.address.dto.response.Envelope;
import br.com.gokustore.address.service.AddressService;
import br.com.gokustore.security.annotation.Secured;

@RestController
@RequestMapping(path = "/api/v1/address")
public class AddressGateway extends AbstractGateway{

	@Autowired
	private AddressService addrService;
	
	@Secured
	@GetMapping(path = "/get-address-by-cep/{cep}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Envelope> getAddrByCep(@PathVariable("cep") String cep){
		Envelope env = new Envelope();
		addrService.getAddressByCep(cep, env);
		return new ResponseEntity<>(env, HttpStatus.OK);
	}
	
	@Secured
	@PostMapping(path = "/add-address")
	public ResponseEntity<Void> addAddress(@RequestBody String payload){
		addrService.addNewAddress(payload);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}	
	
}
