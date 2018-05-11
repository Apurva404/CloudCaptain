package rest;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import constants.EntityConstants;
import dao.UserAWSComputeResponseDao;
import dao.UserAWSDBRequestDao;
import dao.UserAWSS3RequestDao;
import dao.UserDao;
import dao.UserDeploymentRequestDao;
import entity.User;
import entity.UserGCPDBRequest;
import entity.aws.UserAWSComputeResponse;
import entity.aws.UserAWSDBRequest;
import entity.aws.UserAWSDeploymentRequest;
import entity.aws.UserAWSS3Request;
import util.JSONUtil;

@Path("user")
public class UserService {
	@POST
	@Consumes("application/json")
	@Path("/addNewUser")
	@Produces("application/json")
	public Response addInformation(final String msg) {     
		System.out.println("POST APIs is called" );
		int userId;
		String fullname,emailId,phone,company,password;
		userId = EntityConstants.INVALID_ID;
		fullname = emailId = phone = company = password = "";
		JSONObject json;
		
		try {
			System.out.println(msg);;
			json = new JSONObject(msg);
			
			fullname = json.getString("fullName");
			emailId = json.getString("emailId");
			phone = json.getString("phone");
			company = json.getString("company");
			password = json.getString("password");
			
		} catch (JSONException e) {
			e.printStackTrace();
			//return Response.status(400).entity("{\"string\":\"JSON parsing failed.\"}").build();
			return Response.status(400).entity("{\"string\":\"JSON parsing failed.\"}").build();
		} 
		User newUser = new User(fullname, emailId, phone, company, password);
		System.out.println( newUser.getId() + "," + newUser.getFullname() + "," + newUser.getEmailId() + "," + newUser.getPhone() +
				"," + newUser.getCompany() + "," + newUser.getUserPassword() );
		
		userId = UserDao.insert(newUser);
		
		if(userId != EntityConstants.INVALID_ID) {
			
			//return Response.status(200).entity("{\"UserId\":" + userId + "}").build();
			return Response.status(200).entity("{\"UserId\":" + userId + "}").build();
			
		} else {
			// add some meaningful message
			//return Response.status(500).entity("{\"string\":\"Request failed!\"}").build();
			return Response.status(500).entity("{\"string\":\"Request failed!\"}").header("Access-Control-Allow-Origin", "*")
			.header("Access-Control-Allow-Methods","*")
			.header("Access-Control-Allow-Headers","*").build();
		}
	}
	
	@POST
	@Consumes("application/json")
	@Path("userCredentials/verify")
	@Produces("application/json")
	public Response verifyCredentials(final String msg) {
		int userId;
		String username, password;
		
		JSONObject json = new JSONObject(msg);
		userId = EntityConstants.INVALID_ID;
		username = password = "";
		
		try {
			username = json.getString("username");
			password = json.getString("password");
		} catch (JSONException e) {
			e.printStackTrace();
			return Response.status(400).entity("{\"string\": \"JSON parsing failed.\"}").build();
		}
		
		User user = new User(username, password);
		System.out.println( user.getFullname() + "," + user.getUserPassword());
			
		userId = UserDao.getUserId(user);
					
		if(userId != EntityConstants.INVALID_ID)
			return Response.status(200).entity("{\"UserID\":" + userId + "}").build();
		
		return Response.status(401).entity("{\"string\":\"Verification failed!\"}").build();
	}
	
