package rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import dao.UserAWSComputeLoadBalancerDetailsDao;
import dao.UserAWSComputeResponseDao;
import dao.UserAWSDBRequestDao;
import dao.UserAWSS3RequestDao;

@Path("AWS")
public class AWSResourcesDeleteService {
	@DELETE
	@Produces("application/json")
	@Path("delete/S3/{u_id}/{deploymentName}/{bucketName}")
	public Response delAWSs3(@PathParam("u_id") int userId, @PathParam("deploymentName") String deploymentName,
			@PathParam("bucketName") String bucketName) {
		boolean result = UserAWSS3RequestDao.delete(userId, deploymentName, bucketName);
		if (result)
			return Response.status(200).entity("{\"string\":\"Request Succeeded!\"}").build();
		else
			return Response.status(500).entity("{\"string\":\"Request Failed!\"}").build();
	}

	@DELETE
	@Produces("application/json")
	@Path("delete/RDS/{u_id}/{deploymentName}/{dbInstanceName}")
	public Response delAWSRDS(@PathParam("u_id") int userId, @PathParam("deploymentName") String deploymentName,
			@PathParam("dbInstanceName") String dbInstanceName) {
		boolean result = UserAWSDBRequestDao.delete(userId, deploymentName, dbInstanceName);
		if (result)
			return Response.status(200).entity("{\"string\":\"Request Succeeded!\"}").build();
		else
			return Response.status(500).entity("{\"string\":\"Request Failed!\"}").build();
	}

	@DELETE
	@Produces("application/json")
	@Path("delete/EC2/{u_id}/{instanceIP}")
	public Response delAWSEc2(@PathParam("u_id") int userId, @PathParam("instanceIP") String instanceIP) {
		boolean result = UserAWSComputeResponseDao.delete(userId, instanceIP);
		if (result)
			return Response.status(200).entity("{\"string\":\"Request Succeeded!\"}").build();
		else
			return Response.status(500).entity("{\"string\":\"Request Failed!\"}").build();
	}

	@DELETE
	@Produces("application/json")
	@Path("delete/LB/{u_id}/{deploymentName}/{elbIdentifier}")
	public Response delAWSLoadBalancer(@PathParam("u_id") int userId,
			@PathParam("elbIdentifier") String elbIdentifier) {
		boolean result = UserAWSComputeLoadBalancerDetailsDao.delete(userId, elbIdentifier);
		if (result)
			return Response.status(200).entity("{\"string\":\"Request Succeeded!\"}").build();
		else
			return Response.status(500).entity("{\"string\":\"Request Failed!\"}").build();
	}

}
