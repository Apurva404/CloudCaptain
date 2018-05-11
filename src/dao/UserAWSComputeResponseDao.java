package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import constants.EntityConstants;
import db.DBManager;
import entity.aws.UserAWSComputeLoadBalancerDetails;
import entity.aws.UserAWSComputeResponse;
import table.UserAWSComputeLoadBalancerDetailsTable;
import table.UserAWSComputeRequestTable;
import table.UserAWSComputeResponseTable;


public class UserAWSComputeResponseDao {
	public static int insert( UserAWSComputeResponse newComputeResponse) {
		int lastInsertedResponseId = EntityConstants.INVALID_ID;
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					int rowsUpdated = statement.executeUpdate(generateSQLForInsert(newComputeResponse));
					if(rowsUpdated > 0) {
						ResultSet rs = statement.executeQuery("SELECT MAX(" + UserAWSComputeResponseTable.RESPONSE_ID.getColumnName() + ") FROM " + UserAWSComputeResponseTable.getTableName() + ";");
						while(rs.next()) {
							lastInsertedResponseId = rs.getInt(1);							
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					manager.cleanupStatement(statement);
				}
			}
		}
		return lastInsertedResponseId;
	}
	

	private static String generateSQLForInsert(UserAWSComputeResponse newComputeResponse) {
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(UserAWSComputeResponseTable.getTableName());
		sql.append(" (");
		sql.append(UserAWSComputeResponseTable.REQUEST_ID.getColumnName() + ",");
		sql.append(UserAWSComputeResponseTable.USER_ID.getColumnName() + ",");
		sql.append(UserAWSComputeResponseTable.INSTANCE_DNS.getColumnName() + ",");
		sql.append(UserAWSComputeResponseTable.INSTANCE_STATE.getColumnName() + ",");
		sql.append(UserAWSComputeResponseTable.INSTANCE_LAUNCH_TIME.getColumnName() + ",");
		sql.append(UserAWSComputeResponseTable.INSTANCE_IP.getColumnName());
		sql.append(") values (");
		sql.append("'" + newComputeResponse.getRequestId() + "'");
		sql.append(",");
		sql.append("'" + newComputeResponse.getUserId() + "'");
		sql.append(",");
		sql.append("'" + newComputeResponse.getInstanceDNS() + "'");
		sql.append(",");
		sql.append("'" + newComputeResponse.getInstanceState()+ "'");
		sql.append(",");
		sql.append("'" + newComputeResponse.getInstanceLaunchTime() + "'" ); 
		sql.append(",");
		sql.append("'" + newComputeResponse.getInstanceIP() + "'");
		sql.append(");");
		
		System.out.println(sql.toString());
		return sql.toString();
	}
	
	public static ArrayList<UserAWSComputeResponse> getUserEc2Details(int user_id, String deploymentNameIn) {
		ArrayList<UserAWSComputeResponse> ec2ResponseFromDBList = new ArrayList<UserAWSComputeResponse>();
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					ResultSet rs = statement.executeQuery(generateSQLForSelectUserEC2Details(user_id, deploymentNameIn));
					while(rs.next()) {
						int userId = rs.getInt(UserAWSComputeResponseTable.USER_ID.getColumnName());
						int requestId = rs.getInt(UserAWSComputeResponseTable.REQUEST_ID.getColumnName());
						String instanceDNS =rs.getString(UserAWSComputeResponseTable.INSTANCE_DNS.getColumnName());
						String instanceState = rs.getString(UserAWSComputeResponseTable.INSTANCE_STATE.getColumnName());
						String instanceLaunchTime = rs.getString(UserAWSComputeResponseTable.INSTANCE_LAUNCH_TIME.getColumnName());
						String instanceIP = rs.getString(UserAWSComputeResponseTable.INSTANCE_IP.getColumnName());
						String elbIdentifier = rs.getString(UserAWSComputeLoadBalancerDetailsTable.ELB_IDENTIFIER.getColumnName());
						String elbEndpoint = rs.getString(UserAWSComputeLoadBalancerDetailsTable.ELB_ENDPOINT.getColumnName());
						UserAWSComputeLoadBalancerDetails lbItem = new UserAWSComputeLoadBalancerDetails(elbIdentifier,elbEndpoint,requestId);
						UserAWSComputeResponse item = new  UserAWSComputeResponse(requestId,userId,instanceDNS,instanceState,instanceLaunchTime,instanceIP, lbItem);
						ec2ResponseFromDBList.add(item);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					manager.cleanupStatement(statement);
				}
			}
		}
		return ec2ResponseFromDBList;
	}

	private static String generateSQLForSelectUserEC2Details(int userId, String deploymentName) {
		StringBuilder sql = new StringBuilder("SELECT A.UserId,A.RequestId,A.InstanceDNS,A.InstanceState, A.instanceLaunchTime, A.InstanceIP,B.ELBEndpoint,B.ELBIdentifier FROM ");
		sql.append(UserAWSComputeResponseTable.getTableName() + " A LEFT JOIN ");
		sql.append(UserAWSComputeLoadBalancerDetailsTable.getTableName() + " B ON A.RequestId = B.RequestId");
		sql.append(" WHERE  A.RequestId IN");
		sql.append("(SELECT ");
		sql.append(UserAWSComputeRequestTable.getTableName()+ "."+ UserAWSComputeRequestTable.REQUEST_ID.getColumnName());
		sql.append(" FROM ");
		sql.append(UserAWSComputeRequestTable.getTableName()+ " WHERE " + UserAWSComputeRequestTable.getTableName()+ "."+ UserAWSComputeRequestTable.USER_ID.getColumnName());
		sql.append(" = " + userId);
		sql.append(" AND " + UserAWSComputeRequestTable.getTableName()+ "."+ UserAWSComputeRequestTable.DEPLOYMENT_ID.getColumnName() + " = " + "'" + deploymentName + "'" + ")" + ";");

		System.out.println(sql.toString());
		return sql.toString();
	}	
}
