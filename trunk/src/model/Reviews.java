package model;

import java.util.ArrayList;

public class Reviews {
	private String first;
	private String last;
	private String next;
	private String previous;
	private int total_entries;
	
	private ArrayList<Review> reviewList;

	/**
	 * @return the first
	 */
	public String getFirst() {
		return first;
	}

	/**
	 * @param first the first to set
	 */
	public void setFirst(String first) {
		this.first = first;
	}

	/**
	 * @return the last
	 */
	public String getLast() {
		return last;
	}

	/**
	 * @param last the last to set
	 */
	public void setLast(String last) {
		this.last = last;
	}

	/**
	 * @return the next
	 */
	public String getNext() {
		return next;
	}

	/**
	 * @param next the next to set
	 */
	public void setNext(String next) {
		this.next = next;
	}

	/**
	 * @return the previous
	 */
	public String getPrevious() {
		return previous;
	}

	/**
	 * @param previous the previous to set
	 */
	public void setPrevious(String previous) {
		this.previous = previous;
	}

	/**
	 * @return the total_entries
	 */
	public int getTotal_entries() {
		return total_entries;
	}

	/**
	 * @param totalEntries the total_entries to set
	 */
	public void setTotal_entries(int totalEntries) {
		total_entries = totalEntries;
	}

	/**
	 * @return the reviewList
	 */
	public ArrayList<Review> getReviewList() {
		return reviewList;
	}

	/**
	 * @param reviewList the reviewList to set
	 */
	public void setReviewList(ArrayList<Review> reviewList) {
		this.reviewList = reviewList;
	}
	
	public Reviews(){
		first = "";
		last = "";
		next ="";
		previous = "";
		total_entries = 0;
		
		reviewList = new ArrayList<Review>();
	}
	
	public void addReview(Review r){
		reviewList.add(r);
	}
}
