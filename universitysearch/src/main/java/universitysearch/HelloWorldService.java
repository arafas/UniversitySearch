package universitysearch;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;

@Path("/hello")
public class HelloWorldService {
 
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) throws SQLException {
 
		String output = "Jersey say : " + msg;
        DBManager dbManager = new DBManager();
//        Connection conn = dbManager.connectDB();


        return Response.status(200).entity(dbManager.connectDB()).build();
    }
 
}