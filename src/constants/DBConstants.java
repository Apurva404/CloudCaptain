package constants;


public class DBConstants {
    public static String JDBC_class = "com.mysql.jdbc.Driver";
    public static String database_name_qualifier = "jdbc:mysql";
    public static String database_dns = "cloudcaptainbasic.cvymgjfswutu.us-west-2.rds.amazonaws.com";
    public static String database_port = "3306";
    public static String database_name = "Test";
    public static String database_user = "Admin";
    public static String database_password = "Admin2018";
    public static String database_use_SSL = "false";
    public static String database_connection_string = database_name_qualifier + "://" + database_dns + ":" + database_port + "/" +
            database_name + "?user=" + database_user + "&password=" + database_password + "&useSSL=" + database_use_SSL;


}
