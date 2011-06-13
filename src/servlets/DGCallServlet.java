package servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang3.StringEscapeUtils;

import parser.PlacesParser;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import simplelatlng.LatLng;
import view.PlacesHtmlOutput;

import misc.StringUtils;
import model.Geocoder;
import model.Place;

/**
 * Servlet implementation class DGCallServlet
 */
public class DGCallServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DGCallServlet() {
        super();
        // TODO Auto-generated constructor stub
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println(getUrl(request));
		String location = (String)request.getParameter("location");
		String lat = (String)request.getParameter("lat");
		String lng = (String)request.getParameter("lng");
		lat = lat.replace(',', '.');
		lng = lng.replace(',', '.');
		Geocoder gc = new Geocoder();
		String city = gc.reverseGeoCode(new LatLng(Float.valueOf(lat),Float.valueOf(lng)));
		System.out.println("City: "+city);
		
		//like SearchQype
		PrintWriter out = response.getWriter();
		String result = getLocationInCity(location,city);
		
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
		ArrayList<Place> places = parser.parse(result, Double.valueOf(lat), Double.valueOf(lng));
		System.out.println("Gefundene Locations: "+places.size());
		PlacesHtmlOutput output = new PlacesHtmlOutput();
		if(places!=null && places.size()>0){
			int pos = 0;
			double dist = 1000.0;
				//Output places (HTML)
				Iterator<Place> i = places.iterator();
				while(i.hasNext()){
					Place p = (Place)i.next();
					double newDist = gc.distance(Double.valueOf(lat), Double.valueOf(lng), p.getPoint().getLatitude(), p.getPoint().getLongitude(), 'K');
					System.out.println("Alte Distanz: "+dist+", "+"Neue Distanz: "+newDist+" "+p.getAddress().getStreet());
					if(newDist<=dist){
						pos = places.indexOf(p);
						dist = newDist;
						System.out.println("Neue Position: "+pos);
					}	
				}
		
				
				output.outputHtmlPlaces(request, out, "Details", places.get(pos));
		}
		else output.outputEmptyPlace(request, out, "Kein Eintrag gefunden");
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	public static String getUrl(HttpServletRequest req) {
	    String reqUrl = req.getRequestURL().toString();
	    String queryString = req.getQueryString();   // d=789
	    if (queryString != null) {
	        reqUrl += "?"+queryString;
	    }
	    return reqUrl;
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
}
