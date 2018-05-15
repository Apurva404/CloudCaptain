package entity.azure;

import java.util.Date;

import constants.EntityConstants;

public class UserAzureComputeRequest {
	private int requestId;
	private int userId;
	private Date  requestTime;
	private String instanceName;
	private String instanceType;
	private String s3Link;
	private String deploymentName;
	private String imageType;
	
	public UserAzureComputeRequest(int userIdIn,Date requestTimeIn, String instanceNameIn
			 , String instanceTypeIn, String s3LinkIn, String deploymentNameIn,String imageTypeIn){
		requestId = EntityConstants.INVALID_ID;
		userId = userIdIn;
		requestTime = requestTimeIn;
		instanceName = instanceNameIn;
		imageType = imageTypeIn;
		instanceType = instanceTypeIn;
		s3Link = s3LinkIn;	
		deploymentName = deploymentNameIn;
	}
	public UserAzureComputeRequest(int requestIdIn,int userIdIn,Date requestTimeIn, String instanceNameIn,
			 String imageTypeIn, String instanceTypeIn, String s3LinkIn, String deploymentNameIn){
		requestId = requestIdIn;
		userId = userIdIn;
		requestTime = requestTimeIn;	
		instanceName = instanceNameIn;
		instanceType = instanceTypeIn;
		s3Link = s3LinkIn;
		deploymentName = deploymentNameIn;
		imageType = imageTypeIn;
	}
	public int getRequestId() { return requestId; }
	public int getUserId() { return userId; }
	public Date getRequestTime() { return requestTime; }
	public String getInstanceName() { return instanceName; }
	public String getInstanceType() { return instanceType; }
	public String getS3Link() { return s3Link; }
	public String getDeploymentName() { return deploymentName; }
	public String getImageType() { return imageType; }
}
