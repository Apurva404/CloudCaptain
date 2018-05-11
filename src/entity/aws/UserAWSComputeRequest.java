package entity.aws;

import java.util.Date;

import constants.EntityConstants;

public class UserAWSComputeRequest {
	private int requestId;
	private int userId;
	private Date  requestTime;
	private String imageType;
	private String instanceType;
	private int noOfInstance;
	private String LoadBalancer;
	private String S3Link;
	private String deploymentName;
	
	public UserAWSComputeRequest(int userIdIn,Date requestTimeIn, String imageTypeIn, String instanceTypeIn,int noOfInstanceIn, String loadBalancerIn, String s3LinkIn, String deploymentNameIn){
		requestId = EntityConstants.INVALID_ID;
		userId = userIdIn;
		requestTime = requestTimeIn;
		imageType = imageTypeIn;
		instanceType = instanceTypeIn;
		noOfInstance = noOfInstanceIn;
		LoadBalancer = loadBalancerIn;
		S3Link = s3LinkIn;	
		deploymentName = deploymentNameIn;
	}
	public UserAWSComputeRequest(int userIdIn,int requestIdIn,Date requestTimeIn, String imageTypeIn, String instanceTypeIn,int noOfInstanceIn, String loadBalancerIn, String s3LinkIn, String deploymentNameIn){
		requestId = requestIdIn;
		userId = userIdIn;
		requestTime = requestTimeIn;
		imageType = imageTypeIn;
		instanceType = instanceTypeIn;
		noOfInstance = noOfInstanceIn;
		LoadBalancer = loadBalancerIn;
		S3Link = s3LinkIn;	
		deploymentName = deploymentNameIn;
	}
	public int getRequestId() { return requestId; }
	public int getUserId() { return userId; }
	public Date getRequestTime() { return requestTime; }
	public String getImageType() { return imageType; }
	public String getInstanceType() { return instanceType; }
	public int getNoOfInstance() { return noOfInstance; }
	public String getLoadBalancer() { return LoadBalancer; }
	public String getS3Link() { return S3Link; }
	public String getDeploymentName() { return deploymentName; }

}
