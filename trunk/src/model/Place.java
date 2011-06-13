package model;

import java.util.ArrayList;
import simplelatlng.LatLng;

public class Place {
	private String id;
	private int rating_avg;
	private String title;
	private boolean closed;
	private String url;
	private LatLng point;
	private String opening_hours;
	private String phone;
	private Address address;
	private Category category;
	private ReviewURIList reviews_de;
	private ReviewURIList reviews_en;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the rating_avg
	 */
	public int getRating_avg() {
		return rating_avg;
	}
	/**
	 * @param ratingAvg the rating_avg to set
	 */
	public void setRating_avg(int ratingAvg) {
		rating_avg = ratingAvg;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the closed
	 */
	public boolean isClosed() {
		return closed;
	}
	/**
	 * @param closed the closed to set
	 */
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the point
	 */
	public LatLng getPoint() {
		return point;
	}
	/**
	 * @param point the point to set
	 */
	public void setPoint(LatLng point) {
		this.point = point;
	}
	/**
	 * @return the opening_hours
	 */
	public String getOpening_hours() {
		return opening_hours;
	}
	/**
	 * @param openingHours the opening_hours to set
	 */
	public void setOpening_hours(String openingHours) {
		opening_hours = openingHours;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}
	
	/**
	 * @return the reviews_de
	 */
	public ReviewURIList getReviews_de() {
		return reviews_de;
	}
	/**
	 * @param reviewsDe the reviews_de to set
	 */
	public void setReviews_de(ReviewURIList reviewsDe) {
		reviews_de = reviewsDe;
	}
	/**
	 * @return the reviews_en
	 */
	public ReviewURIList getReviews_en() {
		return reviews_en;
	}
	/**
	 * @param reviewsEn the reviews_en to set
	 */
	public void setReviews_en(ReviewURIList reviewsEn) {
		reviews_en = reviewsEn;
	}
	
	public Place(String id, int ratingAvg, String title, boolean closed,
			String url, LatLng point, String openingHours, String phone,
			Address address, Category category, ReviewURIList reviewsDe,
			ReviewURIList reviewsEn) {
		this.id = id;
		rating_avg = ratingAvg;
		this.title = title;
		this.closed = closed;
		this.url = url;
		this.point = point;
		opening_hours = openingHours;
		this.phone = phone;
		this.address = address;
		this.category = category;
		reviews_de = reviewsDe;
		reviews_en = reviewsEn;
	}
	
	public Place(){
		this.id = "";
		this.rating_avg=0;
		this.title="";
		this.closed=false;
		this.url = "";
		this.point = new LatLng(0.0, 0.0);
		this.opening_hours = "";
		this.phone = "";
		this.address = new Address();
		this.category = new Category();
		reviews_de = new ReviewURIList();
		reviews_en = new ReviewURIList();
	}
	
	public int reviewSum(){
		return (reviews_de.getCount() + reviews_en.getCount());
	}
	
	public String toHTML(){
		String out = ""; 
		out += "<TABLE border='1'>";
		//HTML
		//Name
		  out+=
		  "<TR>" + 
	      "<TD>" + 
	      "Name:" +
	      "</TD>" + 
	      "<TD>" +
	      this.title +
	      "</TD>" +
	      "</TR>" +
	    //Kategorie
	      "<TR>" + 
	      "<TD>" + 
	      "Kategorie:" +
		  "</TD>" + 
	      "<TD>" +
	      this.category.getFull_title() +
		  "</TD>" +
	      "</TR>" +
	    //Bewertung
	      "<TR>" + 
	      "<TD>" + 
	      "Durchschnittliche Bewertung:" +
	      "</TD>" + 
	      "<TD>";
	      for (int i=1; i<=this.rating_avg;i++)
	      	out += "*";
	      out += "</TD>" +
	      "</TR>" +
	    //Summe Bewertungen
	      "<TR>" + 
	      "<TD>" + 
	      "Summe Bewertungen:" +
	      "</TD>" + 
	      "<TD>" +
	      this.reviewSum();
	      out += "</TD>" +
	      "</TR>" +
	    //Ort:
	      "<TR>" + 
	      "<TD>" + 
	      "Geolocation:" +
	      "</TD>" + 
	      "<TD>" +
	      this.point.toString() +
	      "</TD>" +
	      "</TR>" +
	    //URL
	      "<TR>" + 
	      "<TD>" + 
	      "Webseite:" +
	      "</TD>" + 
	      "<TD>" +
	      this.url +
	      "</TD>" +
	      "</TR>" +
	    //Telefon
	      "<TR>" + 
	      "<TD>" + 
	      "Telefon-Nummer:" +
	      "</TD>" + 
	      "<TD>" +
	      this.phone +
	      "</TD>" +
	      "</TR>" +
	    //Adresse
	      "<TR>" + 
	      "<TD>" + 
	      "Adresse:" +
	      "</TD>" + 
	      this.address.toHTML() +
	    //…ffnungszeiten
	      "<TR>" + 
	      "<TD>" + 
	      "Öffnungszeiten:" +
	      "</TD>" + 
	      "<TD>" +
	      this.opening_hours +
	      "</TD>" +
	      "</TR>" +
	      "</TABLE>";
	      System.out.println(out);
	    return out;
	}
}
