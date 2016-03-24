package universitysearch;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class TagManager extends DBManager {
	private static SessionFactory factory;
	
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}
	
	public Integer addTag(String tName){
		Session session = factory.openSession();
	    Transaction tx = null;
		Integer userID = null;

		try{
	         tx = session.beginTransaction();
	         Tag tag = new Tag (tName);
	         userID = (Integer) session.save(tag); 
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
	
	public void removeTag(int tID) {
	    //Delete File from database based on entered ID
	    Session session = factory.openSession();
	    Transaction tx = null;
	    try{
	        //SomeEntity ent = session.get(SomeEntity.class, '1234');
	        //session.delete(ent);
	        tx = session.beginTransaction();
	        Tag tag = new Tag();
	        tag.setId(tID);
	        session.delete(tag); 
	        tx.commit();
	    }catch (HibernateException e) {
	        if (tx!=null)
	            tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	        session.close(); 
	    }
	}
	
    public void modifyFile(int tID, String tName) {
      // Set elements to null that you do not want updated, or for long/ints set to -1
      // fileID is required
      Session session = factory.openSession();
      Transaction tx = null;
      try{
          tx = session.beginTransaction();
          Tag tag = (Tag) session.load(Tag.class, tID);
          // This point file is loaded from DB
          
          if (tName != null) {
            tag.setTagName(tName);
          }
          
          session.update(tag); 
          tx.commit();
      }catch (HibernateException e) {
          if (tx!=null)
              tx.rollback();
          e.printStackTrace(); 
      }finally {
          session.close(); 
      }
  }
}
