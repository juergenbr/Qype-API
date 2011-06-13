package handler;

import java.util.ArrayList;
import java.util.Stack;

import model.Review;
import model.Reviews;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;


public class ReviewHandler implements ContentHandler{
	
	private boolean textStarted = false;
	private Review tmpReview;
	private StringBuilder text = new StringBuilder();
	private String tmpVal = "";
	private Reviews reviews = new Reviews();
	private Stack<XmlElement> elementStack;
	private boolean empty;
	
	//pages
	private String first;
	private String next;
	private String last;
	private String previous;
	private int total_entries;
	
	public boolean isEmpty(){
		return empty;
	}
	
	private class XmlElement{
		private String name;
		private boolean isEmpty = true;
 
		public XmlElement(String name) {
			this.name = name;
		}
 
		public void setNotEmpty(){
			isEmpty = false;
		}
 
	}
	
	public ReviewHandler(){
		tmpVal = "";
		empty = false;
		
	}
	
	public ReviewHandler(boolean textStarted, Review tmpReview,
			StringBuilder text, String tmpVal, ArrayList<Review> reviews,
			Stack<XmlElement> elementStack, boolean empty) {
		this.textStarted = textStarted;
		this.tmpReview = tmpReview;
		this.text = text;
		this.tmpVal = tmpVal;
		this.reviews = new Reviews();
		this.elementStack = elementStack;
		this.empty = empty;
	}

	public void startElement(String uri, String name, String qName, Attributes atts) {
		
		//parse pages
		if(name.equalsIgnoreCase("link")){
			if(atts.getValue("rel").equalsIgnoreCase("first")){
				first = atts.getValue("href");
				System.out.println("first page: "+first);
				reviews.setFirst(first);
			}
			else if (atts.getValue("rel").equalsIgnoreCase("next")){
				next = atts.getValue("href");
				System.out.println("next page: "+next);
				reviews.setNext(next);
			}
			else if(atts.getValue("rel").equalsIgnoreCase("last")){
				last = atts.getValue("href");
				System.out.println("last page: "+last);
				reviews.setLast(last);
			}
			else if(atts.getValue("rel").equalsIgnoreCase("previous")){
				previous = atts.getValue("href");
				System.out.println("previous page: "+previous);
				reviews.setPrevious(previous);
			}
			
		}
		
	   if(name.equalsIgnoreCase("content") && atts.getValue("type").equalsIgnoreCase("text")){
		   textStarted = true;
	   }
	   if(name.equalsIgnoreCase("review")){
		   tmpReview = new Review();
	   }
	   elementStack.push(new XmlElement(qName));
	}

	public void endElement(String uri, String name, String qName) {
		XmlElement elem = elementStack.peek();
		if (elem.name.equalsIgnoreCase("reviews") && elem.isEmpty) {
			empty = true;
		}
		
		//total reviews
		if(name.equalsIgnoreCase("total_entries")){
			total_entries = Integer.valueOf(tmpVal);
			reviews.setTotal_entries(total_entries);
		}
		
		if(name.equals("rating")) {
			   tmpReview.setRating(Integer.valueOf(tmpVal));
		}
		
		if(name.equals("review")) {
	      //System.out.println(tmpReview.toHtml());
	      reviews.addReview(tmpReview);
	   }
		if(name.equalsIgnoreCase("content")){
		  textStarted = false;
		  String fullTextDesc = text.toString(); // do whatever you want with this string now
		  tmpReview.setText(fullTextDesc);
		  text = new StringBuilder();
		}
	}

	public void characters(char[] buf, int offset, int length) {
	   if (textStarted) {
	      text.append(new String(buf, offset, length));
	   }
	   else{
		   tmpVal = new String(buf,offset,length);
	   }
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endPrefixMapping(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processingInstruction(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDocumentLocator(Locator arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void skippedEntity(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		tmpVal = "";
		empty = false;
		elementStack = new Stack<XmlElement>();
		
	}

	@Override
	public void startPrefixMapping(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}
	
	public Reviews getReviews(){
		return reviews;
	}
}
