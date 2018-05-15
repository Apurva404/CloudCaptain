package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import constants.DBConstants;

public class DBManager {
    private static DBManager manager = null;
    private Map<Statement, Connection> connections;

    private DBManager() throws ClassNotFoundException {
        Class.forName(DBConstants.JDBC_class);
        connections = new HashMap<>();
    }

    public static DBManager get() {
        if(manager == null)
            try {
                manager = new DBManager();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Unable to connect to database....");
                System.exit(0);
            }
        return manager;
    }

    public Statement getStatement() {
        Connection connection = null;
        Statement statement = null;
        try {
            // create a database connection
        	System.out.println(DBConstants.database_connection_string);
            connection = DriverManager.getConnection(DBConstants.database_connection_string);
            statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            //String sql = "PRAGMA foreign_keys = ON";
            //statement.executeUpdate(sql);
        } catch(SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
            try {
                if(connection != null)
                    connection.close();
            } catch(SQLException e1) {
                // connection close failed.
                System.err.println(e1);
            }
        }

        if((connection != null) && (statement != null))  {
            connections.put(statement, connection);
        }

        return statement;
    }

    public void cleanupStatement(Statement statement) {
        if((statement != null) && (connections.containsKey(statement))) {
            try {
                Connection connection = connections.get(statement);
                if(connection != null)
                    connection.close();
            } catch(SQLException e1) {
                // connection close failed.
                System.err.println(e1);
            }
        }
    }
}
