package handler;

import java.util.ArrayList;
import java.util.Stack;

import model.Address;
import model.Category;
import model.Geocoder;
import model.Place;
import model.ReviewURIList;

import org.xml.sax.*;

import simplelatlng.LatLng;


public class PlacesHandler implements ContentHandler{
	
	private double lat;
	private double lng;
	private boolean dgcall;
	
	private ArrayList<Place> places;
	private ArrayList<Place> places_final;
	private String tempVal;
	private Place tmpPlace;
	private Category tmpCat;
	private Address tmpAd;
	private double tmpdistance;
	private boolean cat;
	private Locator locator;
	private ReviewURIList tmpRevDE;
	private ReviewURIList tmpRevEN;
	private Stack<XmlElement> elementStack;
	private boolean empty;
	
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
	
	public void setLatLng(Double lat, Double lng){
		this.lat = lat;
		this.lng = lng;
		dgcall = true;
	}
	
	
	public boolean isEmpty(){
		return empty;
	}
	
	public PlacesHandler(){
		super();
		dgcall = false;
		lat = 0.0;
		lng = 0.0;
		tempVal = "";
		tmpPlace = new Place();
		tmpCat = new Category();
		tmpAd = new Address();
		cat = false;
	}
	
	public void startElement(String uri, String localName,
	        String qName, Attributes attributes)
	        throws SAXException{
		tempVal = "";
		
		//place found; create new place variable
		if(qName.equalsIgnoreCase("place")){
			tmpPlace = new Place();
		}
		
		//place found; create new place variable
		if(qName.equalsIgnoreCase("places")){
			places = new ArrayList<Place>();
		}
		
		//category found; create new category varaible
		if(qName.equalsIgnoreCase("category")){
			cat = true;
			tmpCat = new Category();
		}
		
		//address found; create new address varaible
		if(qName.equalsIgnoreCase("category")){
			tmpAd = new Address();
		}
		
		//Links DE
		int length = attributes.getLength();
		
		if(qName.equalsIgnoreCase("link")){
			//search for element "count"
			int poscount = 0;
			boolean reviewlink = false;
			for(int i=0; i<attributes.getLength();i++){
				if(attributes.getQName(i).equalsIgnoreCase("count")){
					poscount = i;
					reviewlink=true;
					System.out.println("Review-Link gefunden!, Reviews: "+attributes.getValue(poscount));
					
				}
			}
			
			//get count position
			int hreflang=0;
			for(int i=0; i<attributes.getLength();i++){
				if(attributes.getLocalName(i).equals("hreflang"))
					hreflang = i;
			}
			if(reviewlink && attributes.getValue(hreflang).equalsIgnoreCase("de")){
				tmpRevDE = new ReviewURIList();
				tmpRevDE.setLanguage("de"); 
				tmpRevDE.setReview_uris(attributes.getValue("href").toString());
				tmpRevDE.setCount(Integer.valueOf(attributes.getValue("count")));
				tmpPlace.setReviews_de(tmpRevDE);
				System.out.println("German reviews:" + tmpRevDE.getCount());
			}
			if(reviewlink && attributes.getValue(hreflang).equalsIgnoreCase("en")){
				tmpRevEN = new ReviewURIList();
				tmpRevEN.setLanguage("en");
				tmpRevEN.setReview_uris(attributes.getValue("href").toString());
				tmpRevEN.setCount(Integer.valueOf(attributes.getValue("count")));
				tmpPlace.setReviews_en(tmpRevEN);
				System.out.println("Engisch reviews:" + tmpRevEN.getCount());
			}
		}
		elementStack.push(new XmlElement(qName));
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}
	
