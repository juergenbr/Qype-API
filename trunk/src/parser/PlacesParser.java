package parser;

import handler.EmptyTagsHandler;
import handler.PlacesHandler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;

import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import model.Place;

public class PlacesParser {
	
	private ArrayList<Place> places;
	private XMLReader reader;
	private PlacesHandler h;
	
	public PlacesParser(){
		places = new ArrayList<Place>();
	}
	
	public ArrayList<Place> parse(String xml){
		try{
			h = new PlacesHandler();
			reader = XMLReaderFactory.createXMLReader();
			//parse non-empty XML
			reader.setContentHandler(h);
			if(xml!=""){
				try{
				InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
				InputSource isource = new InputSource();
				isource.setByteStream(is);
				isource.setEncoding("UTF-8");
				reader.parse(isource);
				if(!h.isEmpty()){
					return h.getPlaces();
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
		return new ArrayList();
		
	}
	
	public ArrayList<Place> parse(String xml, Double lat, Double lng){
		try{
			h = new PlacesHandler();
			reader = XMLReaderFactory.createXMLReader();
			//parse non-empty XML
			h.setLatLng(lat,lng);
			reader.setContentHandler(h);
			if(xml!=""){
				try{
				InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
				InputSource isource = new InputSource();
				isource.setByteStream(is);
				isource.setEncoding("UTF-8");
				reader.parse(isource);
				if(!h.isEmpty()){
					return h.getPlaces();
				}
				else
					System.out.println("Dokument leer!!!!");
					return new ArrayList<Place>();
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
		return new ArrayList();
		
	}
}