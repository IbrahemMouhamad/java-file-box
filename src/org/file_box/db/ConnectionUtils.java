package org.file_box.db;

import java.sql.Connection;
import java.sql.SQLException;
 
public class ConnectionUtils {
 
    public static Connection getConnection() 
              throws ClassNotFoundException, SQLException {
 
        // return MySQL connection
        return MySQLConnectionUtil.getMySQLConnection();
    }
     
    public static void closeQuietly(Connection conn) {
        try {
        	// close the connection
            conn.close();
        } catch (Exception e) {
        }
    }
 
    public static void rollbackQuietly(Connection conn) {
        try {
        	// uncommit the changes
            conn.rollback();
        } catch (Exception e) {
        }
    }
}