package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import constants.EntityConstants;
import db.DBManager;
import entity.azure.UserAzureComputeRequest;
import table.UserAzureComputeRequestTable;

public class UserAzureComputeRequestDao {
	public static int insert( UserAzureComputeRequest newComputeRequest) {
		int lastInsertedRequestId = EntityConstants.INVALID_ID;
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					int rowsUpdated = statement.executeUpdate(generateSQLForInsert(newComputeRequest));
					if(rowsUpdated > 0) {
						ResultSet rs = statement.executeQuery("SELECT MAX(" + UserAzureComputeRequestTable.REQUEST_ID.getColumnName() + ") FROM " + UserAzureComputeRequestTable.getTableName() + ";");
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
	

	private static String generateSQLForInsert(UserAzureComputeRequest newComputeRequest) {
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(UserAzureComputeRequestTable.getTableName());
		sql.append(" (");
		sql.append(UserAzureComputeRequestTable.USER_ID.getColumnName() + ",");
		sql.append(UserAzureComputeRequestTable.REQUEST_TIME.getColumnName() + ",");
		sql.append(UserAzureComputeRequestTable.INSTANCE_NAME.getColumnName() + ",");
		sql.append(UserAzureComputeRequestTable.INSTANCE_TYPE.getColumnName() + ",");
		sql.append(UserAzureComputeRequestTable.S3_LINK.getColumnName() + ",");
		sql.append(UserAzureComputeRequestTable.DEPLOYMENT_ID.getColumnName() + ",");
		sql.append(UserAzureComputeRequestTable.IMAGE_TYPE.getColumnName());
		sql.append(") values (");
		sql.append("'" + newComputeRequest.getUserId() + "'");
		sql.append(",");
		sql.append("'" + new Timestamp(newComputeRequest.getRequestTime().getTime()).toString() + "'"); 
		sql.append(",");
		sql.append("'" + newComputeRequest.getInstanceName() + "'");
		sql.append(",");
		sql.append("'" + newComputeRequest.getInstanceType() + "'");
		sql.append(",");
		sql.append("'" + newComputeRequest.getS3Link() + "'");
		sql.append(",");
		sql.append("'" + newComputeRequest.getDeploymentName() + "'");
		sql.append(",");
		sql.append("'" + newComputeRequest.getImageType() + "'");
		sql.append(");");
		System.out.println(sql.toString());
		return sql.toString();
	}

}
