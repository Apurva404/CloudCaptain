package entity.aws;

import constants.EntityConstants;

public class UserAWSComputeLoadBalancerDetails {
	private String elbIdentifier;
	private String elbEndpoint;
	private int requestId;
	
	public UserAWSComputeLoadBalancerDetails(String elbIdentifierIn, String elbEndpointIn, int requestIdIn){
		elbIdentifier = elbIdentifierIn;
		elbEndpoint = elbEndpointIn;
		requestId = requestIdIn;
	}
	
	public UserAWSComputeLoadBalancerDetails(String elbIdentifierIn, String elbEndpointIn){
		elbIdentifier = elbIdentifierIn;
		elbEndpoint = elbEndpointIn;
		requestId =EntityConstants.INVALID_ID;
	}
	public String getElbIdentifier() { return elbIdentifier; }
	public String getElbEndpoint() { return elbEndpoint; }
	public int getRequestId() { return requestId; }
}
