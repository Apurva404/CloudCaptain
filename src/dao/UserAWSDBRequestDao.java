package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import constants.EntityConstants;
import db.DBManager;
import entity.aws.UserAWSDBRequest;
import table.UserAWSDBRequestTable;
import table.UserAWSS3RequestTable;

public class UserAWSDBRequestDao {
	public static int insert( UserAWSDBRequest newDBRequest) {
		int lastInsertedRequestId = EntityConstants.INVALID_ID;
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					int rowsUpdated = statement.executeUpdate(generateSQLForInsert(newDBRequest));
					if(rowsUpdated > 0) {
						ResultSet rs = statement.executeQuery("SELECT MAX(" + UserAWSDBRequestTable.REQUEST_ID.getColumnName() + ") FROM " + UserAWSDBRequestTable.getTableName() + ";");
						while(rs.next()) {
							lastInsertedRequestId = rs.getInt(1);							
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					manager.cleanupStatement(statement);
				}
			}
		}
		return lastInsertedRequestId;
	}
	

	private static String generateSQLForInsert(UserAWSDBRequest newDBRequest) {
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(UserAWSDBRequestTable.getTableName());
		sql.append(" (");
		sql.append(UserAWSDBRequestTable.USER_ID.getColumnName() + ",");
		sql.append(UserAWSDBRequestTable.REQUEST_TIME.getColumnName() + ",");
		sql.append(UserAWSDBRequestTable.DB_INSTANCE_NAME.getColumnName() + ",");
		sql.append(UserAWSDBRequestTable.DB_TYPE.getColumnName() + ",");
		sql.append(UserAWSDBRequestTable.MASTER_USERNAME.getColumnName() + ",");
		sql.append(UserAWSDBRequestTable.MASTER_PASSWORD.getColumnName() + ",");
		sql.append(UserAWSDBRequestTable.DEPLOYEMENT_ID.getColumnName());
		sql.append(") values (");
		sql.append("'" + newDBRequest.getUserId() + "'");
		sql.append(",");
		sql.append("'" + new Timestamp(newDBRequest.getRequestTime().getTime()).toString() + "'");
		sql.append(",");
		sql.append("'" + newDBRequest.getDBInstanceName() + "'");
		sql.append(",");
		sql.append("'" + newDBRequest.getDBType() + "'");
		sql.append(",");
		sql.append("'" + newDBRequest.getMasterUsername() + "'");
		sql.append(",");
		sql.append("'" + newDBRequest.getMasterPassword() + "'");
		sql.append(",");
		sql.append("'" + newDBRequest.getDeploymentName() + "'");
		sql.append(");");
		
		System.out.println(sql.toString());
		return sql.toString();
	}
	
	public static ArrayList<UserAWSDBRequest> getUserRDSDetails(int user_id, String deploymentNameIn) {
		ArrayList<UserAWSDBRequest> rdsRequestFromDBList = new ArrayList<UserAWSDBRequest>();
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					ResultSet rs = statement.executeQuery(generateSQLForSelectuserRDSDetails(user_id, deploymentNameIn));
					while(rs.next()) {
						int userId = rs.getInt(UserAWSDBRequestTable.USER_ID.getColumnName());
						int requestId = rs.getInt(UserAWSDBRequestTable.REQUEST_ID.getColumnName());
						Date requestTime= rs.getDate(UserAWSDBRequestTable.REQUEST_TIME.getColumnName());
						String DBInstanceName =rs.getString(UserAWSDBRequestTable.DB_INSTANCE_NAME.getColumnName());
						String DBType = rs.getString(UserAWSDBRequestTable.DB_TYPE.getColumnName());
						String masterUsername = rs.getString(UserAWSDBRequestTable.MASTER_USERNAME.getColumnName());
						String masterPassword = rs.getString(UserAWSDBRequestTable.MASTER_PASSWORD.getColumnName());
						String deploymentName = rs.getString(UserAWSDBRequestTable.DEPLOYEMENT_ID.getColumnName());
						
						UserAWSDBRequest item = new  UserAWSDBRequest(requestId,userId,requestTime,DBInstanceName, DBType,masterUsername,masterPassword, deploymentName);
						rdsRequestFromDBList.add(item);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					manager.cleanupStatement(statement);
				}
			}
		}
		return rdsRequestFromDBList;
	}

	private static String generateSQLForSelectuserRDSDetails(int userId, String deploymentName) {
		StringBuilder sql = new StringBuilder("SELECT * FROM ");
		sql.append(UserAWSDBRequestTable.getTableName());
		sql.append(" WHERE ");
		sql.append(UserAWSDBRequestTable.USER_ID.getColumnName());
		sql.append("=" + userId);	
		sql.append(" AND ");
		sql.append(UserAWSS3RequestTable.DEPLOYEMENT_ID.getColumnName());
		sql.append(" = " + "'" + deploymentName + "'" + ";");
		System.out.println(sql.toString());
		return sql.toString();
	}	
	
	public static boolean delete( int userId, String deploymentName, String dbInstanceName) {
		boolean result = false;
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					int rowsUpdated = statement.executeUpdate(generateSQLForDeleteRDS(userId, deploymentName, dbInstanceName));
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
	
	private static String generateSQLForDeleteRDS(int userId, String deploymentName, String dbInstanceName) {
		StringBuilder sql = new StringBuilder("DELETE FROM ");
		sql.append(UserAWSDBRequestTable.getTableName());
		sql.append(" WHERE ");
		sql.append(UserAWSDBRequestTable.USER_ID.getColumnName());
		sql.append("=" + userId);	
		sql.append(" AND ");
		sql.append(UserAWSDBRequestTable.DEPLOYEMENT_ID.getColumnName());
		sql.append(" = " + "'" + deploymentName + "'");
		sql.append(" AND ");
		sql.append(UserAWSDBRequestTable.DB_INSTANCE_NAME.getColumnName());
		sql.append(" = " + "'" + dbInstanceName + "'" + ";");
		System.out.println(sql.toString());
		return sql.toString();
	}
}
