package table;

public enum UserDeploymentRequestTable {
	DEPLOYMENT_NAME(0,"DeploymentName","varchar(255)"),
	CLOUD_TYPE(0,"CloudType","varchar(255)"),
	USER_ID(2, "UserId", "INT");
	
	private int columnIndex;
	private String columnName;
	private String columnDataType;
	
	private static final int size = values().length;
	
	UserDeploymentRequestTable(int colIndex, String colName, String colType) {
   		columnIndex = colIndex;
   		columnName = colName;
   		columnDataType = colType;
   	}
   	
   	public int getColumnIndex() { return columnIndex; }
   	public String getColumnName() { return columnName; }
   	public String getColumnDataType() { return columnDataType; }
   	public static int size() { return size; }
   	public static String getTableName() { return "UserDeploymentRequest"; }
}
