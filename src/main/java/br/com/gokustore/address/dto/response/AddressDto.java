package br.com.gokustore.address.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressDto {
	
	private Integer id;
	private String postalCode;
	private String address;
	@JsonProperty("city")
	private String cityName;
	@JsonProperty("state")
	private String cityStateName;
	@JsonProperty("country")
	private String cityStateCountryName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityStateName() {
		return cityStateName;
	}
	public void setCityStateName(String cityStateName) {
		this.cityStateName = cityStateName;
	}
	public String getCityStateCountryName() {
		return cityStateCountryName;
	}
	public void setCityStateCountryName(String cityStateCountryName) {
		this.cityStateCountryName = cityStateCountryName;
	}
	
	
}
