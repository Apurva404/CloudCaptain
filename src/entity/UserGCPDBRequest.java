package entity;

import java.util.Date;

import constants.EntityConstants;

public class UserGCPDBRequest {
	private int requestId;
	private int userId;
	private Date  requestTime;
	private String dbType;
	private String dbInstanceName;
	private String masterUsername;
	private String masterPassword;
	private String deploymentName;
	
	public UserGCPDBRequest(int userIdIn,Date requestTimeIn,String dbTypeIn, String dbInstanceNameIn, String masterUsernameIn, String masterPasswordIn, String deploymentNameIn){
		requestId = EntityConstants.INVALID_ID;
		userId = userIdIn;
		requestTime = requestTimeIn;
		dbType = dbTypeIn;
		dbInstanceName = dbInstanceNameIn;
		masterUsername = masterUsernameIn;
		masterPassword = masterUsernameIn;	
		deploymentName = deploymentNameIn;
	}
	
	public UserGCPDBRequest(int requestIdIn,int userIdIn,Date requestTimeIn,String dbTypeIn, String dbInstanceNameIn, String masterUsernameIn, String masterPasswordIn, String deploymentNameIn){
		requestId = requestIdIn;
		userId = userIdIn;
		requestTime = requestTimeIn;
		dbType = dbTypeIn;
		dbInstanceName = dbInstanceNameIn;
		masterUsername = masterUsernameIn;
		masterPassword = masterUsernameIn;	
		deploymentName = deploymentNameIn;
	}

	public int getRequestId() { return requestId; }
	public int getUserId() { return userId; }
	public Date getRequestTime() { return requestTime; }
	public String getDBInstanceName() { return dbInstanceName; }
	public String getDBType() { return dbType; }
	public String getMasterUsername() { return masterUsername; }
	public String getMasterPassword() { return masterPassword; }
	public String getDeploymentName() { return deploymentName; }
}

