package br.com.gokustore.address.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gokustore.address.jpa.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{

	@Query(nativeQuery = true, 
			value = "SELECT * FROM address WHERE postal_code = :postal")
	Address getAddrByPostalCode(@Param("postal") String postal);
	
}
