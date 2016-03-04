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
public class EndPointManager {
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String input) throws SQLException {
		
		//String output = "Jersey say : " + columnName;
		//String tableOption = "read";
		// can me read, write, modify
		//String tableName = "users";
		// can be any table name
        SessionFactory factory = DBManager.getSessionFactory();
		
	    UserManager UM = new UserManager();
	    UM.setFactory(factory);
	    Integer empID1 = UM.addUser(input, "test", "test", "test", 0, 0, 0, "HASH TEST");
        
        return Response.status(200).entity(input).build();
    }
 
}