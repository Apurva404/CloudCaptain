package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import constants.EntityConstants;
import db.DBManager;
import entity.aws.UserAWSComputeRequest;
import table.UserAWSComputeRequestTable;


public class UserAWSComputeRequestDao {
	public static int insert( UserAWSComputeRequest newComputeRequest) {
		int lastInsertedRequestId = EntityConstants.INVALID_ID;
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					int rowsUpdated = statement.executeUpdate(generateSQLForInsert(newComputeRequest));
					if(rowsUpdated > 0) {
						ResultSet rs = statement.executeQuery("SELECT MAX(" + UserAWSComputeRequestTable.REQUEST_ID.getColumnName() + ") FROM " + UserAWSComputeRequestTable.getTableName() + ";");
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
	

	private static String generateSQLForInsert(UserAWSComputeRequest newComputeRequest) {
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(UserAWSComputeRequestTable.getTableName());
		sql.append(" (");
		sql.append(UserAWSComputeRequestTable.USER_ID.getColumnName() + ",");
		sql.append(UserAWSComputeRequestTable.REQUEST_TIME.getColumnName() + ",");
		sql.append(UserAWSComputeRequestTable.IMAGE_TYPE.getColumnName() + ",");
		sql.append(UserAWSComputeRequestTable.INSTANCE_TYPE.getColumnName() + ",");
		sql.append(UserAWSComputeRequestTable.NO_OF_INSTANCE.getColumnName() + ",");
		sql.append(UserAWSComputeRequestTable.LOAD_BALANCER.getColumnName() + ",");
		sql.append(UserAWSComputeRequestTable.S3_LINK.getColumnName() + ",");
		sql.append(UserAWSComputeRequestTable.DEPLOYMENT_ID.getColumnName());
		sql.append(") values (");
		sql.append("'" + newComputeRequest.getUserId() + "'");
		sql.append(",");
		sql.append("'" + new Timestamp(newComputeRequest.getRequestTime().getTime()).toString() + "'"); 
		sql.append(",");
		sql.append("'" + newComputeRequest.getImageType() + "'");
		sql.append(",");
		sql.append("'" + newComputeRequest.getInstanceType() + "'");
		sql.append(",");
		sql.append("'" + newComputeRequest.getNoOfInstance() + "'");
		sql.append(",");
		sql.append("'" + newComputeRequest.getLoadBalancer() + "'");
		sql.append(",");
		sql.append("'" + newComputeRequest.getS3Link() + "'");
		sql.append(",");
		sql.append("'" + newComputeRequest.getDeploymentName() + "'");
		sql.append(");");
		System.out.println(sql.toString());
		return sql.toString();
	}
	
}
