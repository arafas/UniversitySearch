package universitysearch;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.sql.SQLException;

@Path("/API")
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

	@POST
	@Path("/fileUpload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addFile(@FormDataParam("file") InputStream fileInputStream,
						  @FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {

		FileUpload fileUpload = new FileUpload();
		fileUpload.saveFile(fileInputStream, contentDispositionHeader);
		return Response.status(200).entity("pass").build();
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
	
	@POST
	@Path("/signin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response signInUser(User user, @Context HttpServletRequest request) {
		String email = user.getEmail();
		String password = user.getPassword();
		
		// Aquire DB connection and add user
		SessionFactory factory = DBManager.getSessionFactory();

		UserManager UM = new UserManager();
		UM.setFactory(factory);
		User userInfo = UM.signInUser(email, password);
		
		if(userInfo == null) {
			return Response.status(403).entity("Incorrect username or password. Please check if you have activated your account.").build();

		} else {
			HttpSession jsessid = request.getSession(true);
			jsessid.setAttribute("userId", userInfo.getId());
			JSONObject jsonObject = null;

			try {
				jsonObject = new JSONObject();
				jsonObject.put("firstName", userInfo.getFirstName());
				jsonObject.put("lastName", userInfo.getLastName());
				jsonObject.put("isProf", userInfo.getIsProf());
				jsonObject.put("email", userInfo.getEmail());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return Response.ok(jsonObject).build();
		}
	}

	@POST
	@Path("/signOut")
	public Response signOutUser(@Context HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		httpSession.invalidate();

		return Response.ok().build();
	}

	@GET
	@Path("/search/{searchterm}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@PathParam("searchterm") String searchTerm) {
		SessionFactory sessionFactory = DBManager.getSessionFactory();

		MasterSearch masterSearch = new MasterSearch();
		masterSearch.setFactory(sessionFactory);

		String jsonResp = masterSearch.searchCoursesAndFiles(searchTerm);

		return Response.status(200).entity(jsonResp).build();
	}
}