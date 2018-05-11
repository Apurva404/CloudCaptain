package dao;
import java.sql.SQLException;
import java.sql.Statement;
import db.DBManager;
import entity.aws.UserAWSComputeLoadBalancerDetails;
import table.UserAWSComputeLoadBalancerDetailsTable;

public class UserAWSComputeLoadBalancerDetailsDao {
	
	public static void insert(UserAWSComputeLoadBalancerDetails  newComputeLoadBalancer) {
		DBManager manager = DBManager.get();
		if(manager != null) {
			Statement statement = manager.getStatement();
			if (statement != null) {
				try {
					 	 statement.executeUpdate(generateSQLForInsert(newComputeLoadBalancer));
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					manager.cleanupStatement(statement);
				}
			}
		}
	}
	

	private static String generateSQLForInsert(UserAWSComputeLoadBalancerDetails newComputeLoadBalancer) {
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(UserAWSComputeLoadBalancerDetailsTable.getTableName());
		sql.append(" (");
		sql.append(UserAWSComputeLoadBalancerDetailsTable.ELB_IDENTIFIER.getColumnName() + ",");
		sql.append(UserAWSComputeLoadBalancerDetailsTable.ELB_ENDPOINT.getColumnName() + ",");
		sql.append(UserAWSComputeLoadBalancerDetailsTable.REQUEST_ID.getColumnName());
		sql.append(") values (");
		sql.append("'" + newComputeLoadBalancer.getElbIdentifier() + "'");
		sql.append(",");
		sql.append("'" + newComputeLoadBalancer.getElbEndpoint() + "'");
		sql.append(",");
		sql.append("'" + newComputeLoadBalancer.getRequestId() + "'");
		sql.append(");");		
		System.out.println(sql.toString());
		return sql.toString();
	}
}
