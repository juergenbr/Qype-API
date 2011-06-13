package model;

import javax.ws.rs.core.MediaType;

import parser.GeoCodeParser;

import simplelatlng.LatLng;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;


public class Geocoder {
    
    private static final String YAHOO_API_BASE_URL = "http://where.yahooapis.com/geocode";
    private static final String API_Key="GzG5LrnV34GTMyik4TdsbKbyDy0H2miZqNr4RAsL7ZEdzTnViKicDE.5u_tMZfZpeBn_cA--";
    
    public String reverseGeoCode(LatLng latlng) {
        
        String url = String.format(YAHOO_API_BASE_URL, String.valueOf(latlng.getLatitude()), String.valueOf(latlng.getLongitude()));
        
        ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(url);
		String ret = service.queryParam("q", latlng.getLatitude()+" "+latlng.getLongitude()).queryParam("gflags", "R").queryParam("appid", API_Key).accept(MediaType.TEXT_XML).type(MediaType.TEXT_XML).get(String.class);
		System.out.println(ret);
		//TODO Parse XML and get City Name
		GeoCodeParser p = new GeoCodeParser();
		String city = p.parse(ret);
		return city;
    }
    
    public double distance(double lat1, double lon1, Double lat, Double lng, char unit) {
    	  double theta = lon1 - lng;
    	  double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat)) * Math.cos(deg2rad(theta));
    	  dist = Math.acos(dist);
    	  dist = rad2deg(dist);
    	  dist = dist * 60 * 1.1515;
    	  if (unit == 'K') {
    	    dist = dist * 1.609344;
    	  } else if (unit == 'N') {
    	  	dist = dist * 0.8684;
    	    }
    	  return (dist);
    	}

    	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    	/*::  This function converts decimal degrees to radians             :*/
    	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    	private double deg2rad(double deg) {
    	  return (deg * Math.PI / 180.0);
    	}

    	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    	/*::  This function converts radians to decimal degrees             :*/
    	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    	private double rad2deg(double rad) {
    	  return (rad * 180.0 / Math.PI);
    	}

}