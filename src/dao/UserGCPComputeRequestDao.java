package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import constants.EntityConstants;
import db.DBManager;
import entity.UserGCPComputeRequest;
import table.UserGCPComputeRequestTable;

public class UserGCPComputeRequestDao {
	public static int insert( UserGCPComputeRequest newComputeRequest) {
		int lastInsertedRequestId = EntityConstants.INVALID_ID;
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					int rowsUpdated = statement.executeUpdate(generateSQLForInsert(newComputeRequest));
					if(rowsUpdated > 0) {
						ResultSet rs = statement.executeQuery("SELECT MAX(" + UserGCPComputeRequestTable.REQUEST_ID.getColumnName() + ") FROM " + UserGCPComputeRequestTable.getTableName() + ";");
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
	

	private static String generateSQLForInsert(UserGCPComputeRequest newComputeRequest) {
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(UserGCPComputeRequestTable.getTableName());
		sql.append(" (");
		sql.append(UserGCPComputeRequestTable.USER_ID.getColumnName() + ",");
		sql.append(UserGCPComputeRequestTable.REQUEST_TIME.getColumnName() + ",");
		sql.append(UserGCPComputeRequestTable.INSTANCE_NAME.getColumnName() + ",");
		sql.append(UserGCPComputeRequestTable.INSTANCE_TYPE.getColumnName() + ",");
		sql.append(UserGCPComputeRequestTable.WARFILE_LINK.getColumnName() + ",");
		sql.append(UserGCPComputeRequestTable.DEPLOYMENT_ID.getColumnName());
		sql.append(") values (");
		sql.append("'" + newComputeRequest.getUserId() + "'");
		sql.append(",");
		sql.append("'" + new Timestamp(newComputeRequest.getRequestTime().getTime()).toString() + "'"); 
		sql.append(",");
		sql.append("'" + newComputeRequest.getInstanceName() + "'");
		sql.append(",");
		sql.append("'" + newComputeRequest.getInstanceType() + "'");
		sql.append(",");
		sql.append("'" + newComputeRequest.getwarFileLink() + "'");
		sql.append(",");
		sql.append("'" + newComputeRequest.getDeploymentName() + "'");
		sql.append(");");
		System.out.println(sql.toString());
		return sql.toString();
	}

}
