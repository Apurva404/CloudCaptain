package table;

public enum UserAWSComputeResponseTable {
	RESPONSE_ID(0, "ResponseId", "INT"),
	REQUEST_ID(1,"RequestId","INT"),
	USER_ID(2, "UserId", "INT"),
	INSTANCE_DNS(3,"InstanceDNS","varchar(500)"),
	INSTANCE_STATE(2,"InstanceState","varchar(500)"),
	INSTANCE_LAUNCH_TIME(4,"instanceLaunchTime","varchar(500)"),
	INSTANCE_IP(5,"InstanceIP","varchar(255)");
	
	private int columnIndex;
	private String columnName;
	private String columnDataType;
	
	private static final int size = values().length;
	
	UserAWSComputeResponseTable(int colIndex, String colName, String colType) {
   		columnIndex = colIndex;
   		columnName = colName;
   		columnDataType = colType;
   	}
   	
   	public int getColumnIndex() { return columnIndex; }
   	public String getColumnName() { return columnName; }
   	public String getColumnDataType() { return columnDataType; }
   	public static int size() { return size; }
   	public static String getTableName() { return "UserAWSComputeResponse"; }
}