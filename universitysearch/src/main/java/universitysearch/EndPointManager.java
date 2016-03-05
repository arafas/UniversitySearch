package universitysearch;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.hibernate.SessionFactory;
import java.sql.SQLException;

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
		
        /*
	    UserManager UM = new UserManager();
	    UM.setFactory(factory);
	    Integer empID1 = UM.addUser(input, "test", "test", "1234", "bob@test.com", "password");
        */
        /*
	    FileManager FM = new FileManager();
	    FM.setFactory(factory);
	    Integer fileID1 = FM.addFile(input, "/test/", "fileTest", "WhoCares", 10, 1);
        */
        /*
	    CourseManager CM = new CourseManager();
	    CM.setFactory(factory);
	    Integer courseID1 = CM.addCourse(input, "blahblah", "CSC490", 2);
        return Response.status(200).entity(input).build();
        */
	    FileCourseManager FM = new FileCourseManager();
	    FM.setFactory(factory);
	    Integer FMID1 = FM.linkCourse(1, 1, 1);
        return Response.status(200).entity(input).build();
    }
 
}