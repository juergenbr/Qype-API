package servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import misc.ServletUtilities;
import misc.StringUtils;
import model.Place;

import org.apache.commons.lang3.StringEscapeUtils;

import parser.PlacesParser;
import sun.security.provider.certpath.Builder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.uri.UriBuilderImpl;


public class SearchNearby extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String title = "Search Results from Qype";
		String lat = (String)request.getParameter("lat");
		String lng = (String)request.getParameter("lng");
		
		String result = getNearbyLocation(Float.valueOf(lat), Float.valueOf(lng));
		
		result = StringUtils.unescapeHTML(result);
		result = StringEscapeUtils.unescapeXml(result);
		result = result.replaceAll("&", "andrep");
		
		//System.out.println(result);
		PlacesParser parser = new PlacesParser();
		ArrayList<Place> places = parser.parse(result);
		System.out.println("Gefundene Locations: "+places.size());
		
		out.println(ServletUtilities.headWithTitle(title) +
                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=CENTER>" + title + "</H1>\n");
		
		
		int pos = 0;
		int count = 0;
		//if places were found
		if(places.size()>0){
		/*
		//Output places (HTML)
		Iterator i = places.iterator();
		
		while(i.hasNext()){
			Place p = (Place)i.next();
			if(p.reviewSum()>count){
					System.out.println("Summe Reviews/Pos"+p.reviewSum()+"/"+pos);
					pos = places.indexOf(p);
					count = p.reviewSum();
			}	
		}
		*/
		
		Iterator i = places.iterator();
		while(i.hasNext()){
			out.println("<TABLE border='1'>");
			Place p = (Place)i.next();
			String printstring = p.toHTML();
			printstring = printstring.replaceAll("andrep", "&");
			out.println(printstring);
			out.println("<TR></TR>");
			out.println("<TR></TR>");
			out.println("</TABLE>");
			out.println("<form name='showRev' action='/Qype_Test/LoadReviews' method='GET'>");
			out.println("<INPUT type='hidden' name='germanRev' value='"+p.getReviews_de().getReview_uris()+"'>");
			out.println("<INPUT type='hidden' name='englishRev' value='"+p.getReviews_en().getReview_uris()+"'>");
			out.println("<input type='SUBMIT' value='Reviews'></input>");
			out.println("</form>");
		}
		
		}
	}
	
	public String getNearbyLocation(float lat, float lng) throws UnsupportedEncodingException{
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		URI uri = UriBuilder.fromUri("http://api.qype.com/v1/positions/"+lat+","+lng+"/places").build();
		WebResource service = client.resource(uri);
		
		String ret = service.queryParam("consumer_key", "blwUjMJZYCDi4qaPHgQy9Q").accept(MediaType.TEXT_XML).type(MediaType.TEXT_XML).get(String.class);
		return ret;
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}