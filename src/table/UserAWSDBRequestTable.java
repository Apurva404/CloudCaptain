package table;

public enum UserAWSDBRequestTable {
	REQUEST_ID(0, "RequestId", "INT"),
	USER_ID(1, "UserId", "INT"),
	REQUEST_TIME(2,"RequestTime","DateTime"),
	DB_INSTANCE_NAME(3,"DBInstanceName","varchar(255)"),
	DB_TYPE(4,"DBType","varchar(255)"),
	MASTER_USERNAME(5,"MasterUsername","varchar(255)"),
	MASTER_PASSWORD(6,"MasterPassword","varchar(255)"),
	DEPLOYEMENT_ID(3,"DeploymentId","varchar(255)");
	
	
	private int columnIndex;
	private String columnName;
	private String columnDataType;
	
	private static final int size = values().length;
	
	UserAWSDBRequestTable(int colIndex, String colName, String colType) {
   		columnIndex = colIndex;
   		columnName = colName;
   		columnDataType = colType;
   	}
   	
   	public int getColumnIndex() { return columnIndex; }
   	public String getColumnName() { return columnName; }
   	public String getColumnDataType() { return columnDataType; }
   	
   	public static int size() { return size; }
   	public static String getTableName() { return "UserAWSDBRequest"; }
}
