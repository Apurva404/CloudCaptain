package table;

public enum UserAWSS3RequestTable {
	REQUEST_ID(0, "RequestId", "INT"),
	BUCKET_NAME(1,"BucketName","varchar(500)"),
	USER_ID(2, "UserId", "INT"),
	DEPLOYEMENT_ID(3,"DeploymentId","varchar(255)");
	
	private int columnIndex;
	private String columnName;
	private String columnDataType;
	
	private static final int size = values().length;
	
	UserAWSS3RequestTable(int colIndex, String colName, String colType) {
   		columnIndex = colIndex;
   		columnName = colName;
   		columnDataType = colType;
   	}	
   	public int getColumnIndex() { return columnIndex; }
   	public String getColumnName() { return columnName; }
   	public String getColumnDataType() { return columnDataType; }
   	public static int size() { return size; }
   	public static String getTableName() { return "UserAWSS3Request"; }
}
