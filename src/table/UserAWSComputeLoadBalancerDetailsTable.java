package table;

public enum UserAWSComputeLoadBalancerDetailsTable {
	ELB_IDENTIFIER(0, "ELBIdentifier", "INT"),
	ELB_ENDPOINT(1,"ELBEndpoint","INT"),
	REQUEST_ID(2, "RequestId", "INT");
	
	private int columnIndex;
	private String columnName;
	private String columnDataType;
	
	private static final int size = values().length;
	
	UserAWSComputeLoadBalancerDetailsTable(int colIndex, String colName, String colType) {
   		columnIndex = colIndex;
   		columnName = colName;
   		columnDataType = colType;
   	}
   	
   	public int getColumnIndex() { return columnIndex; }
   	public String getColumnName() { return columnName; }
   	public String getColumnDataType() { return columnDataType; }
   	public static int size() { return size; }
   	public static String getTableName() { return "UserAWSComputeLoadBalancerDetails"; }
}