	@POST
	@Consumes("application/json")
	@Path("/addNewDeployment")
	@Produces("application/json")
	public Response addDeploymentRequest(final String msg) {
		int userId;
		String deploymentName, cloudType;
		
		JSONObject json = new JSONObject(msg);
		userId = EntityConstants.INVALID_ID;
		deploymentName = cloudType = "";
		
		try {
				deploymentName = json.getString("DeploymentName");
				cloudType = json.getString("CloudType");
				userId = json.getInt("UserId");
		} catch (JSONException e) {
			e.printStackTrace();
			return Response.status(400).entity("{\"string\": \"JSON parsing failed.\"}").build();
		}
		
		UserAWSDeploymentRequest newDeploymentRequest = new UserAWSDeploymentRequest(deploymentName, cloudType, userId);
		UserDeploymentRequestDao.insert(newDeploymentRequest);
		System.out.println( newDeploymentRequest.getDeploymentName() + "," + newDeploymentRequest.getCloudType() + ","+newDeploymentRequest.getUserId());
								
		if(userId != EntityConstants.INVALID_ID)
			return Response.status(200).entity("{\"string\":\"Deployment Details inserted succesfully!\"}").build();
		
		return Response.status(401).entity("{\"string\":\"Verification failed!\"}").build();
	}
	
	@GET
	@Produces("application/json")
	@Path("RDSDetails/{user_id}/{deploymentName}")
	public Response getAssociatedRDSInstances(@PathParam("user_id") int User_Id, @PathParam("deploymentName") String deploymentName) {
		ArrayList<UserAWSDBRequest> rdsInstanceList = UserAWSDBRequestDao.getUserRDSDetails(User_Id, deploymentName);
		JSONArray responseBody = new JSONArray();
		for(UserAWSDBRequest item: rdsInstanceList) {
			responseBody.put(JSONUtil.userRDSRequestDetailsToJSON(item));
		}
		return Response.ok(responseBody.toString()).build();
	}
	
	@GET
	@Produces("application/json")
	@Path("AWSEC2Details/{user_id}/{deploymentName}")
	public Response getAssociatedEC2Instances(@PathParam("user_id") int User_Id, @PathParam("deploymentName") String deploymentName) {
		ArrayList<UserAWSComputeResponse> ec2InstanceList = UserAWSComputeResponseDao.getUserEc2Details(User_Id, deploymentName);
		JSONArray responseBody = new JSONArray();
		for(UserAWSComputeResponse item: ec2InstanceList) {
			responseBody.put(JSONUtil.userEC2InstanceDetailsToJSON(item));
		}
		return Response.ok(responseBody.toString()).build();
	}
	
	@GET
	@Produces("application/json")
	@Path("S3Details/{user_id}/{deployment_name}")
	public Response getAssociatedS3Instances(@PathParam("user_id") int User_Id, @PathParam("deployment_name") String deploymentName) {
		ArrayList<UserAWSS3Request> S3BucketList = UserAWSS3RequestDao.getUserS3Details(User_Id, deploymentName);
		JSONArray responseBody = new JSONArray();
		for(UserAWSS3Request item: S3BucketList) {
			responseBody.put(JSONUtil.userS3RequestDetailsToJSON(item));
		}
		return Response.ok(responseBody.toString()).build();
	}
	
	@GET
	@Produces("application/json")
	@Path("Deployments/{user_id}")
	public Response getAssociatedDeploymentName(@PathParam("user_id") int User_Id) {
		ArrayList<UserAWSDeploymentRequest> deploymentList = UserDeploymentRequestDao.getUserDeploymentName(User_Id);
		JSONArray responseBody = new JSONArray();
		for(UserAWSDeploymentRequest item:deploymentList ) {
			responseBody.put(JSONUtil.userDeploymentNameToJSON(item));
		}
		return Response.ok(responseBody.toString()).build();
	}
	
//	@GET
//	@Produces("application/json")
//	@Path("GCPDBDetails/{user_id}/{deploymentName}")
//	public Response getAssociatedGCPDBInstances(@PathParam("user_id") int User_Id, @PathParam("deploymentName") String deploymentName) {
//		ArrayList<UserGCPDBRequest> gcpDBInstanceList = UserGCPDBRequestDao.getUserRDSDetails(User_Id, deploymentName);
//		JSONArray responseBody = new JSONArray();
//		for(UserAWSDBRequest item: gcpDBInstanceList) {
//			responseBody.put(JSONUtil.userGCPDBRequestDetailsToJSON(item));
//		}
//		return Response.ok(responseBody.toString()).build();
//	}
	
}

	