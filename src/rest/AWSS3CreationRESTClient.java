 package rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class AWSS3CreationRESTClient {
	public String ClientGetCall (String bucketName) throws Exception{
		URI uri = new URI("http", "//34.217.134.45:8080/CloudCaptain/S3/create/" + bucketName, null);
		URL url= uri.toURL();
		String Url = url.toString();
		String output= null;

		HttpGet get = new HttpGet(Url);	
	    try{
	    HttpClient httpClient_load = new DefaultHttpClient();
		HttpResponse response_load = httpClient_load.execute(get);
		System.out.println("Hello after coming from API");
		if(response_load.getStatusLine().getStatusCode()!=200) 
		{
			throw new RuntimeException("Failed: HTTP error code :" + response_load.getStatusLine().getStatusCode());
	    }
		BufferedReader br= new BufferedReader(new InputStreamReader((response_load.getEntity().getContent())));
	    if ((output=br.readLine())!= null)
	    {			    	
	        System.out.println(output.toString());
	    } 
	    httpClient_load.getConnectionManager().shutdown();
			}catch(Exception e){
				System.out.println(e);
			 }	
		return output.toString();
	}
}
