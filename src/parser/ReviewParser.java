package parser;

import handler.ReviewHandler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import model.Place;
import model.Review;
import model.Reviews;

import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ReviewParser {
	private ArrayList<Place> places;
	private XMLReader reader;
	private ReviewHandler r;
	
	public Reviews parse(String xml){
		r = new ReviewHandler();
		try{
		reader = XMLReaderFactory.createXMLReader();
		//parse non-empty XML
		reader.setContentHandler(r);
		
		if(xml!=""){
			try{
				InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
				InputSource isource = new InputSource();
				isource.setByteStream(is);
				isource.setEncoding("UTF-8");
				reader.parse(isource);
				if(!r.isEmpty()){
					return r.getReviews();
				}
				else
					System.out.println("Dokument leer!!!!");
				}
				catch (SAXParseException e) {
				      System.err.println(e.getSystemId() + ":" +
				                         e.getLineNumber() + ":" +
				                         e.getColumnNumber() + ": " +
				                         e.getMessage());
				}
		}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return new Reviews();
	}
}
