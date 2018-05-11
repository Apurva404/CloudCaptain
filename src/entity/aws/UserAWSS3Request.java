package entity.aws;
import constants.EntityConstants;

public class UserAWSS3Request {
	private int requestId;
	private String bucketName;
	private int userId;
	private String deploymentName;
	
	public UserAWSS3Request( String bucketNameIn, int userIdIn,String deploymentNameIn){
		requestId = EntityConstants.INVALID_ID;
		bucketName = bucketNameIn;
		userId = userIdIn;
		deploymentName = deploymentNameIn;
	}
	
	public UserAWSS3Request( String bucketNameIn, int userIdIn, int requestIdIn, String deploymentNameIn){
		requestId = requestIdIn;
		bucketName = bucketNameIn;
		userId = userIdIn;
		deploymentName = deploymentNameIn;
		
	}
	public int getRequestId() { return requestId; }
	public String getBucketName() { return bucketName; }
	public int getUserId(){return userId; }
	public String getDeploymentName(){return deploymentName; }
}
