package universitysearch;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

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
	
	public void deleteCourse(int courseId, int profId) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			// Delete file from db
			Course course = new Course();
			course.setProfID(profId);;
			course.setId(courseId);
			session.delete(course);

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public String getFollowingCourses(int userId) {
    	Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			// Get courses users is following
			Criteria criteria = session.createCriteria(UserCourse.class);
			Criterion userValue = Restrictions.eq("user_id", userId);
			List<UserCourse> userCourseList = criteria.list();

			// Get full course information
			Criteria criteria2 = session.createCriteria(Course.class);
			Criterion courseValue = Restrictions.eq("course_id", userCourseList);
			List<Course> courseList = criteria2.list();
			
			// Check to see if course is found
			if (courseList.size() > 0) {
				return getJsonResultObj(courseList);
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
	
    public String getJsonResultObj(List<Course> courses) {
        String res = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            res = mapper.writeValueAsString(courses);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

	
}
