package universitysearch;

import static org.mockito.Mockito.*;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.mockito.Mockito;

public class TestInterface {
  SessionFactory factory = DBManager.getSessionFactory();
  
  @Test
  public void testUserManager() {
    UserManager test = Mockito.mock(UserManager.class);
    UserManager UM = new UserManager();
    test.setFactory(factory);
    
    UM.setFactory(factory);
    int userID = UM.registerUser("Bob", "Alice", "Alice6", "123456789", "bob.alice@mail.utoronto.ca", "password");
    
    Session session = factory.openSession();
    Transaction tx = session.beginTransaction();
    User user = (User) session.load(User.class, userID);
    assertEquals(user.getFirstName(), "Bob");
    assertEquals(user.getLastName(), "Alice");
    assertEquals(user.getUtorid(), "Alice6");
    assertEquals(user.getStudentNumber(), "123456789");
    assertEquals(user.getEmail(), "bob.alice@mail.utoronto.ca");
    assertEquals(user.getPassword(), generateHash("password"));
    session.delete(user); 
    tx.commit();
    session.close();
  }
  
  private String generateHash(String str) {
    MessageDigest md = null;
    try {
        md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(Charset.forName("UTF-8")), 0, str.length());
    } catch (NoSuchAlgorithmException e1) {
        e1.printStackTrace();
    }

    return new BigInteger(1, md.digest()).toString(16);
  }
}
