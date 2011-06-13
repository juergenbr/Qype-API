package model;

public class Review {
	int rating;
	String text;
	
	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	public Review(int rating, String text) {
		this.rating = rating;
		this.text = text;
	}
	
	public Review(){
		rating = 0;
		text = "";
	}
	
	public String toHtml(){
		String out = ""; 
		//Land
		out+=
		  "<TR>" + 
		  "<TD>" +
	      "Bewertung:" +
	      "</TD>" + 
	      "<TD>" +
	      this.rating +
	      "</TD>" + 
	      "</TR>" +
	    //Stra§e
	      "<TR>" + 
		  "<TD>" +
	      "Bewertungstext:" +
	      "</TD>" + 
	      "<TD>" +
	      this.text +
	      "</TD>" + 
	      "</TR>";
		
		return out;
	}
}
