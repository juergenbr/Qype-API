package view;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import misc.ServletUtilities;
import model.Geocoder;
import model.Place;

public class PlacesHtmlOutput {
	public void outputHtmlPlaces(HttpServletRequest request, PrintWriter out, String title,
			Place p) {
		out.println(ServletUtilities.headWithTitle(title) +
                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=CENTER>" + title + "</H1>\n");
		
		
		String printstring = p.toHTML();
		printstring = printstring.replaceAll("andrep", "&");
		out.println(printstring);
		/*
		out.println("<form name='showDE' action='/Qype_Test/LoadReviews' method='GET'>");
		out.println("<INPUT type='hidden' name='germanRev' value='"+p.getReviews_de().getReview_uris()+"'>");
		out.println("<INPUT type='hidden' name='lang' value='de'>");
		out.println("<INPUT type='hidden' name='fromPlace' value='true'>");
		out.println("<input type='SUBMIT' value='Deutsche Reviews'></input>");
		out.println("</form>");
		*/
		out.println("<a href="+'"'+request.getContextPath()+"/LoadReviews?germanRev="+p.getReviews_de().getReview_uris()+"&amp;lang="+"de"+"&amp;fromPlace=true"+'"'+"> Deutsche Reviews </a>");
		/*
		out.println("<form name='showEN' action='/Qype_Test/LoadReviews' method='GET'>");
		out.println("<INPUT type='hidden' name='englishRev' value='"+p.getReviews_en().getReview_uris()+"'>");
		out.println("<INPUT type='hidden' name='lang' value='en'>");
		out.println("<INPUT type='hidden' name='fromPlace' value='true'>");
		out.println("<input type='SUBMIT' value='Englische Reviews'></input>");
		out.println("</form>");
		*/
		out.println("<a href="+'"'+request.getContextPath()+"/LoadReviews?englishRev="+p.getReviews_en().getReview_uris()+"&amp;lang="+"en"+"&amp;fromPlace=true"+'"'+"> Englische Reviews </a>");
		out.println("</BODY>");
		out.println("</HTML>");
		}
	
	public void outputEmptyPlace(HttpServletRequest request, PrintWriter out, String title){
		out.println(ServletUtilities.headWithTitle(title) +
                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=CENTER>" + title + "</H1>\n");
		out.println("</BODY></HTML>");
	}
}
