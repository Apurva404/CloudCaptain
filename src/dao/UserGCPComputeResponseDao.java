package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import constants.EntityConstants;
import db.DBManager;
import entity.gcp.UserGCPComputeResponse;
import table.UserGCPComputeRequestTable;
import table.UserGCPComputeResponseTable;

public class UserGCPComputeResponseDao {
	public static int insert( UserGCPComputeResponse newComputeResponse) {
		int lastInsertedResponseId = EntityConstants.INVALID_ID;
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					int rowsUpdated = statement.executeUpdate(generateSQLForInsert(newComputeResponse));
					if(rowsUpdated > 0) {
						ResultSet rs = statement.executeQuery("SELECT MAX(" + UserGCPComputeResponseTable.REQUEST_ID.getColumnName() + ") FROM " + UserGCPComputeResponseTable.getTableName() + ";");
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

	private static String generateSQLForInsert(UserGCPComputeResponse newComputeResponse) {
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(UserGCPComputeResponseTable.getTableName());
		sql.append(" (");
		sql.append(UserGCPComputeResponseTable.REQUEST_ID.getColumnName() + ",");
		sql.append(UserGCPComputeResponseTable.USER_ID.getColumnName() + ",");
		sql.append(UserGCPComputeResponseTable.INSTANCE_IP.getColumnName());
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
	
	public static ArrayList<UserGCPComputeResponse> getUserGCPInstnaceDetails(int user_id, String deploymentNameIn) {
		ArrayList<UserGCPComputeResponse> gcpComputeResponseFromDBList = new ArrayList<UserGCPComputeResponse>();
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					ResultSet rs = statement.executeQuery(generateSQLForSelectUserGCPDetails(user_id, deploymentNameIn));
					while(rs.next()) {
						int userId = rs.getInt(UserGCPComputeResponseTable.USER_ID.getColumnName());
						int requestId = rs.getInt(UserGCPComputeResponseTable.REQUEST_ID.getColumnName());
						String instanceIP = rs.getString(UserGCPComputeResponseTable.INSTANCE_IP.getColumnName());
						UserGCPComputeResponse item = new  UserGCPComputeResponse(requestId,userId,instanceIP);
						gcpComputeResponseFromDBList.add(item);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					manager.cleanupStatement(statement);
				}
			}
		}
		return gcpComputeResponseFromDBList;
	}

	private static String generateSQLForSelectUserGCPDetails(int userId, String deploymentName) {
		StringBuilder sql = new StringBuilder("SELECT * FROM ");
		sql.append(UserGCPComputeResponseTable.getTableName() + " INNER JOIN " + UserGCPComputeRequestTable.getTableName());
		sql.append(" ON " + UserGCPComputeResponseTable.getTableName()+ "." + UserGCPComputeResponseTable.REQUEST_ID.getColumnName() + " = " 
		+ UserGCPComputeRequestTable.getTableName()+ "."  + UserGCPComputeRequestTable.REQUEST_ID.getColumnName());
		sql.append(" WHERE ");
		sql.append(UserGCPComputeResponseTable.getTableName()+ "." + UserGCPComputeResponseTable.USER_ID.getColumnName());
		sql.append("=" + userId);	
		sql.append(" AND ");
		sql.append(UserGCPComputeRequestTable.getTableName()+ "." + UserGCPComputeRequestTable.DEPLOYMENT_ID.getColumnName());
		sql.append(" = " + "'" + deploymentName + "'" + ";");
		System.out.println(sql.toString());
		return sql.toString();
	}
}
