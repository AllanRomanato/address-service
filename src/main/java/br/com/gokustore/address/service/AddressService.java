package br.com.gokustore.address.service;

import br.com.gokustore.address.dto.response.Envelope;

public interface AddressService {
	void getAddressByCep(String cep, Envelope env);
	void addNewAddress(String payload);
}
