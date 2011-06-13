package model;

public class Category {
	private String id;
	private String full_title;
	/**
	 * @return the full_title
	 */
	public String getFull_title() {
		return full_title;
	}
	/**
	 * @param fullTitle the full_title to set
	 */
	public void setFull_title(String fullTitle) {
		full_title = fullTitle;
	}
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
	public Category(String fullTitle, String id) {
		full_title = fullTitle;
		this.id = id;
	}
	
	public Category(){
		full_title = "";
		id = "";
	}
	
	public String toHTML(){
		String out = ""; 
		  out.concat("<TR>");
	      out.concat("<TD>");
	      out.concat("Kategorie:");
	      out.concat("</TD>");
	      out.concat("<TD>");
	      out.concat(this.full_title);
	      out.concat("</TD>");
	      out.concat("</TR>");
	     return out;
	}
}
