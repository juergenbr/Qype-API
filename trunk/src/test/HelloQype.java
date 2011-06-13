package test;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Scanner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;


public class HelloQype {
	public static void main(String [] args) throws UnsupportedEncodingException{
		
		HelloQype h = new HelloQype(); 
		h.init();
		
	}
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://api.qype.com/v1/places/").build();
	}
	
	private void init(){
		Scanner in = new Scanner(System.in);
		System.out.println("Enter location:");
		String loc = in.nextLine();
		System.out.println("Enter City");
		String city = in.nextLine();
		try {
			System.out.println(getLocationInCity(loc,city));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getLocationInCity(String location, String city) throws UnsupportedEncodingException{
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		
		String places = "/places/";
		service.path(places);
		service.queryParam("show", location);
		service.queryParam("in", city);
		
		return service.queryParam("show", location).queryParam("in", city).queryParam("consumer_key", "blwUjMJZYCDi4qaPHgQy9Q").accept(MediaType.TEXT_XML).get(String.class);
	}
}
