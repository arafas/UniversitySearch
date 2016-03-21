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
	public Integer addFile(String fName, String fPath, String fDesc, String fHash, long fSize, int fOwn){
		Session session = factory.openSession();
	    Transaction tx = null;
		Integer userID = null;

		try{
	         tx = session.beginTransaction();
	         File file = new File (fName, fPath, fDesc, fHash, fSize, fOwn);
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
	
	public void removeFile(int fileID) {
	    //Delete File from database based on entered ID
	    Session session = factory.openSession();
	    Transaction tx = null;
	    try{
	        //SomeEntity ent = session.get(SomeEntity.class, '1234');
	        //session.delete(ent);
	        tx = session.beginTransaction();
	        File file = new File();
	        file.setId(fileID);
	        session.delete(file); 
	        tx.commit();
	    }catch (HibernateException e) {
	        if (tx!=null)
	            tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	        session.close(); 
	    }
	}
	
    public void modifyFile(int fileID, String fName, String fPath, String fDesc, String fHash, long fSize, int fOwn) {
      // Set elements to null that you do not want updated, or for long/ints set to -1
      // fileID is required
      Session session = factory.openSession();
      Transaction tx = null;
      try{
          tx = session.beginTransaction();
          File file = (File) session.load(File.class, fileID);
          // This point file is loaded from DB
          
          if (fName != null) {
            file.setFileName(fName);
          }
          if (fPath != null) {
            file.setFilePath(fPath);
          }
          if (fDesc != null) {
            file.setFileDesc(fDesc);
          }
          if (fHash != null) {
            file.setFileHash(fHash);
          }
          if (fSize != -1) {
            file.setFileSize(fSize);
          }
          if (fOwn != -1) {
            file.setFileOwner(fOwn);
          }
          
          session.update(file); 
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
