package universitysearch;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class FileManager extends DBManager {
	private static SessionFactory factory;
	
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}
	
	/* Method to add a file to the database */
	public Integer addFile(String fName, String fPath, String fDesc, String fHash, long fSize, int fOwn, int courseId){
		Session session = factory.openSession();
	    Transaction tx = null;
		Integer userID = null;

		try{
	         tx = session.beginTransaction();
	         File file = new File (fName, fPath, fDesc, fHash, fSize, fOwn, courseId);
	         userID = (Integer) session.save(file); 
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
