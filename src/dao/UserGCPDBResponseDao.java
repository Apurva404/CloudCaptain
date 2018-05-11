package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import constants.EntityConstants;
import db.DBManager;
import entity.UserGCPDBResponse;
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
}
