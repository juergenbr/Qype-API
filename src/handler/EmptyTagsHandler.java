package handler;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
 
import java.util.Stack;
import java.util.Enumeration;
 
public class EmptyTagsHandler extends DefaultHandler {
 
	private StringBuilder xmlBuilder;
	private Stack<XmlElement> elementStack;
	private String processedXml;
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
 
	public EmptyTagsHandler(){
		xmlBuilder = new StringBuilder();
		elementStack = new Stack();
	}
 
	private String getElementXPath(){
		StringBuilder builder = new StringBuilder();
		for (Enumeration en=elementStack.elements();en.hasMoreElements();){
			builder.append(en.nextElement());
			builder.append("/");
		}
		return builder.toString();
	}
 
	public String getXml(){
		return processedXml;
	}
 
	public void startDocument() throws SAXException {
		xmlBuilder = new StringBuilder();
		elementStack.clear();
		processedXml = null;
	}
 
	public void endDocument() throws SAXException {
		processedXml = xmlBuilder.toString();
	}
 
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (!elementStack.empty()) {
			XmlElement elem = elementStack.peek();
			elem.setNotEmpty();
		}
		xmlBuilder.append("<");
		for (int i=0;i<attributes.getLength();i++){
			xmlBuilder.append(attributes.getQName(i));
			xmlBuilder.append("=");
			xmlBuilder.append(attributes.getValue(i));
		}
		xmlBuilder.append(">");
		elementStack.push(new XmlElement(qName));
	}
 
	public void endElement(String uri, String localName, String qName) throws SAXException {
		XmlElement elem = elementStack.peek();
		if (elem.isEmpty) {
 
			xmlBuilder.insert(xmlBuilder.length()-1, "/");
		} else {
			xmlBuilder.append("</>");
		}
		elementStack.pop();
	}
 
	public void characters(char ch[], int start, int length) throws SAXException {
		if (!elementStack.empty()) {
			XmlElement elem = elementStack.peek();
			elem.setNotEmpty();
		}
		String str = new String(ch, start, length);
		xmlBuilder.append(str);
	}
	public void ignorableWhitespace(char ch[], int start, int length) throws SAXException {
		String str = new String(ch, start, length);
		xmlBuilder.append(str);
	}
 
}
