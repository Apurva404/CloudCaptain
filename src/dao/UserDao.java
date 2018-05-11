package dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import constants.EntityConstants;
import db.DBManager;
import table.UserTable;
import entity.User;

public class UserDao {
	public static int insert( User newUser) {
		int lastInsertedUserId = EntityConstants.INVALID_ID;
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					int rowsUpdated = statement.executeUpdate(generateSQLForInsert(newUser));
					if(rowsUpdated > 0) {
						ResultSet rs = statement.executeQuery("SELECT MAX(" + UserTable.USER_ID.getColumnName() + ") FROM " + UserTable.getTableName() + ";");
						while(rs.next()) {
							lastInsertedUserId = rs.getInt(1);							
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					manager.cleanupStatement(statement);
				}
			}
		}
		return lastInsertedUserId;
	}
	

	private static String generateSQLForInsert(User newUser) {
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(UserTable.getTableName());
		sql.append(" (");
		sql.append(UserTable.FULLNAME.getColumnName() + ",");
		sql.append(UserTable.EMAIL_ID.getColumnName() + ",");
	    sql.append(UserTable.PHONE.getColumnName() + ",");
		sql.append(UserTable.COMPANY.getColumnName() + ",");
		sql.append(UserTable.USERPASSWORD.getColumnName());
		sql.append(") values (");
		sql.append("'" + newUser.getFullname() + "'");
		sql.append(",");
		sql.append("'" + newUser.getEmailId() + "'");
		sql.append(",");
		sql.append("'" + newUser.getPhone() + "'");
		sql.append(",");
		sql.append("'" + newUser.getCompany() + "'");
		sql.append(",");
		sql.append("'" + newUser.getUserPassword() + "'");
		sql.append(");");
		
		System.out.println(sql.toString());
		return sql.toString();
	}
	
	public static int getUserId(User u) {
		int userIdFromDB = EntityConstants.INVALID_ID;
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					ResultSet rs = statement.executeQuery(generateSQLForSelect(u));
					
					if(rs.next()) {
						userIdFromDB = rs.getInt(UserTable.USER_ID.getColumnName());
						if(rs.next()) { // result has more than 1 entry then fail
							userIdFromDB = EntityConstants.INVALID_ID;
						}
					}
											
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					manager.cleanupStatement(statement);
				}
			}
		}
		return userIdFromDB;
	}

	private static String generateSQLForSelect(User u) {
		StringBuilder sql = new StringBuilder("SELECT " + UserTable.USER_ID.getColumnName() + " FROM ");
		sql.append(UserTable.getTableName() + " WHERE ");
		sql.append(UserTable.FULLNAME.getColumnName() + " = '" + u.getFullname() + "' AND ");
		sql.append(UserTable.USERPASSWORD.getColumnName() + " = '" + u.getUserPassword() + "'");
		sql.append(";");
		
		System.out.println(sql.toString());
		return sql.toString();
	}
}
