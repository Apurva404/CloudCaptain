package table;

public enum UserAzureDBResponseTable {
	RESPONSE_ID(0, "ResponseId", "INT"),
	REQUEST_ID(1,"RequestId","INT"),
	USER_ID(2, "UserId", "INT"),
	INSTANCE_ENDPOINT(3,"InstanceEndpoint","varchar(255)");
	
	private int columnIndex;
	private String columnName;
	private String columnDataType;
	
	private static final int size = values().length;
	
	UserAzureDBResponseTable(int colIndex, String colName, String colType) {
   		columnIndex = colIndex;
   		columnName = colName;
   		columnDataType = colType;
   	}
   	
   	public int getColumnIndex() { return columnIndex; }
   	public String getColumnName() { return columnName; }
   	public String getColumnDataType() { return columnDataType; }
   	public static int size() { return size; }
   	public static String getTableName() { return "UserAzureDBResponse"; }
}
