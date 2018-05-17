package rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import dao.UserGCPComputeResponseDao;
import dao.UserGCPDBResponseDao;

@Path("GCP")
public class GCPResourceDeleteService {
	@DELETE
	@Produces("application/json")
	@Path("delete/DB/{u_id}/{instanceEndpoint}")
	public Response delGcpDB(@PathParam("u_id") int userId, @PathParam("instanceEndpoint") String instanceEndpoint) {
		boolean result = UserGCPDBResponseDao.delete(userId, instanceEndpoint);
		if (result)
			return Response.status(200).entity("{\"string\":\"Request Succeeded!\"}").build();
		else
			return Response.status(500).entity("{\"string\":\"Request Failed!\"}").build();
	}

	@DELETE
	@Produces("application/json")
	@Path("delete/Compute/{u_id}/{instanceIP}")
	public Response delGcpCompute(@PathParam("u_id") int userId, @PathParam("instanceIP") String instanceIP) {
		boolean result = UserGCPComputeResponseDao.delete(userId, instanceIP);
		if (result)
			return Response.status(200).entity("{\"string\":\"Request Succeeded!\"}").build();
		else
			return Response.status(500).entity("{\"string\":\"Request Failed!\"}").build();
	}

}
