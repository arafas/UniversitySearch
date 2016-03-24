package universitysearch;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
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
	public Integer addFile(String fName, String fPath, String fDesc, String
            fHash, long fSize, int fOwn, int courseId, JSONArray tags) throws JSONException {
		Session session = factory.openSession();
	    Transaction tx = null;
		Integer fileId = null;

		try{
	         tx = session.beginTransaction();
	         File file = new File (fName, fPath, fDesc, fHash, fSize, fOwn, courseId);
	         fileId = (Integer) session.save(file);
             addTags(tags, fileId, session);
	         tx.commit();
		}catch (HibernateException e) {
	    	if (tx!=null)
	        	tx.rollback();
	        e.printStackTrace();
	 	}finally {
	        session.close();
	    }
		return fileId;
	}

	public void addTags(JSONArray tags, int fileId, Session session) throws JSONException {
		for (int i = 0; i < tags.length(); i++) {
			JSONObject obj = (JSONObject) tags.get(i);
			Tags tag = new Tags(obj.getString("text"), fileId);
			session.save(tag);
		}
	}
}
