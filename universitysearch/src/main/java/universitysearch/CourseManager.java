package universitysearch;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CourseManager extends DBManager {
	private static SessionFactory factory;
	
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}
	
	/* Method to add a course to the database */
	public Integer addCourse(String cName, String cDesc, String cCode, int pID){
		Session session = factory.openSession();
	    Transaction tx = null;
		Integer userID = null;
		//User user = new User(email, pass, fName, lName, isPr, isAd, emailVer);
		//String addUser = "INSERT INTO `universitysearch`.`users` (`email`, `password`, `first_name`, `last_name`) VALUES ('" + 
		//email + "', '"+ pass + "', '" + fName + "', '" + lName + ")" ; 
        
		try{
	         tx = session.beginTransaction();
	         Course course = new Course(cName, cDesc, cCode, pID);
	         userID = (Integer) session.save(course); 
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
