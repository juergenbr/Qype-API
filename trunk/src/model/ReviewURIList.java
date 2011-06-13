package model;

import java.util.ArrayList;

public class ReviewURIList {
	private String language;
	private String review_uris;
	private int count;
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * @return the review_uris
	 */
	public String getReview_uris() {
		return review_uris;
	}
	/**
	 * @param reviewUris the review_uris to set
	 */
	public void setReview_uris(String reviewUri) {
		review_uris = reviewUri;
	}
	
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	public ReviewURIList(String language, String reviewUri, int count) {
		this.language = language;
		review_uris = reviewUri;
		this.count = count;
	}
	
	public ReviewURIList(){
		this.language = "";
		review_uris= "";
		this.count=0;
	}
}
