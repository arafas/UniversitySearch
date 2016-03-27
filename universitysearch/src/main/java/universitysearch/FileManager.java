package universitysearch;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.*;

import java.util.List;

public class FileManager extends DBManager {
	private static SessionFactory factory;

	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	/* Method to add a file to the database */
	public Integer addFile(String fName, String fPath, String fDesc, String fHash, long fSize, int fOwn, String tHash,
			int courseId, JSONArray tags) throws JSONException {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer fileId = null;

		try {
			tx = session.beginTransaction();
			File file = new File(fName, fPath, fDesc, fHash, fSize, fOwn, tHash, courseId);
			fileId = (Integer) session.save(file);
			addTags(tags, fileId, session);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return fileId;
	}

	public void addTags(JSONArray tags, int fileId, Session session) throws JSONException {
		for (int i = 0; i < tags.length(); i++) {
			JSONObject obj = (JSONObject) tags.get(i);
			Tags tag = new Tags(obj.getString("text"), fileId);
			int id = (Integer) session.save(tag);
		}
	}

	public void addTags(JSONArray tags, int fileId) throws JSONException {
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			addTags(tags, fileId, session);
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null)
				tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}

	public List getTags (int fileId)  {
		Session session = factory.openSession();
		Query query = session.createQuery("from universitysearch.Tags where fileId = :id ");
		query.setParameter("id", fileId);
		return query.list();
	}
	
	public void deleteFile(String fileId) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			// Get file info
			String fileInfo = getFileInfo(fileId);
			JSONObject jsonObject = new JSONObject(fileInfo);
			
			// Delete file from db
			File file = new File();
			file.setId(Integer.parseInt(fileId));
			session.delete(file);
			
			// Delete physical file from db
			java.io.File actualFile = new java.io.File(System.getenv("OPENSHIFT_DATA_DIR") + "/" + jsonObject.getString("filePath").substring(jsonObject.getString("filePath").indexOf('/'), jsonObject.getString("filePath").length()) + jsonObject.getString("fileName"));
			Path filePath = actualFile.toPath();
			
			try {
			    Files.delete(filePath);
			} catch (NoSuchFileException x) {
			    System.err.format("%s: no such" + " file or directory%n", filePath);
			} catch (DirectoryNotEmptyException x) {
			    System.err.format("%s not empty%n", filePath);
			} catch (IOException x) {
			    // File permission problems are caught here.
			    System.err.println(x);
			}

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

    public void modifyFile(int fileID, String fName, String fPath, String fDesc, String fHash, long fSize, int fOwn, String tHash) {
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
			if (tHash != null) {
				file.setTimeHash(tHash);
			}

			session.update(file);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public String getFileInfo(String fileId) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(File.class);
			Criterion fileValue = Restrictions.eq("id", Integer.parseInt(fileId));
			criteria.add(fileValue);
			List<File> fileList = criteria.list();

			// Check to see if user is found
			if (fileList.size() == 1) {
				return getJsonResultObj(fileList.get(0));
			}

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	public void approveFile(int fileId, int profId) throws Exception {
		Session session = factory.openSession();
		
		
		Criteria criteria = session.createCriteria(File.class);
		Criterion fileValue = Restrictions.eq("id", fileId);
		criteria.add(fileValue);
		
		File file = (File) criteria.uniqueResult();

		if(file == null) {
			throw new Exception("File doesn't exist");
		}
		
		Criteria criteria2 = session.createCriteria(Course.class);
		Criterion courseValue = Restrictions.eq("id", file.getCourseId());
		criteria2.add(courseValue);
		
		Course course = (Course) criteria2.uniqueResult();
		
		if(course == null) {
			throw new Exception("Course associated with file not found");
		}
		
		// If prof has authorization to approve file
		if(profId == course.getProfID()) {
		
			Transaction tx = null;
			try {
				tx = session.beginTransaction();

				if(file != null) {
					file.setIsApprov(1);
					session.update(file);
				}
				
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
		} else {
			throw new Exception("You are not authorized to approve this file because you did not create this course");
		}
	}

	public List<File> getFilesForCourse(int id) throws Exception {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			Criteria criteria = session.createCriteria(File.class);
			Criterion fileValue = Restrictions.eq("courseId", id);
			criteria.add(fileValue);
			List<File> fileList = criteria.list();

			return fileList;
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	public String getJsonResultObj(File file) {
		String res = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			res = mapper.writeValueAsString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	public int isApproved(int fileId) throws Exception {
		Session session = factory.openSession();
		
		Criteria criteria = session.createCriteria(File.class);
		Criterion fileValue = Restrictions.eq("id", fileId);
		criteria.add(fileValue);
		
		File file = (File) criteria.uniqueResult();

		if(file == null) {
			throw new Exception("File doesn't exist");
		}
		
		return file.getIsApprov();
	}
}
