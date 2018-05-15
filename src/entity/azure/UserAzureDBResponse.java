package entity.azure;

import constants.EntityConstants;

public class UserAzureDBResponse {
	private int responseId;
	private int requestId;
	private int userId;
	private String instanceEndpoint;
	
	public UserAzureDBResponse(int requestIdIn, int userIdIn, String instanceEndpointIn){
		responseId = EntityConstants.INVALID_ID;
		userId = userIdIn;
		requestId = requestIdIn;
		instanceEndpoint = instanceEndpointIn;
	}
	public UserAzureDBResponse(int responseIdIn,int requestIdIn, int userIdIn, String instanceEndpointIn){
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
