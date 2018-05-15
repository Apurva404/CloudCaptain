package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

import entity.aws.UserAWSComputeLoadBalancerDetails;
import entity.aws.UserAWSComputeResponse;
import entity.aws.UserAWSDBRequest;
import entity.aws.UserAWSDeploymentRequest;
import entity.aws.UserAWSS3Request;
import entity.azure.UserAzureComputeResponse;
import entity.azure.UserAzureDBResponse;
import entity.gcp.UserGCPComputeResponse;
import entity.gcp.UserGCPDBResponse;

public class JSONUtil {
	public static JSONObject AWSComputeResponseToJSON(UserAWSComputeResponse awsComputeResponseInfo){	
		JSONObject json = new JSONObject();
		if (awsComputeResponseInfo != null) {
			json.accumulate("REQUEST_ID", awsComputeResponseInfo.getRequestId());
			json.accumulate("USER_ID", awsComputeResponseInfo.getUserId());
			json.accumulate("INSTANCE_DNS", awsComputeResponseInfo.getInstanceDNS());
			json.accumulate("INSTANCE_STATE", awsComputeResponseInfo.getInstanceState());
			json.accumulate("INSTANCE_LAUNCH_TIME", awsComputeResponseInfo.getInstanceLaunchTime());
			json.accumulate("INSTANCE_IP", awsComputeResponseInfo.getInstanceIP());
			json.accumulate("INSTANCE_ID", awsComputeResponseInfo.getInstanceID());
		}
		return json;
	}	
	public static JSONObject AzureComputeResponseToJSON(UserAzureComputeResponse azureComputeResponseInfo){	
		JSONObject json = new JSONObject();
		if (azureComputeResponseInfo != null) {
			json.accumulate("REQUEST_ID", azureComputeResponseInfo.getRequestId());
			json.accumulate("USER_ID", azureComputeResponseInfo.getUserId());
			json.accumulate("INSTANCE_IP", azureComputeResponseInfo.getInstanceEndpoint());
		}
		return json;
	}	
	public static JSONObject GCPComputeResponseToJSON(UserGCPComputeResponse gcpComputeResponseInfo){	
		JSONObject json = new JSONObject();
		if (gcpComputeResponseInfo != null) {
			json.accumulate("REQUEST_ID", gcpComputeResponseInfo.getRequestId());
			json.accumulate("USER_ID", gcpComputeResponseInfo.getUserId());
			json.accumulate("INSTANCE_IP", gcpComputeResponseInfo.getInstanceEndpoint());
		}
		return json;
	}
	
	public static JSONObject GCPDBResponseToJSON(UserGCPDBResponse gcpDBResponseInfo) {
		JSONObject json = new JSONObject();
		if (gcpDBResponseInfo != null) {
			json.accumulate("Request_Id", gcpDBResponseInfo.getRequestId());
			json.accumulate("User_Id", gcpDBResponseInfo.getUserId());
			json.accumulate("Database_IP", gcpDBResponseInfo.getInstanceEndpoint());
		}
		return json;
	}
	public static JSONObject AzureDBResponseToJSON(UserAzureDBResponse azureDBResponseInfo) {
		JSONObject json = new JSONObject();
		if (azureDBResponseInfo != null) {
			json.accumulate("Request_Id", azureDBResponseInfo.getRequestId());
			json.accumulate("User_Id", azureDBResponseInfo.getUserId());
			json.accumulate("Database_IP", azureDBResponseInfo.getInstanceEndpoint());
		}
		return json;
	}

	public static JSONObject AWSComputeLoadBalancerToJSON(UserAWSComputeLoadBalancerDetails awsComputeLoadBalancerInfo) {
		JSONObject json = new JSONObject();
		if (awsComputeLoadBalancerInfo != null) {
			json.accumulate("REQUEST_ID", awsComputeLoadBalancerInfo.getRequestId());
			json.accumulate("ELBIdentifier", awsComputeLoadBalancerInfo.getElbIdentifier());
			json.accumulate("ELBEndpoint", awsComputeLoadBalancerInfo.getElbEndpoint());
		}
		return json;
	}
	public static JSONObject userRDSRequestDetailsToJSON(UserAWSDBRequest item) {
		JSONObject json = new JSONObject();
		if (item != null) {
			json.accumulate("Request_Id", item.getRequestId());
			json.accumulate("User_Id", item.getUserId());
			json.accumulate("Request_Time", item.getRequestTime());
			json.accumulate("DBInstanceName", item.getDBInstanceName());
			json.accumulate("DbType", item.getDBType());
			json.accumulate("MasterUserName",item.getMasterUsername());
			json.accumulate("MasterPassword",item.getMasterPassword());	
			json.accumulate("DeploymentName",item.getDeploymentName());	
		}
		return json;
	}
	
