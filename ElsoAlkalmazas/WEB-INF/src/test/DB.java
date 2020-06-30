package test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DB {
	
	final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final String URL = "jdbc:derby:sampleDB;create=true";
  
    private Connection conn = null;
    private Statement createStatement = null;
    private DatabaseMetaData dbmd = null;
    
    public DB() {
        try {
			Class.forName(JDBC_DRIVER).newInstance();
            conn = DriverManager.getConnection(URL);
            System.out.println("A híd létrejött.");
        } catch (Exception ex) {
            System.out.println("Valami baj van a connection létrehozásakor.");
            System.out.println("" + ex);
        }

        if (conn != null) {
            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Valami baj van a createStatement létrehozásakor.");
                System.out.println("" + ex);
            }
        }
             
        try {
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adatbázis leírás létrehozásakor.");
            System.out.println("" + ex);
        }
        
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "USERS", null);
            if (!rs.next()) {
                createStatement.execute("create table users(name varchar(20), address varchar(20))");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattábla létrehozásakor.");
            System.out.println("" + ex);
        }      
    }
    
}
