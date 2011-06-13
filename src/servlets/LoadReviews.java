package servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import misc.ServletUtilities;
import misc.StringUtils;
import model.Review;
import model.Reviews;

import org.apache.commons.lang3.StringEscapeUtils;

import parser.ReviewParser;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class LoadReviews extends HttpServlet{

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private ReviewParser parser = new ReviewParser();
	private Reviews reviews = new Reviews();
	private String langSelect = "";
	String fromPlace = "";
	
	//varaibles for page change in reviews
	private String next;
	private String prev;
	private String first;
	private String last;
	
	public static String getUrl(HttpServletRequest req) {
	    String reqUrl = req.getRequestURL().toString();
	    String queryString = req.getQueryString();   // d=789
	    if (queryString != null) {
	        reqUrl += "?"+queryString;
	    }
	    return reqUrl;
	}
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(getUrl(request));
		PrintWriter out = response.getWriter();
		String title = "Search Results from Qype";
		//language selection
		String lang = (String)request.getParameter("lang");
		//true if user comes from place overview
		String fromPlace = (String)request.getParameter("fromPlace");
		//page change varaibles
		next = (String)request.getParameter("next");
		prev = (String)request.getParameter("previous");
		last = (String)request.getParameter("last");
		first = (String)request.getParameter("first");
		//REST result String
		String resultString = "";
		
		//define which String to parse
		String Uri = "";
		String nextPageUri = "";
		
		//german Reviews
		if(lang.equalsIgnoreCase("de")){
			langSelect = lang;
			//if user comes from Place-overview
			if(fromPlace.equalsIgnoreCase("true")){
				Uri = (String)request.getParameter("germanRev");
			}
			//if user comes from a review page
			else{
				if(!(next == null))
					Uri = next;
				if(!(prev == null))
					Uri = prev;
				if(!(first == null))
					Uri = first;
				if(!(last == null))
					Uri = last;
			}
		}
		
		//english Reviews
		if(lang.equalsIgnoreCase("en")){
			langSelect = lang;
			//if user comes from Place-overview
			if(fromPlace.equalsIgnoreCase("true")){
				Uri = (String)request.getParameter("englishRev");
			}
			//if user comes from a review page
			else{
				if(!(next == null))
					Uri = next;
				if(!(prev == null))
					Uri = prev;
				if(!(first == null))
					Uri = first;
				if(!(last == null))
					Uri = last;
			}
		}
		
		Uri = Uri.replaceAll("andrep", "&");
		resultString = getReviews(Uri);
		resultString = StringUtils.unescapeHTML(resultString);
		resultString = StringEscapeUtils.unescapeXml(resultString);
		resultString = resultString.replaceAll("&", "andrep");
		reviews = parser.parse(resultString);
		
		outputHtmlReviews(request, out);
}

private void outputHtmlReviews(HttpServletRequest request, PrintWriter out) {
	String title = "Reviews";
	out.println(ServletUtilities.headWithTitle(title) +
	        "<BODY BGCOLOR=\"#FDF5E6\">\n" +
	        "<H1 ALIGN=CENTER>" + title + "</H1>\n");
	
	out.println("<TABLE border='1'>");
	
	Iterator i = reviews.getReviewList().iterator();
	while(i.hasNext()){
		Review r = (Review)i.next();
		out.println(r.toHtml());
	}
	out.println("</TABLE>");
	
	if(reviews.getTotal_entries()>10){
		out.println("<CENTER>");
		out.println("<TABLE border='0'>");
		out.println("<TR><TD>");
		//Button first
		if(!reviews.getFirst().equalsIgnoreCase("null")){
		/*
		out.println("<form name='nextPage' action='/Qype_Test/LoadReviews' method='GET'>");
		out.println("<INPUT type='hidden' name='first' value='"+reviews.getFirst()+"'>");
		out.println("<INPUT type='hidden' name='lang' value='"+langSelect+"'>");
		out.println("<INPUT type='hidden' name='fromPlace' value='false'>");
		out.println("<input type='SUBMIT' value='<<'></input>");
		out.println("</form>");
		*/
		out.println("<a href="+'"'+request.getContextPath()+"/LoadReviews?first="+reviews.getFirst()+"&amp;lang="+langSelect+"&amp;fromPlace=false"+'"'+"> << </a>");	
		}
		
		if(!reviews.getPrevious().equalsIgnoreCase("null")){
		//Button prevoious
		/*
		out.println("<form name='prevPage' action='/Qype_Test/LoadReviews' method='GET'>");
		out.println("<INPUT type='hidden' name='previous' value='"+reviews.getPrevious()+"'>");
		out.println("<INPUT type='hidden' name='lang' value='"+langSelect+"'>");
		out.println("<INPUT type='hidden' name='fromPlace' value='false'>");
		out.println("<input type='SUBMIT' value='<'></input>");
		out.println("</form>");
		*/
		out.println("<a href="+'"'+request.getContextPath()+"/LoadReviews?first="+reviews.getPrevious()+"&amp;lang="+langSelect+"&amp;fromPlace=false"+'"'+"> < </a>");
		}
		
		if(!reviews.getNext().equalsIgnoreCase("null")){
		//Button next
			/*
		out.println("<form name='nextPage' action='/Qype_Test/LoadReviews' method='GET'>");
		out.println("<INPUT type='hidden' name='next' value='"+reviews.getNext()+"'>");
		out.println("<INPUT type='hidden' name='lang' value='"+langSelect+"'>");
		out.println("<INPUT type='hidden' name='fromPlace' value='false'>");
		out.println("<input type='SUBMIT' value='>'></input>");
		out.println("</form>");
		*/
			out.println("<a href="+'"'+request.getContextPath()+"/LoadReviews?first="+reviews.getNext()+"&amp;lang="+langSelect+"&amp;fromPlace=false"+'"'+"> > </a>");
		}
		
		if(!reviews.getLast().equalsIgnoreCase("null")){
		//Button last
			/*
		out.println("<form name='lastPage' action='/Qype_Test/LoadReviews' method='GET'>");
		out.println("<INPUT type='hidden' name='last' value='"+reviews.getLast()+"'>");
		out.println("<INPUT type='hidden' name='lang' value='"+langSelect+"'>");
		out.println("<INPUT type='hidden' name='fromPlace' value='false'>");
		out.println("<input type='SUBMIT' value='>>'></input>");
		out.println("</form>");
		*/
			out.println("<a href="+'"'+request.getContextPath()+"/LoadReviews?first="+reviews.getLast()+"&amp;lang="+langSelect+"&amp;fromPlace=false"+'"'+"> >> </a>");
		out.println("</TD>");
		out.println("</TR>");
		out.println("</TABLE>");
		out.println("</CENTER>");
		}
}
	
	
out.println("</BODY>");
}
	
	public String getReviews(String uri) throws UnsupportedEncodingException{
		System.out.println(uri+"?consumer_key=blwUjMJZYCDi4qaPHgQy9Q");
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
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
