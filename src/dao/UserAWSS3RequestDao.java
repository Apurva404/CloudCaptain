package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import constants.EntityConstants;
import db.DBManager;
import entity.aws.UserAWSS3Request;
import table.UserAWSS3RequestTable;

public class UserAWSS3RequestDao {
	public static int insert( UserAWSS3Request newS3Request) {
		int lastInsertedResponseId = EntityConstants.INVALID_ID;
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					int rowsUpdated = statement.executeUpdate(generateSQLForInsert(newS3Request));
					if(rowsUpdated > 0) {
						ResultSet rs = statement.executeQuery("SELECT MAX(" + UserAWSS3RequestTable.REQUEST_ID.getColumnName() + ") FROM " + UserAWSS3RequestTable.getTableName() + ";");
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
	

	private static String generateSQLForInsert(UserAWSS3Request newS3Request) {
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(UserAWSS3RequestTable.getTableName());
		sql.append(" (");
		sql.append(UserAWSS3RequestTable.BUCKET_NAME.getColumnName() + ",");
		sql.append(UserAWSS3RequestTable.USER_ID.getColumnName() + ",");
		sql.append(UserAWSS3RequestTable.DEPLOYEMENT_ID.getColumnName());
		sql.append(") values (");
		sql.append("'" + newS3Request.getBucketName() + "'");
		sql.append(",");
		sql.append("'" + newS3Request.getUserId() + "'");
		sql.append(",");
		sql.append("'" + newS3Request.getDeploymentName() + "'");
		sql.append(");");
		
		System.out.println(sql.toString());
		return sql.toString();
	}
	
	public static ArrayList<UserAWSS3Request> getUserS3Details(int user_id,String deploymentNameIn) {
		ArrayList<UserAWSS3Request> S3RequestFromDBList = new ArrayList<UserAWSS3Request>();
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					ResultSet rs = statement.executeQuery(generateSQLForSelectuserS3Details(user_id,deploymentNameIn));
					while(rs.next()) {
						int userId = rs.getInt(UserAWSS3RequestTable.USER_ID.getColumnName());
						int requestId= rs.getInt(UserAWSS3RequestTable.REQUEST_ID.getColumnName());
						String bucketName =rs.getString(UserAWSS3RequestTable.BUCKET_NAME.getColumnName());
						String deploymentName = rs.getString(UserAWSS3RequestTable.DEPLOYEMENT_ID.getColumnName());
					
						UserAWSS3Request item = new  UserAWSS3Request(bucketName, userId,requestId,deploymentName);
						S3RequestFromDBList.add(item);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					manager.cleanupStatement(statement);
				}
			}
		}
		return S3RequestFromDBList;
	}

	private static String generateSQLForSelectuserS3Details(int userId, String deploymentName) {
		StringBuilder sql = new StringBuilder("SELECT * FROM ");
		sql.append(UserAWSS3RequestTable.getTableName());
		sql.append(" WHERE ");
		sql.append(UserAWSS3RequestTable.USER_ID.getColumnName());
		sql.append("=" + userId);	
		sql.append(" AND ");
		sql.append(UserAWSS3RequestTable.DEPLOYEMENT_ID.getColumnName());
		sql.append(" = " + "'" + deploymentName + "'" + ";");
		System.out.println(sql.toString());
		return sql.toString();
	}

	public static boolean delete( int userId, String deploymentName, String bucketName) {
		boolean result = false;
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					int rowsUpdated = statement.executeUpdate(generateSQLForDeleteS3(userId, deploymentName, bucketName));
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
	
	private static String generateSQLForDeleteS3(int userId, String deploymentName, String bucketName) {
		StringBuilder sql = new StringBuilder("DELETE FROM ");
		sql.append(UserAWSS3RequestTable.getTableName());
		sql.append(" WHERE ");
		sql.append(UserAWSS3RequestTable.USER_ID.getColumnName());
		sql.append("=" + userId);	
		sql.append(" AND ");
		sql.append(UserAWSS3RequestTable.DEPLOYEMENT_ID.getColumnName());
		sql.append(" = " + "'" + deploymentName + "'");
		sql.append(" AND ");
		sql.append(UserAWSS3RequestTable.BUCKET_NAME.getColumnName());
		sql.append(" = " + "'" + bucketName + "'");
		System.out.println(sql.toString());
		return sql.toString();
	}
}
