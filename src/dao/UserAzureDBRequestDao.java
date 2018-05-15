package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import constants.EntityConstants;
import db.DBManager;
import entity.azure.UserAzureDBRequest;
import table.UserAzureDBRequestTable;

public class UserAzureDBRequestDao {
	public static int insert( UserAzureDBRequest newDBRequest) {
		int lastInsertedRequestId = EntityConstants.INVALID_ID;
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					int rowsUpdated = statement.executeUpdate(generateSQLForInsert(newDBRequest));
					if(rowsUpdated > 0) {
						ResultSet rs = statement.executeQuery("SELECT MAX(" + UserAzureDBRequestTable.REQUEST_ID.getColumnName() + ") FROM " + UserAzureDBRequestTable.getTableName() + ";");
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
	

	private static String generateSQLForInsert(UserAzureDBRequest newDBRequest) {
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(UserAzureDBRequestTable.getTableName());
		sql.append(" (");
		sql.append(UserAzureDBRequestTable.USER_ID.getColumnName() + ",");
		sql.append(UserAzureDBRequestTable.REQUEST_TIME.getColumnName() + ",");
		sql.append(UserAzureDBRequestTable.DB_TYPE.getColumnName() + ",");
		sql.append(UserAzureDBRequestTable.DB_INSTANCE_NAME.getColumnName() + ",");
		sql.append(UserAzureDBRequestTable.ROOT_USERNAME.getColumnName() + ",");
		sql.append(UserAzureDBRequestTable.ROOT_PASSWORD.getColumnName()+ ",");
		sql.append(UserAzureDBRequestTable.DEPLOYEMENT_ID.getColumnName());
		sql.append(") values (");
		sql.append("'" + newDBRequest.getUserId() + "'");
		sql.append(",");
		sql.append("'" + new Timestamp(newDBRequest.getRequestTime().getTime()).toString() + "'"); 
		sql.append(",");
		sql.append("'" + newDBRequest.getDBType() + "'");
		sql.append(",");
		sql.append("'" + newDBRequest.getDBInstanceName() + "'");
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

}
