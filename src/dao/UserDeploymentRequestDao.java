package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import db.DBManager;
import entity.aws.UserAWSDeploymentRequest;
import table.UserDeploymentRequestTable;


public class UserDeploymentRequestDao {
	public static void insert(UserAWSDeploymentRequest  newDeploymentRequest) {
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					 	statement.executeUpdate(generateSQLForInsert(newDeploymentRequest));
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					manager.cleanupStatement(statement);
				}
			}
		}
	}
	

	private static String generateSQLForInsert(UserAWSDeploymentRequest newDeploymentRequest) {
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(UserDeploymentRequestTable.getTableName());
		sql.append(" (");
		sql.append(UserDeploymentRequestTable.DEPLOYMENT_NAME.getColumnName() + ",");
		sql.append(UserDeploymentRequestTable.CLOUD_TYPE.getColumnName() + ",");
		sql.append(UserDeploymentRequestTable.USER_ID.getColumnName());
		sql.append(") values (");
		sql.append("'" + newDeploymentRequest.getDeploymentName() + "'");
		sql.append(",");
		sql.append("'" + newDeploymentRequest.getCloudType() + "'");
		sql.append(",");
		sql.append("'" + newDeploymentRequest.getUserId() + "'");
		sql.append(");");		
		System.out.println(sql.toString());
		return sql.toString();
	}
	
	public static ArrayList<UserAWSDeploymentRequest> getUserDeploymentName(int user_id) {
		ArrayList<UserAWSDeploymentRequest> deploymentNameDBFromDBList = new ArrayList<UserAWSDeploymentRequest>();
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					ResultSet rs = statement.executeQuery(generateSQLForSelectUserDeploymentName(user_id));
					while(rs.next()) {
						String deploymentName = rs.getString(UserDeploymentRequestTable.DEPLOYMENT_NAME.getColumnName());
						String  cloudType= rs.getString(UserDeploymentRequestTable.CLOUD_TYPE.getColumnName());
						int userId =rs.getInt(UserDeploymentRequestTable.USER_ID.getColumnName());
					
						UserAWSDeploymentRequest item = new  UserAWSDeploymentRequest(deploymentName,cloudType ,userId);
						deploymentNameDBFromDBList.add(item);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					manager.cleanupStatement(statement);
				}
			}
		}
		return deploymentNameDBFromDBList;
	}

	private static String generateSQLForSelectUserDeploymentName(int userId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM ");
		sql.append(UserDeploymentRequestTable.getTableName());
		sql.append(" WHERE ");
		sql.append(UserDeploymentRequestTable.USER_ID.getColumnName());
		sql.append("=" + userId + ";");	
		System.out.println(sql.toString());
		return sql.toString();
	}
}
