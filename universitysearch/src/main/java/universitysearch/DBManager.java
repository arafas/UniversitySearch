package universitysearch;

import java.io.IOException;
import java.sql.*;

/**
 * Created by zubairbaig on 2/19/16.
 */
public class DBManager {
    public Connection conn;

    public String connectDB() throws SQLException {
        conn = null;
        Statement stmt = null;
        String test = "";
        try {

            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 2: Open a connection
            conn = DriverManager.getConnection("jdbc:mysql://" + System.getenv().get("OPENSHIFT_MYSQL_DB_HOST") + ":" +
                    System.getenv("OPENSHIFT_MYSQL_DB_PORT") + "/universitysearch",
                    System.getenv("OPENSHIFT_MYSQL_DB_USERNAME"), System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD"));

            test = getName();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return test;

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
