package universitysearch;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.SessionFactory;

import java.sql.SQLException;

@Path("/hello")
public class EndPointManager {

	@GET
	@Path("/test/{param}")
	public Response getMsg(@PathParam("param") String input) throws SQLException {

		// String output = "Jersey say : " + columnName;
		// String tableOption = "read";
		// can me read, write, modify
		// String tableName = "users";
		// can be any table name
		SessionFactory factory = DBManager.getSessionFactory();
		/*
		 * UserManager UM = new UserManager(); UM.setFactory(factory); Integer
		 * empID1 = UM.addUser(input, "test", "test", "test", 0, 0, 0,
		 * "HASH TEST");
		 */
		return Response.status(200).entity(input).build();
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addUser(User user) {
		// Set variables to the values from user registration
		String fName = user.getFirstName();
		String lName = user.getLastName();
		String utorid = user.getUtorid();
		String studentNumber = user.getStudentNumber();
		String email = user.getEmail() + "@mail.utoronto.ca";
		String pass = user.getPassword();

		// Aquire DB connection and add user
		SessionFactory factory = DBManager.getSessionFactory();

		UserManager UM = new UserManager();
		UM.setFactory(factory);
		Integer userID = UM.registerUser(fName, lName, utorid, studentNumber, email, pass);

		return "Your account has been made, please verify it by clicking the activation link that has been send to your email.";
	}

	@GET
	@Path("/activate/email/{email}/hash/{hash}")
	@Produces(MediaType.TEXT_PLAIN)
	public String activateUser(@PathParam("email") String email, @PathParam("hash") String hash) {
		// Aquire DB connection and add user
		SessionFactory factory = DBManager.getSessionFactory();

		UserManager UM = new UserManager();
		UM.setFactory(factory);
		UM.activateUser(email, hash);

		return "Your account has been activated, you can now login";
	}

}