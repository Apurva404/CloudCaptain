package entity.gcp;

import java.util.Date;

import constants.EntityConstants;

public class UserGCPComputeRequest {
	private int requestId;
	private int userId;
	private Date  requestTime;
	private String instanceName;
	private String instanceType;
	private String warFileLink;
	private String deploymentName;
	
	public UserGCPComputeRequest(int userIdIn,Date requestTimeIn, String instanceNameIn, String instanceTypeIn, String warFileLinkIn, String deploymentNameIn){
		requestId = EntityConstants.INVALID_ID;
		userId = userIdIn;
		requestTime = requestTimeIn;
		instanceName = instanceNameIn;
		instanceType = instanceTypeIn;
		warFileLink = warFileLinkIn;	
		deploymentName = deploymentNameIn;
	}
	public UserGCPComputeRequest(int requestIdIn,int userIdIn,Date requestTimeIn, String instanceNameIn, String instanceTypeIn, String warFileLinkIn, String deploymentNameIn){
		requestId = requestIdIn;
		userId = userIdIn;
		requestTime = requestTimeIn;
		instanceName = instanceNameIn;
		instanceType = instanceTypeIn;
		warFileLink = warFileLinkIn;
		deploymentName = deploymentNameIn;
	}
	public int getRequestId() { return requestId; }
	public int getUserId() { return userId; }
	public Date getRequestTime() { return requestTime; }
	public String getInstanceName() { return instanceName; }
	public String getInstanceType() { return instanceType; }
	public String getWarFileLink() { return warFileLink; }
	public String getwarFileLink() { return warFileLink; }
	public String getDeploymentName() { return deploymentName; }
}