	public static JSONObject userEC2InstanceDetailsToJSON(UserAWSComputeResponse awsComputeResponseInfo) {
		JSONObject json = new JSONObject();
		if (awsComputeResponseInfo != null) {
			json.accumulate("REQUEST_ID", awsComputeResponseInfo.getRequestId());
			json.accumulate("USER_ID", awsComputeResponseInfo.getUserId());
			json.accumulate("INSTANCE_DNS", awsComputeResponseInfo.getInstanceDNS());
			json.accumulate("INSTANCE_STATE", awsComputeResponseInfo.getInstanceState());
			json.accumulate("INSTANCE_LAUNCH_TIME", awsComputeResponseInfo.getInstanceLaunchTime());
			json.accumulate("INSTANCE_IP", awsComputeResponseInfo.getInstanceIP());
			json.accumulate("INSTANCE_ID", awsComputeResponseInfo.getInstanceID());
			json.accumulate("ELBIdentifier", awsComputeResponseInfo.getLoadbalancer().getElbIdentifier());
			json.accumulate("ELBEndpoint", awsComputeResponseInfo.getLoadbalancer().getElbEndpoint());
		}
		return json;
	}
	
	
	public static JSONObject userS3RequestDetailsToJSON(UserAWSS3Request item) {
		JSONObject json = new JSONObject();
		if (item != null) {
			json.accumulate("Request_Id", item.getRequestId());
			json.accumulate("User_Id", item.getUserId());
			json.accumulate("BucketName", item.getBucketName());	
			json.accumulate("DeploymentName", item.getDeploymentName());
		}
		return json;
	}
	
	public static JSONObject userDeploymentNameToJSON(UserAWSDeploymentRequest item) {
		JSONObject json = new JSONObject();
		if (item != null) {
			json.accumulate("DeploymentName", item.getDeploymentName());
			json.accumulate("CloudType", item.getCloudType());
			json.accumulate("User_Id", item.getUserId());	
		}
		return json;
	}

	public static JSONObject userGCPComputeDetailsToJSON(UserGCPComputeResponse gcpComputeResponseInfo) {
		JSONObject json = new JSONObject();
		if (gcpComputeResponseInfo != null) {
			json.accumulate("REQUEST_ID", gcpComputeResponseInfo.getRequestId());
			json.accumulate("USER_ID", gcpComputeResponseInfo.getUserId());
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			json.accumulate("INSTANCE_STATE", "Running");
			json.accumulate("INSTANCE_LAUNCH_TIME", dtf.format(now));
			json.accumulate("INSTANCE_IP", gcpComputeResponseInfo.getInstanceEndpoint());
		}
		return json;
	}
	
	public static JSONObject userGCPDBRequestDetailsToJSON(UserGCPDBResponse gcpDBResponseInfo) {
		JSONObject json = new JSONObject();
		if (gcpDBResponseInfo != null) {
			json.accumulate("REQUEST_ID", gcpDBResponseInfo.getRequestId());
			json.accumulate("USER_ID", gcpDBResponseInfo.getUserId());
			json.accumulate("DB_ENDPOINT", gcpDBResponseInfo.getInstanceEndpoint());
		}
		return json;
	} 
	
	public static JSONObject userAzureomputeDetailsToJSON(UserAzureComputeResponse azureComputeResponseInfo) {
		JSONObject json = new JSONObject();
		if (azureComputeResponseInfo != null) {
			json.accumulate("REQUEST_ID", azureComputeResponseInfo.getRequestId());
			json.accumulate("USER_ID", azureComputeResponseInfo.getUserId());
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			json.accumulate("INSTANCE_STATE", "Running");
			json.accumulate("INSTANCE_LAUNCH_TIME", dtf.format(now));
			json.accumulate("INSTANCE_IP", azureComputeResponseInfo.getInstanceEndpoint());
		}
		return json;
	}
	
	public static JSONObject userAzureDBRequestDetailsToJSON(UserAzureDBResponse azureDBResponseInfo) {
		JSONObject json = new JSONObject();
		if (azureDBResponseInfo != null) {
			json.accumulate("REQUEST_ID", azureDBResponseInfo.getRequestId());
			json.accumulate("USER_ID", azureDBResponseInfo.getUserId());
			json.accumulate("DB_ENDPOINT", azureDBResponseInfo.getInstanceEndpoint());
		}
		return json;
	} 
	
}

