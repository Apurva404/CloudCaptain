package rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import constants.EntityConstants;
import dao.UserAWSComputeRequestDao;
import dao.UserAWSDBRequestDao;
import dao.UserAWSS3RequestDao;
import entity.aws.UserAWSComputeLoadBalancerDetails;
import entity.aws.UserAWSComputeRequest;
import entity.aws.UserAWSComputeResponse;
import entity.aws.UserAWSDBRequest;
import entity.aws.UserAWSS3Request;
import rest.AWSResourceCreationRESTClient;
import util.JSONUtil;

@Path("deploy")
public class AWSDeploymentService {
	@POST
	@Consumes("application/json")
	@Path("/awsRDS")
	@Produces("application/json")
	public Response createDB(final String msg) throws Exception {
		System.out.println("AWS RDS POST APIs is called");
		int requestId, userId;
		String requestDateTimeString;
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date requestDate = new Date(0);
		String cloudType, DBInstanceName, DBType, masterUsername, masterUserPassword,deploymentName;
		requestId = EntityConstants.INVALID_ID;
		cloudType = DBInstanceName = DBType = masterUsername = masterUserPassword = deploymentName = "";
		JSONObject json;

		try {
			System.out.println(msg);
			;
			json = new JSONObject(msg);
			userId = json.getInt("userId");
			requestDateTimeString = json.getString("DateTime");
			requestDate = sdf.parse(requestDateTimeString);
			cloudType = json.getString("CloudType");
			DBInstanceName = json.getString("DBInstanceName");
			DBType = json.getString("DBType");
			masterUsername = json.getString("MasterUsername");
			masterUserPassword = json.getString("MasterPassword");
			deploymentName = json.getString("DeploymentName");

		} catch (JSONException e) {
			e.printStackTrace();
			return Response.status(400).entity("{\"string\":\"JSON parsing failed.\"}").build();
		}
		if (cloudType.equalsIgnoreCase("AWS")) {
			UserAWSDBRequest newDBRequest = new UserAWSDBRequest(userId, requestDate, DBInstanceName, DBType,
					masterUsername, masterUserPassword,deploymentName);
			System.out.println(newDBRequest.getRequestId() + "," + newDBRequest.getUserId() + ","
					+ newDBRequest.getDBInstanceName() + "," + newDBRequest.getDBType() + ","
					+ newDBRequest.getMasterUsername() + "," + newDBRequest.getMasterPassword() + newDBRequest.getDeploymentName());

			requestId = UserAWSDBRequestDao.insert(newDBRequest);
			if (requestId != EntityConstants.INVALID_ID) {
				AWSRDSCreationRESTClient newDBClient = new AWSRDSCreationRESTClient();
				String result = newDBClient.ClientGetCall(DBType, DBInstanceName, masterUsername, masterUserPassword);
				if (result.equalsIgnoreCase("OK"))
					return Response.status(200)
							.entity("{\"string\":\"DB instance creation in process...Endpoint not available yet\"}")
							.build();
			}
		}
		// add some meaningful message
		return Response.status(500).entity("{\"string\":\"Request failed!\"}").build();
		// return Response.status(500).entity("{\"string\":\"Request
		// failed!\"}").header("Access-Control-Allow-Origin", "*")
		// .header("Access-Control-Allow-Methods","*")
		// .header("Access-Control-Allow-Headers","*").build();
	}

