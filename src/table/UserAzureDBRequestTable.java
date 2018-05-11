package table;

public enum UserAzureDBRequestTable {
	REQUEST_ID(0, "RequestId", "INT"),
	USER_ID(1, "UserId", "INT"),
	REQUEST_TIME(2,"RequestTime","DateTime"),
	DB_TYPE(3,"DBType","varchar(255)"),
	DB_INSTANCE_NAME(4,"DBInstanceName","varchar(255)"),
	ROOT_USERNAME(5,"RootUsername","varchar(255)"),
	ROOT_PASSWORD(6,"RootPassword","varchar(255)"),
	DEPLOYEMENT_ID(7,"DeploymentId","varchar(255)");
	
	
	private int columnIndex;
	private String columnName;
	private String columnDataType;
	
	private static final int size = values().length;
	
	UserAzureDBRequestTable(int colIndex, String colName, String colType) {
   		columnIndex = colIndex;
   		columnName = colName;
   		columnDataType = colType;
   	}
   	
   	public int getColumnIndex() { return columnIndex; }
   	public String getColumnName() { return columnName; }
   	public String getColumnDataType() { return columnDataType; }
   	
   	public static int size() { return size; }
   	public static String getTableName() { return "UserAzureDBRequest"; }

}
