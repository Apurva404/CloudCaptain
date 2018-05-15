package rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.UserAWSComputeLoadBalancerDetailsDao;
import dao.UserAWSComputeResponseDao;
import entity.aws.UserAWSComputeLoadBalancerDetails;
import entity.aws.UserAWSComputeResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class AWSResourceCreationRESTClient {

	public ArrayList<UserAWSComputeResponse> ClientGetCall(int userId, String ImageType, String instanceType,
			int noOfInstance, String s3Link, String loadBalancer, int requestId) throws Exception {
		URI uri = new URI("http", "//34.217.134.45:8080/CloudCaptain/EC2/create/" + userId + "/" + requestId + "/"
				+ ImageType + "/" + instanceType + "/" + noOfInstance + "/" + s3Link + "/" + loadBalancer, null);

		ArrayList<UserAWSComputeResponse> myList = new ArrayList<UserAWSComputeResponse>();
		URL url = uri.toURL();
		String Url = url.toString();
		String output = null;
		String instanceDNS = null;
		String instanceIP = null;
		String instanceId = null;
		String instanceState = null;
		String instanceLaunchTime = null;

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
				JSONArray outputArray = new JSONArray(output);
				System.out.println(outputArray);
				UserAWSComputeLoadBalancerDetails newLoadBalancer = null;
				if (loadBalancer.equalsIgnoreCase("true")) {
					JSONObject newObject = outputArray.getJSONObject(noOfInstance);
					String elbIdentifier = newObject.getString("ELBIdentifier");
					String elbEndpoint = newObject.getString("ELBEndpoint");
					int requestIdLb = newObject.getInt("requestID");
					newLoadBalancer = new UserAWSComputeLoadBalancerDetails(elbIdentifier, elbEndpoint, requestIdLb);
					UserAWSComputeLoadBalancerDetailsDao.insert(newLoadBalancer);
				}
				for (int i = 0; i < noOfInstance; i++) {
					JSONObject newObject = outputArray.getJSONObject(i);
					System.out.println(outputArray.getJSONObject(i));
					// request_Id = newObject.getInt("requestID");
					// user_Id = newObject.getInt("userID");
					instanceDNS = newObject.getString("instanceDNS");
					instanceIP = newObject.getString("instanceIP");
					instanceState = newObject.getString("instanceState");
					instanceLaunchTime = newObject.getString("instanceLaunchTime");
					instanceId = newObject.getString("instanceID");
					UserAWSComputeResponse newComputeResponse = new UserAWSComputeResponse(requestId, userId,
							instanceDNS, instanceState, instanceLaunchTime, instanceIP, instanceId);
					newComputeResponse.setLoadbalancer(newLoadBalancer);
					UserAWSComputeResponseDao.insert(newComputeResponse);
					myList.add(newComputeResponse);
				}
			}

			httpClient_load.getConnectionManager().shutdown();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Instance details are:" + instanceDNS + instanceIP + instanceState + instanceLaunchTime + instanceId);
		return myList;
	}

}