	@POST
	@Consumes("application/json")
	@Path("/awsCompute")
	@Produces("application/json")
	public Response createCompute(final String msg) throws Exception {
		System.out.println("POST APIs is called");
		int requestId, userId, noOfInstance;
		String requestDateTimeString;
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date requestDate = new Date(0);
		String cloudType, imageType, instanceType, LoadBalancer, S3Link, deploymentName;
		requestId = EntityConstants.INVALID_ID;
		cloudType = imageType = instanceType = LoadBalancer = S3Link = deploymentName ="";
		JSONObject json;
		JSONArray responseBody = new JSONArray();

		try {
			System.out.println(msg);
			;
			json = new JSONObject(msg);
			userId = json.getInt("userId");
			requestDateTimeString = json.getString("DateTime");
			requestDate = sdf.parse(requestDateTimeString);
			cloudType = json.getString("CloudType");
			imageType = json.getString("ImageType");
			instanceType = json.getString("InstanceType");
			noOfInstance = json.getInt("NoOfInstance");
			LoadBalancer = json.getString("LoadBalancer");
			S3Link = json.getString("S3Link");
			deploymentName = json.getString("DeploymentName");

		} catch (JSONException e) {
			e.printStackTrace();
			// return Response.status(400).entity("{\"string\":\"JSON parsing
			// failed.\"}").build();
			return Response.status(400).entity("{\"string\":\"JSON parsing failed.\"}").build();
		}
		if (cloudType.equalsIgnoreCase("AWS")) {
			// Creating a new record in request for the user request of AWS
			// compute resource
			UserAWSComputeRequest newComputeRequest = new UserAWSComputeRequest(userId, requestDate, imageType,
					instanceType, noOfInstance, LoadBalancer, S3Link,deploymentName);
			System.out.println(newComputeRequest.getRequestId() + "," + newComputeRequest.getUserId() + ","
					+ newComputeRequest.getRequestTime() + "," + newComputeRequest.getImageType() + ","
					+ newComputeRequest.getInstanceType() + "," + newComputeRequest.getNoOfInstance() + ","
					+ newComputeRequest.getLoadBalancer() + newComputeRequest.getS3Link() + ","
					+ newComputeRequest.getDeploymentName());
			// call AWSComputeRequestDao to insert a record of request in DB
			requestId = UserAWSComputeRequestDao.insert(newComputeRequest);
			// calling the EC2 creation api
			AWSResourceCreationRESTClient awsEC2CreationClient = new AWSResourceCreationRESTClient();
			ArrayList<UserAWSComputeResponse> newComputeResponseList = awsEC2CreationClient.ClientGetCall(userId,
					imageType, instanceType, noOfInstance, S3Link, LoadBalancer, requestId);

			UserAWSComputeLoadBalancerDetails loadBalancer = null;
			for (UserAWSComputeResponse newResponse : newComputeResponseList) {
				if ((loadBalancer == null) && (newResponse.getLoadbalancer() != null))
					loadBalancer = newResponse.getLoadbalancer();
				responseBody.put(JSONUtil.AWSComputeResponseToJSON(newResponse));
			}
			if (loadBalancer != null) {
				responseBody.put(JSONUtil.AWSComputeLoadBalancerToJSON(loadBalancer));
			}
		}
		if (requestId != EntityConstants.INVALID_ID) {
			return Response.ok(responseBody.toString()).build();
		} else {
			// add some meaningful message
			// return Response.status(500).entity("{\"string\":\"Request
			// failed!\"}").build();
			return Response.status(500).entity("{\"string\":\"Request failed!\"}")
					.header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "*")
					.header("Access-Control-Allow-Headers", "*").build();
		}
	}

	@POST
	@Consumes("application/json")
	@Path("/awsS3")
	@Produces("application/json")
	public Response createS3(final String msg) throws Exception {
		System.out.println("AWS S3 POST APIs is called");
		String s3bucketName = "", deploymentName = "";
		int requestId, userId;
		requestId = userId = EntityConstants.INVALID_ID;

		try {
			System.out.println(msg);
			JSONObject json = new JSONObject(msg);
			userId = json.getInt("userId");
			s3bucketName = json.getString("bucketName");
			deploymentName = json.getString("deploymentName");
		} catch (JSONException e) {
			e.printStackTrace();
			return Response.status(400).entity("{\"string\":\"JSON parsing failed.\"}").build();
		}

		UserAWSS3Request newS3Request = new UserAWSS3Request(s3bucketName, userId,deploymentName);
		System.out.println(
				newS3Request.getRequestId() + "," + newS3Request.getUserId() + "," + newS3Request.getBucketName() + newS3Request.getDeploymentName());
		requestId = UserAWSS3RequestDao.insert(newS3Request);

		if (requestId != EntityConstants.INVALID_ID) {
			AWSS3CreationRESTClient newS3Client = new AWSS3CreationRESTClient();
			String result = newS3Client.ClientGetCall(s3bucketName);
			if (result.equals("OK"))
				return Response.status(200).entity("{\"RequestId\":" + requestId + "}").build();
		}

		// add some meaningful message
		return Response.status(500).entity("{\"string\":\"Request failed!\"}").build();
	}
}
