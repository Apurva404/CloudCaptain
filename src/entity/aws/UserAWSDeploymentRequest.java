package entity.aws;

public class UserAWSDeploymentRequest {
	private String deploymentName;
	private String cloudType;
	private int userId;
	
	public UserAWSDeploymentRequest(String deploymentNameIn, String cloudTypeIn, int userIdIn){
		deploymentName = deploymentNameIn;
		cloudType = cloudTypeIn;
		userId = userIdIn;
	}
	public String getDeploymentName() { return deploymentName; }
	public String getCloudType() { return cloudType; }
	public int getUserId() { return userId; }
}
