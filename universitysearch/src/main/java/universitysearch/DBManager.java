package universitysearch;

import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Created by zubairbaig on 2/19/16.
 * Edited by  jacobsteele on 3/03/16.
 */
public class DBManager {
    public Connection conn;

    public int connectDB(String input) throws SQLException {
        conn = null;
        int test = 0;
        try {

            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 2: Open a connection
            conn = DriverManager.getConnection("jdbc:mysql://" + System.getenv().get("OPENSHIFT_MYSQL_DB_HOST") + ":" +
                    System.getenv("OPENSHIFT_MYSQL_DB_PORT") + "/universitysearch",
                    System.getenv("OPENSHIFT_MYSQL_DB_USERNAME"), System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD"));

            if (conn != null && input != ""){            	            	
            	UserManager userManager = new UserManager();
            	//userManager.setConn(conn);
            	//test = userManager.addUser(input, "test", "test", "test", 0, 0, 0);
            }
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return test;
    }
    

}
