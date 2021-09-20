package br.com.gokustore.address.service.impl;

import static br.com.gokustore.address.utils.Constants.APP_NAME;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gokustore.address.cache.GokuCache;
import br.com.gokustore.address.dto.request.NewAddressDto;
import br.com.gokustore.address.dto.response.AddressDto;
import br.com.gokustore.address.dto.response.Envelope;
import br.com.gokustore.address.jpa.model.Address;
import br.com.gokustore.address.jpa.repository.AddressRepository;
import br.com.gokustore.address.service.AddressService;
import br.com.gokustore.utils.exceptions.BadRequestException;
import br.com.gokustore.utils.exceptions.NotFoundException;

@Service
public class AddressServiceImpl implements AddressService {

	private static final Logger log = Logger.getLogger(AddressServiceImpl.class);
	
	@Autowired
	private AddressRepository addressRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private GokuCache<String, Address> cachedValues;
	
	public void getAddressByCep(String postalCode, Envelope env) {
		long start = System.currentTimeMillis();
		log.info(" ---- RECOVERING INFORMATION FOR POSTAL CODE "+postalCode+" ---- ");
		
		Address addr = loadAddrInfo(postalCode);
		
		if(addr==null) {
			log.info(" ---- NO ADDRESS FOR THIS POSTAL CODE HAD BEEN FOUND ---- ");
			throw new NotFoundException("CEP não encontrado");
		}
		
		AddressDto dto = modelMapper.map(addr, AddressDto.class);

		env.setData(dto);
		env.setSuccess(true);
		
		log.info(" ---- INFORMATION RECOVERED ---- ");
		long stop = System.currentTimeMillis();
		
		log.info(" ---- Operation took: "+(stop-start)+" millis ---- ");
	}

	public void addNewAddress(String payload) {		
		try {
			log.info(" ---- SAVING NEW ADDRESS ---- ");
			NewAddressDto addrDto = objectMapper.readValue(payload, NewAddressDto.class);
		
		
			PropertyMap<NewAddressDto, Address> skipIdField = new PropertyMap<NewAddressDto, Address>() {
				protected void configure() {
					skip().setId(null);
				}
			};
	
			modelMapper.addMappings(skipIdField);
			
			Address addressEntity = modelMapper.map(addrDto, Address.class);
			addressEntity.setCreatedUser(APP_NAME);
			addressEntity.setCreatedAt(new Date());
			
			addressRepo.save(addressEntity);
			
			log.info(" ---- ADDRESS SAVED SUCCESSFULLY ---- ");
		} catch (IOException e) {
			log.error(" ---- SOMTHING WRONG HAS HAPPENED ---- ", e);
			throw new BadRequestException(e.getMessage());
		}
	}
	
	private Address loadAddrInfo(String postal) {
		log.info(" ---- LOADING FROM CACHE ---- ");
		Address addr = cachedValues.get(postal);
		
		if(addr==null) {
			log.info(" ---- NO DATA FOUND ON CACHE, LOADING FROM DB ---- ");
			addr = addressRepo.getAddrByPostalCode(postal);
			if(addr != null) {
				log.info(" ---- DATA FOUND ON DB SAVING ON CACHE ---- ");
				cachedValues.insert(postal, addr);
			} else {
				log.info(" ---- NO DATA FOUND ON DB ---- ");
			}
		} else {
			log.info(" ---- DATA FOUND ON CACHE ---- ");
		}
		return addr;
	}

}
