  package rest;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;

import constants.EntityConstants;
import dao.UserGCPComputeRequestDao;
import dao.UserGCPDBRequestDao;
import entity.UserGCPComputeRequest;
import entity.UserGCPComputeResponse;
import entity.UserGCPDBRequest;
import entity.UserGCPDBResponse;
import util.JSONUtil;

@Path("deployGCP")
public class GCPDeploymentService {
	@POST
	@Consumes("application/json")
	@Path("/Compute")
	@Produces("application/json")

	public Response createCompute(final String msg) throws Exception {
		System.out.println("POST APIs is called");
		int requestId, userId;
		String requestDateTimeString;
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date requestDate = new Date(0);
		String cloudType, instanceName, instanceType, warFileLink, deploymentName;
		requestId = EntityConstants.INVALID_ID;
		cloudType = instanceName = instanceType  = warFileLink = deploymentName ="";
		JSONObject json;
		JSONObject responseBody = new JSONObject();
		UserGCPComputeResponse newComputeResponse = null;

		try {
			System.out.println(msg);
			;
			json = new JSONObject(msg);
			userId = json.getInt("userId");
			requestDateTimeString = json.getString("DateTime");
			requestDate = sdf.parse(requestDateTimeString);
			cloudType = json.getString("CloudType");
			instanceName = json.getString("InstanceName");
			instanceType = json.getString("InstanceType");
			warFileLink = json.getString("warFileLink");
			deploymentName = json.getString("DeploymentName");

		} catch (JSONException e) {
			e.printStackTrace();
			// return Response.status(400).entity("{\"string\":\"JSON parsing
			// failed.\"}").build();
			return Response.status(400).entity("{\"string\":\"JSON parsing failed.\"}").build();
		}
		if (cloudType.equalsIgnoreCase("GCP")) {
			// Creating a new record in request for the user request of AWS
			// compute resource
			UserGCPComputeRequest newComputeRequest = new UserGCPComputeRequest(userId, requestDate, instanceName,
					instanceType,warFileLink,deploymentName);
			System.out.println(newComputeRequest.getRequestId() + "," + newComputeRequest.getUserId() + ","
					+ newComputeRequest.getRequestTime() + "," + newComputeRequest.getInstanceName() + ","
					+ newComputeRequest.getInstanceType()  + ","+  newComputeRequest.getWarFileLink() + ","+ newComputeRequest.getDeploymentName());
			// call GCPComputeRequestDao to insert a record of request in DB
			requestId = UserGCPComputeRequestDao.insert(newComputeRequest);
			// calling the GCP Compute creation api
			GCPComputeCreationRESTClient GCPComputeCreationClient = new GCPComputeCreationRESTClient();
			newComputeResponse = GCPComputeCreationClient.ClientGetCall(userId,instanceName, instanceType, warFileLink, requestId);
			if (newComputeResponse != null) 			
					responseBody=JSONUtil.GCPComputeResponseToJSON(newComputeResponse);
		}
		if (requestId != EntityConstants.INVALID_ID && newComputeResponse != null ) {
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
	@Path("/DB")
	@Produces("application/json")

	public Response createDB(final String msg) throws Exception {
		System.out.println("POST APIs is called");
		int requestId, userId;
		String requestDateTimeString;
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date requestDate = new Date(0);
		String dbType, dbInstanceName, masterUsername,masterPassword, deploymentName;
		requestId = EntityConstants.INVALID_ID;
		dbType = dbInstanceName = masterUsername  = masterPassword = deploymentName ="";
		JSONObject json;
		JSONObject responseBody = new JSONObject();
		UserGCPDBResponse newDBResponse = null;

		try {
			System.out.println(msg);
			;
			json = new JSONObject(msg);
			userId = json.getInt("userId");
			requestDateTimeString = json.getString("DateTime");
			requestDate = sdf.parse(requestDateTimeString);
			dbType = json.getString("DBType");
			dbInstanceName = json.getString("DBInstanceName");
			masterUsername = json.getString("MasterUsername");
			masterPassword = json.getString("MasterPassword");
			deploymentName = json.getString("DeploymentName");

		} catch (JSONException e) {
			e.printStackTrace();
			// return Response.status(400).entity("{\"string\":\"JSON parsing
			// failed.\"}").build();
			return Response.status(400).entity("{\"string\":\"JSON parsing failed.\"}").build();
		}
			// Creating a new record in request for the user request of AWS
			// compute resource
			UserGCPDBRequest newDBRequest = new UserGCPDBRequest(userId, requestDate, dbType,
					dbInstanceName,masterUsername, masterPassword,deploymentName);
			System.out.println(newDBRequest.getRequestId() + "," + newDBRequest.getUserId() + ","
					+ newDBRequest.getRequestTime() + "," + newDBRequest.getDBInstanceName() + ","
					+ newDBRequest.getMasterUsername()  + ","+  newDBRequest.getMasterPassword() + ","+ newDBRequest.getDeploymentName());
			// call GCPComputeRequestDao to insert a record of request in DB
			requestId = UserGCPDBRequestDao.insert(newDBRequest);
			// calling the GCP Compute creation api
			GCPDBCreationClient GCPDBCreationClient = new GCPDBCreationClient();
			newDBResponse = GCPDBCreationClient.ClientGetCall(userId,requestId, dbInstanceName, masterUsername, masterPassword, dbType);
			if (newDBResponse != null) 			
					responseBody=JSONUtil.GCPDBResponseToJSON(newDBResponse);
		if (requestId != EntityConstants.INVALID_ID && newDBResponse != null ) {
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
}
