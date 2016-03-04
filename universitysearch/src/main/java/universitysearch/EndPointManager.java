package universitysearch;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("/hello")
public class HelloWorldService {
	private static SessionFactory factory; 
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String input) throws SQLException {
 
		//String output = "Jersey say : " + columnName;
		//String tableOption = "read";
		// can me read, write, modify
		//String tableName = "users";
		// can be any table name
        //DBManager dbManager = new DBManager();
        //int test = dbManager.connectDB(input);
		try{
	       factory = new Configuration().configure().buildSessionFactory();
	    }catch (Throwable ex) { 
	       System.err.println("Failed to create sessionFactory object." + ex);
	       throw new ExceptionInInitializerError(ex); 
	    }
	    UserManager UM = new UserManager();
	    UM.setFactory(factory);
	    /* Add input user records in database */
	    Integer empID1 = UM.addUser(input, "test", "test", "test", 0, 0, 0);
        
        return Response.status(200).entity(empID1).build();
    }
 
}