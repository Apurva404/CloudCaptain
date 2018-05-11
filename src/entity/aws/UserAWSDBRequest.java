package entity.aws;

import java.util.Date;

import constants.EntityConstants;

public class UserAWSDBRequest {
	private int requestId;
	private int userId;
	private Date  requestTime;
	private String DBInstanceName;
	private String DBType;
	private String masterUsername;
	private String masterPassword;
	private String deploymentName;
	
	public UserAWSDBRequest(int userIdIn,Date requestTimeIn, String DBInstanceNameIn, String DBTypeIn, String masterUsernameIn, String masterPasswordIn, String deploymentNameIn){
		requestId = EntityConstants.INVALID_ID;
		userId = userIdIn;
		requestTime = requestTimeIn;
		DBInstanceName = DBInstanceNameIn;
		DBType = DBTypeIn;
		masterUsername = masterUsernameIn;
		masterPassword = masterPasswordIn;
		deploymentName = deploymentNameIn;	
	}
	
	public UserAWSDBRequest(int requestIdIn,int userIdIn,Date requestTimeIn, String DBInstanceNameIn, String DBTypeIn, String masterUsernameIn, String masterPasswordIn, String deploymentNameIn){
		requestId = requestIdIn;
		userId = userIdIn;
		requestTime = requestTimeIn;
		DBInstanceName = DBInstanceNameIn;
		DBType = DBTypeIn;
		masterUsername = masterUsernameIn;
		masterPassword = masterPasswordIn;
		deploymentName = deploymentNameIn;	
	}
	
	public int getRequestId() { return requestId; }
	public int getUserId() { return userId; }
	public Date getRequestTime() { return requestTime; }
	public String getDBInstanceName() { return DBInstanceName; }
	public String getDBType() { return DBType; }
	public String getMasterUsername() { return masterUsername; }
	public String getMasterPassword() { return masterPassword; }
	public String getDeploymentName() { return deploymentName; }
}
