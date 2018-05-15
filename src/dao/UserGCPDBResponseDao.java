package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import constants.EntityConstants;
import db.DBManager;
import entity.gcp.UserGCPDBResponse;
import table.UserGCPDBRequestTable;
import table.UserGCPDBResponseTable;

public class UserGCPDBResponseDao {
	public static int insert( UserGCPDBResponse newDBResponse) {
		int lastInsertedResponseId = EntityConstants.INVALID_ID;
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					int rowsUpdated = statement.executeUpdate(generateSQLForInsert(newDBResponse));
					if(rowsUpdated > 0) {
						ResultSet rs = statement.executeQuery("SELECT MAX(" + UserGCPDBResponseTable.RESPONSE_ID.getColumnName() + ") FROM " + UserGCPDBResponseTable.getTableName() + ";");
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
	

	private static String generateSQLForInsert(UserGCPDBResponse newDBResponse) {
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(UserGCPDBResponseTable.getTableName());
		sql.append(" (");
		sql.append(UserGCPDBResponseTable.REQUEST_ID.getColumnName() + ",");
		sql.append(UserGCPDBResponseTable.USER_ID.getColumnName() + ",");
		sql.append(UserGCPDBResponseTable.INSTANCE_ENDPOINT.getColumnName());
		sql.append(") values (");
		sql.append("'" + newDBResponse.getRequestId() + "'");
		sql.append(",");
		sql.append("'" + newDBResponse.getUserId() + "'");
		sql.append(",");
		sql.append("'" + newDBResponse.getInstanceEndpoint() + "'");
		sql.append(");");
		
		System.out.println(sql.toString());
		return sql.toString();
	}
	
	public static ArrayList<UserGCPDBResponse> getUserGcpDBDetails(int user_id, String deploymentNameIn) {
		ArrayList<UserGCPDBResponse> gcpDBResponseFromDBList = new ArrayList<UserGCPDBResponse>();
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					ResultSet rs = statement.executeQuery(generateSQLForSelectUserGCPDetails(user_id, deploymentNameIn));
					while(rs.next()) {
						int userId = rs.getInt(UserGCPDBResponseTable.USER_ID.getColumnName());
						int requestId = rs.getInt(UserGCPDBResponseTable.REQUEST_ID.getColumnName());
						String instanceIP = rs.getString(UserGCPDBResponseTable.INSTANCE_ENDPOINT.getColumnName());
						UserGCPDBResponse item = new  UserGCPDBResponse(requestId,userId,instanceIP);
						gcpDBResponseFromDBList.add(item);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					manager.cleanupStatement(statement);
				}
			}
		}
		return gcpDBResponseFromDBList;
	}

	private static String generateSQLForSelectUserGCPDetails(int userId, String deploymentName) {
		StringBuilder sql = new StringBuilder("SELECT * FROM ");
		sql.append(UserGCPDBResponseTable.getTableName() + " INNER JOIN " + UserGCPDBRequestTable.getTableName());
		sql.append(" ON " + UserGCPDBResponseTable.getTableName()+ "." + UserGCPDBResponseTable.REQUEST_ID.getColumnName() + " = " 
		+ UserGCPDBRequestTable.getTableName()+ "."  + UserGCPDBRequestTable.REQUEST_ID.getColumnName());
		sql.append(" WHERE ");
		sql.append(UserGCPDBResponseTable.getTableName()+ "." + UserGCPDBResponseTable.USER_ID.getColumnName());
		sql.append("=" + userId);	
		sql.append(" AND ");
		sql.append(UserGCPDBRequestTable.getTableName()+ "." + UserGCPDBRequestTable.DEPLOYEMENT_ID.getColumnName());
		sql.append(" = " + "'" + deploymentName + "'" + ";");
		System.out.println(sql.toString());
		return sql.toString();
	}
}
