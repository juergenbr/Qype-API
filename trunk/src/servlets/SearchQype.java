package servlets;




import java.io.*;
import java.net.URI;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang3.StringEscapeUtils;


import parser.PlacesParser;
import view.PlacesHtmlOutput;

import misc.ServletUtilities;
import misc.StringUtils;
import model.Place;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import java.util.*;



/**
 * Servlet implementation class SearchQype
 */
public class SearchQype extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchQype() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String title = "gefundene Locations";
		String l = (String)request.getParameter("location");
		String c = (String)request.getParameter("city");
		
		String result = getLocationInCity(l,c);
		
		result = StringUtils.unescapeHTML(result);
		result = StringEscapeUtils.unescapeXml(result);
		/*result = result.replaceAll("Š", "ae");
		result = result.replaceAll("š", "oe");
		result = result.replaceAll("Ÿ", "ue");
		result = result.replaceAll("€", "Ae");
		result = result.replaceAll("…", "Oe");
		result = result.replaceAll("†", "Ue");
		result = result.replaceAll("§", "ss");*/
		result = result.replaceAll("&", "andrep");
		System.out.println(result);
		//System.out.println(result);
		PlacesParser parser = new PlacesParser();
		ArrayList<Place> places = parser.parse(result);
		if(places != null){
			System.out.println("Gefundene Locations: "+places.size());
		}
		
		PlacesHtmlOutput output = new PlacesHtmlOutput();
		if(places!=null && places.size()>0){
			int pos = 0;
			int count = 0;
				//Output places (HTML)
				Iterator<Place> i = places.iterator();
		
				while(i.hasNext()){
					Place p = (Place)i.next();
					if(p.reviewSum()>count){
						pos = places.indexOf(p);
						count = p.reviewSum();
					}	
				}
				output.outputHtmlPlaces(request, out, title, places.get(pos));
		}	
		else
			output.outputEmptyPlace(request, out, "Kein Eintrag gefunden");
	}
	
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://api.qype.com/v1/places/").build();
	}
	
	public String getLocationInCity(String location, String city) throws UnsupportedEncodingException{
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		
		String places = "/places/";
		service.path(places);
		service.queryParam("show", location);
		service.queryParam("in", city);
		
		String ret = service.queryParam("show", location).queryParam("in", city).queryParam("consumer_key", "blwUjMJZYCDi4qaPHgQy9Q").accept(MediaType.TEXT_XML).type(MediaType.TEXT_XML).get(String.class);
		//System.out.println("Web REssource: "+service.toString());
		return ret;
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
