package model;

public class Address {
	private String country_code;
	private String housenumber;
	private String postcode;
	private String city;
	private String street;
	/**
	 * @return the country_code
	 */
	public String getCountry_code() {
		return country_code;
	}
	/**
	 * @param countryCode the country_code to set
	 */
	public void setCountry_code(String countryCode) {
		country_code = countryCode;
	}
	/**
	 * @return the housenumber
	 */
	public String getHousenumber() {
		return housenumber;
	}
	/**
	 * @param housenumber the housenumber to set
	 */
	public void setHousenumber(String housenumber) {
		this.housenumber = housenumber;
	}
	/**
	 * @return the postcode
	 */
	public String getPostcode() {
		return postcode;
	}
	/**
	 * @param postcode the postcode to set
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	public Address(String countryCode, String housenumber, String postcode,
			String city, String street) {
		country_code = countryCode;
		this.housenumber = housenumber;
		this.postcode = postcode;
		this.city = city;
		this.street = street;
	}
	
	public Address(){
		country_code = "";
		this.housenumber = "";
		this.postcode = "";
		this.city = "";
		this.street = "";
	}
	
	public String toHTML(){
		String out = ""; 
		//Land
		out+=
		  "<TR>" + 
		  "<TD>" +
	      "Land:" +
	      "</TD>" + 
	      "<TD>" +
	      this.country_code +
	      "</TD>" + 
	      "</TR>" +
	    //Stra§e
	      "<TR>" + 
		  "<TD>" +
	      "Strasse:" +
	      "</TD>" + 
	      "<TD>" +
	      this.street+" "+this.housenumber +
	      "</TD>" + 
	      "</TR>" +
	    //PLZ+Stadt
	      "<TR>" + 
		  "<TD>" +
	      "PLZ:" +
	      "</TD>" + 
	      "<TD>" +
	      this.postcode+ " "+this.city +
	      "</TD>" + 
	      "</TR>";
	    return out;
	}
}
