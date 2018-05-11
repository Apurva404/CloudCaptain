package table;

public enum UserAWSComputeRequestTable {
		REQUEST_ID(0, "RequestId", "INT"),
		USER_ID(1, "UserId", "INT"),
		REQUEST_TIME(2,"RequestTime","DateTime"),
		IMAGE_TYPE(3,"ImageType","varchar(255)"),
		INSTANCE_TYPE(4,"InstanceType","varchar(255)"),
		NO_OF_INSTANCE(5,"NoOfInstance","varchar(255)"),
		LOAD_BALANCER(6,"LoadBalancer","varchar(255)"),
		S3_LINK(7,"S3Link","varchar(500)"),
		DEPLOYMENT_ID(3,"DeploymentId","varchar(255)");
		
		private int columnIndex;
		private String columnName;
		private String columnDataType;
		
		private static final int size = values().length;
		
		UserAWSComputeRequestTable(int colIndex, String colName, String colType) {
	   		columnIndex = colIndex;
	   		columnName = colName;
	   		columnDataType = colType;
	   	}
	   	
	   	public int getColumnIndex() { return columnIndex; }
	   	public String getColumnName() { return columnName; }
	   	public String getColumnDataType() { return columnDataType; }
	   	
	   	public static int size() { return size; }
	   	public static String getTableName() { return "UserAWSComputeRequest"; }

}
