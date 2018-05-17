package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import constants.EntityConstants;
import db.DBManager;
import entity.azure.UserAzureComputeResponse;
import table.UserAzureComputeRequestTable;
import table.UserAzureComputeResponseTable;

public class UserAzureComputeResponseDao {
	public static int insert( UserAzureComputeResponse newComputeResponse) {
		int lastInsertedResponseId = EntityConstants.INVALID_ID;
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					int rowsUpdated = statement.executeUpdate(generateSQLForInsert(newComputeResponse));
					if(rowsUpdated > 0) {
						ResultSet rs = statement.executeQuery("SELECT MAX(" + UserAzureComputeResponseTable.REQUEST_ID.getColumnName() + ") FROM " + UserAzureComputeResponseTable.getTableName() + ";");
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

	private static String generateSQLForInsert(UserAzureComputeResponse newComputeResponse) {
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(UserAzureComputeResponseTable.getTableName());
		sql.append(" (");
		sql.append(UserAzureComputeResponseTable.REQUEST_ID.getColumnName() + ",");
		sql.append(UserAzureComputeResponseTable.USER_ID.getColumnName() + ",");
		sql.append(UserAzureComputeResponseTable.INSTANCE_IP.getColumnName());
		sql.append(") values (");
		sql.append("'" + newComputeResponse.getRequestId() + "'");
		sql.append(",");
		sql.append("'" + newComputeResponse.getUserId() + "'");
		sql.append(",");
		sql.append("'" + newComputeResponse.getInstanceEndpoint() + "'");
		sql.append(");");
		
		System.out.println(sql.toString());
		return sql.toString();
	}
 
	public static ArrayList<UserAzureComputeResponse> getUserAzureInstnaceDetails(int user_id, String deploymentNameIn) {
		ArrayList<UserAzureComputeResponse> azureComputeResponseFromDBList = new ArrayList<UserAzureComputeResponse>();
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					ResultSet rs = statement.executeQuery(generateSQLForSelectUserAzureDetails(user_id, deploymentNameIn));
					while(rs.next()) {
						int userId = rs.getInt(UserAzureComputeResponseTable.USER_ID.getColumnName());
						int requestId = rs.getInt(UserAzureComputeResponseTable.REQUEST_ID.getColumnName());
						String instanceIP = rs.getString(UserAzureComputeResponseTable.INSTANCE_IP.getColumnName());
						UserAzureComputeResponse item = new  UserAzureComputeResponse(requestId,userId,instanceIP);
						azureComputeResponseFromDBList.add(item);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					manager.cleanupStatement(statement);
				}
			}
		}
		return azureComputeResponseFromDBList;
	}

	private static String generateSQLForSelectUserAzureDetails(int userId, String deploymentName) {
		StringBuilder sql = new StringBuilder("SELECT * FROM ");
		sql.append(UserAzureComputeResponseTable.getTableName() + " INNER JOIN " + UserAzureComputeRequestTable.getTableName());
		sql.append(" ON " + UserAzureComputeResponseTable.getTableName()+ "." + UserAzureComputeResponseTable.REQUEST_ID.getColumnName() + " = " 
		+ UserAzureComputeRequestTable.getTableName()+ "."  + UserAzureComputeRequestTable.REQUEST_ID.getColumnName());
		sql.append(" WHERE ");
		sql.append(UserAzureComputeResponseTable.getTableName()+ "." + UserAzureComputeResponseTable.USER_ID.getColumnName());
		sql.append("=" + userId);	
		sql.append(" AND ");
		sql.append(UserAzureComputeRequestTable.getTableName()+ "." + UserAzureComputeRequestTable.DEPLOYMENT_ID.getColumnName());
		sql.append(" = " + "'" + deploymentName + "'" + ";");
		System.out.println(sql.toString());
		return sql.toString();
	}
	
	public static boolean delete( int userId, String instanceIP) {
		boolean result = false;
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					int rowsUpdated = statement.executeUpdate(generateSQLForDeleteAzureCompute(userId, instanceIP));
					if(rowsUpdated > 0) {
						result = true;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					manager.cleanupStatement(statement);
				}
			}
		}
		return result;
	}
	
	private static String generateSQLForDeleteAzureCompute(int userId, String instanceIP) {
		StringBuilder sql = new StringBuilder("DELETE FROM ");
		sql.append(UserAzureComputeResponseTable.getTableName());
		sql.append(" WHERE ");
		sql.append(UserAzureComputeResponseTable.USER_ID.getColumnName());
		sql.append("=" + userId);	
		sql.append(" AND ");
		sql.append(UserAzureComputeResponseTable.INSTANCE_IP.getColumnName());
		sql.append(" = " + "'" + instanceIP + "'" + ";");
		System.out.println(sql.toString());
		return sql.toString();
	}

}
