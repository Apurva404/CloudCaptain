package entity.azure;

import constants.EntityConstants;

public class UserAzureComputeResponse {
	private int responseId;
	private int requestId;
	private int userId;
	private String instanceEndpoint;
	
	public UserAzureComputeResponse(int requestIdIn, int userIdIn, String instanceEndpointIn){
		responseId = EntityConstants.INVALID_ID;
		userId = userIdIn;
		requestId = requestIdIn;
		instanceEndpoint = instanceEndpointIn;
	}
	public UserAzureComputeResponse(int responseIdIn,int requestIdIn, int userIdIn, String instanceEndpointIn){
		responseId = responseIdIn;
		requestId = requestIdIn;
		userId = userIdIn;
		instanceEndpoint = instanceEndpointIn;
	}
	public int getResponseId() { return responseId; }
	public int getRequestId() { return requestId; }
	public int getUserId() { return userId; }
	public String getInstanceEndpoint() { return instanceEndpoint; }

}