	public void endElement(String uri, String localName,
	          String qName)
	          throws SAXException {
		XmlElement elem = elementStack.peek();
		if (elem.name.equalsIgnoreCase("places") && elem.isEmpty) {
			empty = true;
		}
		//add Place to ArrayList
		if(qName.equalsIgnoreCase("place")){
			System.out.println("place end");
			places.add(tmpPlace);
			System.out.println(places.size());
			System.out.println("Entfernung: "+tmpdistance);
			//if(dgcall && tmpdistance <= 0.5){
				places_final = places;
				System.out.println("Summe Reviews!" + tmpPlace.reviewSum());
				System.out.println("-----------------------------------------");
			//}
		}
		
		//Place title
		if(qName.equalsIgnoreCase("title") && !cat){
			System.out.println("title: "+tempVal);
			tmpPlace.setTitle(tempVal);
		}
		//Place closed (true/false)
		if(qName.equalsIgnoreCase("closed")){
			System.out.println("closed: "+tempVal);
			if(tempVal.equalsIgnoreCase("false"))
				tmpPlace.setClosed(false);
			else if(tempVal.equalsIgnoreCase("true")) 
				tmpPlace.setClosed(true);
		}
		//Place URL
		if(qName.equalsIgnoreCase("url")){
				System.out.println("url: "+tempVal);
				tmpPlace.setUrl(tempVal);
		}
		//Place avgerage rating
		if(qName.equalsIgnoreCase("average_rating")){
			System.out.println("rating: "+tempVal);
			tmpPlace.setRating_avg(Integer.valueOf(tempVal));
		}
		//Place id
		if(qName.equalsIgnoreCase("id") && !cat){
			String idString = tempVal;
			int begin = idString.lastIndexOf("/");
			String id = idString.substring(begin+1);
			System.out.println("ID: "+id);
			tmpPlace.setId(id);
		}
		//Place Location
		if(qName.equalsIgnoreCase("point")){
			System.out.println("Lat/Lng: "+tempVal);
			String point = tempVal;
			int end = point.lastIndexOf(",");
			String lat = point.substring(0, end);
			String lng = point.substring(end+1);
			Geocoder gc = new Geocoder();
			tmpdistance = gc.distance(Float.valueOf(lat), Float.valueOf(lng), this.lat, this.lng, 'K');
			tmpPlace.setPoint(new LatLng(Float.valueOf(lat),Float.valueOf(lng)));
		}
		//Place opening hours
		if(qName.equalsIgnoreCase("opening_hours")){
			tmpPlace.setOpening_hours(tempVal);
		}
		//Place Phone number
		if(qName.equalsIgnoreCase("phone")){
			tmpPlace.setPhone(tempVal);
		}
		//Place Adress
			if(qName.equalsIgnoreCase("country_code")){
				tmpAd.setCountry_code(tempVal);
			}
			if(qName.equalsIgnoreCase("housenumber")){
				tmpAd.setHousenumber(tempVal);
			}
			if(qName.equalsIgnoreCase("postcode")){
				tmpAd.setPostcode(tempVal);
			}
			if(qName.equalsIgnoreCase("city")){
				tmpAd.setCity(tempVal);
			}
			if(qName.equalsIgnoreCase("street")){
				tmpAd.setStreet(tempVal);
			}
		//Place Address
		if(qName.equalsIgnoreCase("address")){
			tmpPlace.setAddress(tmpAd);
		}
		//Category ID
		if(qName.equalsIgnoreCase("id") && cat){
			String idString = tempVal;
			int begin = idString.lastIndexOf("/");
			String id = idString.substring(begin+1);
			tmpCat.setId(id);
		}
		//Category Title
		if(qName.equalsIgnoreCase("full_title")){
			tmpCat.setFull_title(tempVal);
		}
		if(qName.equalsIgnoreCase("category")){
			tmpPlace.setCategory(tmpCat);
			cat = false;
		}
	}

	/**
	 * @return the places
	 */
	public ArrayList<Place> getPlaces() {
		return places_final;
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
		this.locator = locator;
		
	}

	@Override
	public void skippedEntity(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("Anfang des Dokuments");
		elementStack = new Stack();
		elementStack.clear();
		places = new ArrayList<Place>();
		tempVal = "";
		tmpPlace = new Place();
		tmpCat = new Category();
		tmpAd = new Address();
		tmpdistance = 0.0;
		cat = false;
		empty = false;
	}

	@Override
	public void startPrefixMapping(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}
}
