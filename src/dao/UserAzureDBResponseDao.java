package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import constants.EntityConstants;
import db.DBManager;
import entity.azure.UserAzureDBResponse;
import table.UserAzureDBRequestTable;
import table.UserAzureDBResponseTable;

public class UserAzureDBResponseDao {
	public static int insert( UserAzureDBResponse newDBResponse) {
		int lastInsertedResponseId = EntityConstants.INVALID_ID;
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					int rowsUpdated = statement.executeUpdate(generateSQLForInsert(newDBResponse));
					if(rowsUpdated > 0) {
						ResultSet rs = statement.executeQuery("SELECT MAX(" + UserAzureDBResponseTable.RESPONSE_ID.getColumnName() + ") FROM " + UserAzureDBResponseTable.getTableName() + ";");
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
	

	private static String generateSQLForInsert(UserAzureDBResponse newDBResponse) {
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(UserAzureDBResponseTable.getTableName());
		sql.append(" (");
		sql.append(UserAzureDBResponseTable.REQUEST_ID.getColumnName() + ",");
		sql.append(UserAzureDBResponseTable.USER_ID.getColumnName() + ",");
		sql.append(UserAzureDBResponseTable.INSTANCE_ENDPOINT.getColumnName());
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
	
	public static ArrayList<UserAzureDBResponse> getUserAzureDBDetails(int user_id, String deploymentNameIn) {
		ArrayList<UserAzureDBResponse> azureDBResponseFromDBList = new ArrayList<UserAzureDBResponse>();
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					ResultSet rs = statement.executeQuery(generateSQLForSelectUserAzureDetails(user_id, deploymentNameIn));
					while(rs.next()) {
						int userId = rs.getInt(UserAzureDBResponseTable.USER_ID.getColumnName());
						int requestId = rs.getInt(UserAzureDBResponseTable.REQUEST_ID.getColumnName());
						String instanceIP = rs.getString(UserAzureDBResponseTable.INSTANCE_ENDPOINT.getColumnName());
						UserAzureDBResponse item = new  UserAzureDBResponse(requestId,userId,instanceIP);
						azureDBResponseFromDBList.add(item);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					manager.cleanupStatement(statement);
				}
			}
		}
		return azureDBResponseFromDBList;
	}

	private static String generateSQLForSelectUserAzureDetails(int userId, String deploymentName) {
		StringBuilder sql = new StringBuilder("SELECT * FROM ");
		sql.append(UserAzureDBResponseTable.getTableName() + " INNER JOIN " + UserAzureDBRequestTable.getTableName());
		sql.append(" ON " + UserAzureDBResponseTable.getTableName()+ "." + UserAzureDBResponseTable.REQUEST_ID.getColumnName() + " = " 
		+ UserAzureDBRequestTable.getTableName()+ "."  + UserAzureDBRequestTable.REQUEST_ID.getColumnName());
		sql.append(" WHERE ");
		sql.append(UserAzureDBResponseTable.getTableName()+ "." + UserAzureDBResponseTable.USER_ID.getColumnName());
		sql.append("=" + userId);	
		sql.append(" AND ");
		sql.append(UserAzureDBRequestTable.getTableName()+ "." + UserAzureDBRequestTable.DEPLOYEMENT_ID.getColumnName());
		sql.append(" = " + "'" + deploymentName + "'" + ";");
		System.out.println(sql.toString());
		return sql.toString();
	}
	
	public static boolean delete( int userId, String instanceEndpoint) {
		boolean result = false;
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					int rowsUpdated = statement.executeUpdate(generateSQLForDeleteAzureDb(userId, instanceEndpoint));
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
	
	private static String generateSQLForDeleteAzureDb(int userId, String instanceEndpoint) {
		StringBuilder sql = new StringBuilder("DELETE FROM ");
		sql.append(UserAzureDBResponseTable.getTableName());
		sql.append(" WHERE ");
		sql.append(UserAzureDBResponseTable.USER_ID.getColumnName());
		sql.append("=" + userId);	
		sql.append(" AND ");
		sql.append(UserAzureDBResponseTable.INSTANCE_ENDPOINT.getColumnName());
		sql.append(" = " + "'" + instanceEndpoint + "'");
		System.out.println(sql.toString());
		return sql.toString();
	}
}
