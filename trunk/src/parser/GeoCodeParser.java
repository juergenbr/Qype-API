package parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import handler.GeoCodeHandler;

import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class GeoCodeParser {
	private XMLReader reader;
	private GeoCodeHandler h;
	public String parse(String xml){
	try{
		reader = XMLReaderFactory.createXMLReader();
		h = new GeoCodeHandler();
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
				return h.getCity();
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
	return "";
	}
	
}
