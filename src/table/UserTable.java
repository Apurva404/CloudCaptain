package table;

public enum UserTable {
	USER_ID(0, "UserId", "INT"),
	FULLNAME(1, "Fullname", "VARCHAR(255) NOT NULL"),
	EMAIL_ID(3, "Email", "VARCHAR(255) NOT NULL"),
	PHONE(4, "Phone", "VARCHAR(255)"),
	COMPANY(5, "Company", "VARCHAR(255)"),
	USERPASSWORD(4, "UserPassword", "VARCHAR(255) NOT NULL");
	
	
	private int columnIndex;
	private String columnName;
	private String columnDataType;
	
	private static final int size = values().length;
	
   	UserTable(int colIndex, String colName, String colType) {
   		columnIndex = colIndex;
   		columnName = colName;
   		columnDataType = colType;
   	}
   	
   	public int getColumnIndex() { return columnIndex; }
   	public String getColumnName() { return columnName; }
   	public String getColumnDataType() { return columnDataType; }
   	
   	public static int size() { return size; }
   	public static String getTableName() { return "User"; }

}
