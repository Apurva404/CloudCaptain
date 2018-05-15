package rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import dao.UserAzureDBResponseDao;
import entity.azure.UserAzureDBResponse;

public class AzureDBCreationRESTClient {
	public UserAzureDBResponse ClientGetCall(int userId,int requestId, String dbInstanceName, String masterUsername, String masterPassword, String dbType) throws Exception {
		URI uri = new URI("http", "//34.217.134.45:8080/CloudCaptain/MicrosoftDB/create/" + userId + "/" + requestId + "/"
				+ dbInstanceName + "/" + masterUsername   + "/" + masterPassword + "/" + dbType, null);

		URL url = uri.toURL();
		String Url = url.toString();
		System.out.println(Url);
		
		String output = null;
		String instanceEndpoint = null; 
		UserAzureDBResponse newAzureResponse = null;

		HttpGet get = new HttpGet(Url);
		try {
			HttpClient httpClient_load = new DefaultHttpClient();
			HttpResponse response_load = httpClient_load.execute(get);
			System.out.println("Hello after coming from API");
			if (response_load.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed: HTTP error code :" + response_load.getStatusLine().getStatusCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((response_load.getEntity().getContent())));

			if ((output = br.readLine()) != null)
			{
				JSONObject newObject = new JSONObject(output);
				System.out.println(newObject);;
				instanceEndpoint = newObject.getString("DatabaseIP");
				newAzureResponse = new UserAzureDBResponse(requestId, userId,instanceEndpoint);
				UserAzureDBResponseDao.insert(newAzureResponse);	
			}
			httpClient_load.getConnectionManager().shutdown();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Database details are:" + instanceEndpoint);
		return newAzureResponse;
	}
}
