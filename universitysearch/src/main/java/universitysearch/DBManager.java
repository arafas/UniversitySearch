package universitysearch;

import java.io.IOException;
import java.sql.*;

/**
 * Created by zubairbaig on 2/19/16.
 */
public class DBManager {
    public static Connection conn;

    public static Connection connectDB() throws SQLException {
        conn = null;
        try {
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 2: Open a connection
            conn = DriverManager.getConnection("jdbc:mysql://" + System.getenv().get("OPENSHIFT_MYSQL_DB_HOST") + ":" +
                    System.getenv("OPENSHIFT_MYSQL_DB_PORT") + "/universitysearch",
                    System.getenv("OPENSHIFT_MYSQL_DB_USERNAME"), System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD"));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;

    }

    public String getName() throws SQLException {
        String name = "";
        Statement stmt;
        //STEP 4: Execute a query
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");

        while (rs.next()) {
           name =  rs.getString("name");
        }
        return name;
    }
}
