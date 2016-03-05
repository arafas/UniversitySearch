package universitysearch;

import java.util.List; 
import java.util.Date;
import java.util.Iterator; 
 
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
 

/**
 * Created  by jacobsteele on 3/03/16.
 */
public class UserManager extends DBManager {
	private static SessionFactory factory; 
	
	/*
	public Connection conn = null;
		
	public String getFirstName(String columnName) throws SQLException {
	        String name = "";
	        Statement stmt = conn.createStatement();
	        //STEP 4: Execute a query
	        ResultSet rs = stmt.executeQuery("SELECT * FROM users");
	        while (rs.next()) {
	           name =  rs.getString(columnName);
	        }
	        return name;		
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}*/
	
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}
	/* Method to CREATE a user in the database */
	public Integer addUser(String fName, String lName, String utorid, String studentNumber, String email, String pass){
		Session session = factory.openSession();
	    Transaction tx = null;
		Integer userID = null;
		//User user = new User(email, pass, fName, lName, isPr, isAd, emailVer);
		//String addUser = "INSERT INTO `universitysearch`.`users` (`email`, `password`, `first_name`, `last_name`) VALUES ('" + 
		//email + "', '"+ pass + "', '" + fName + "', '" + lName + ")" ; 
        
		try{
	         tx = session.beginTransaction();
	         User employee = new User (fName, lName, utorid,  studentNumber, email, pass);
	         userID = (Integer) session.save(employee); 
	         tx.commit();
		}catch (HibernateException e) {
	    	if (tx!=null)
	        	tx.rollback();
	        e.printStackTrace(); 
	 	}finally {
	        session.close(); 
	    }
		return userID;
	}

}
