package entity.aws;

import constants.EntityConstants;

public class UserAWSComputeResponse {
	private int responseId;
	private int requestId;
	private int userId;
	private String instanceDNS;
	private String instanceState;
	private String instanceLaunchTime;
	private String instanceIP;
	private UserAWSComputeLoadBalancerDetails loadbalancer;
	
	public UserAWSComputeResponse(int requestIdIn, int userIdIn,String instanceDNSIn, String instanceStateIn,String instancelaunchTimeIn, String instanceIPIn){
		responseId = EntityConstants.INVALID_ID;
		requestId = requestIdIn;
		userId = userIdIn;
		instanceDNS = instanceDNSIn;
		instanceState = instanceStateIn;
		instanceLaunchTime = instancelaunchTimeIn;
		instanceIP = instanceIPIn;
		loadbalancer = null;
	}
	
	public UserAWSComputeResponse(int requestIdIn, int userIdIn,String instanceDNSIn, String instanceStateIn,String instancelaunchTimeIn, String instanceIPIn,UserAWSComputeLoadBalancerDetails lbIn ){
		responseId = EntityConstants.INVALID_ID;
		requestId = requestIdIn;
		userId = userIdIn;
		instanceDNS = instanceDNSIn;
		instanceState = instanceStateIn;
		instanceLaunchTime = instancelaunchTimeIn;
		instanceIP = instanceIPIn;
		loadbalancer = lbIn;
	}
	
	public int getResponseId() { return responseId; }
	public int getRequestId() { return requestId; }
	public int getUserId() { return userId; }
	public String getInstanceDNS() { return instanceDNS; }
	public String getInstanceState() { return instanceState; }
	public String getInstanceLaunchTime() { return instanceLaunchTime; }
	public String getInstanceIP() { return instanceIP; }
	public UserAWSComputeLoadBalancerDetails getLoadbalancer() { return loadbalancer; }
	public void setLoadbalancer(UserAWSComputeLoadBalancerDetails lb) { loadbalancer=lb; }
} 