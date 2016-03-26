package universitysearch;

import static org.mockito.Mockito.*;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class TestInterface {
  //SessionFactory factory = DBManager.getSessionFactory();
  
  @Test
  public void testUser() {
    User mockUser = Mockito.mock(User.class);
    
    User testUser = new User("Bob", "Dave", "bobD4", "987654321", "bob.dave@mail.utoronto.ca", "password123");
    testUser.setId(40);
    testUser.setHash("TestHash");
    testUser.setCreationDate("2016-05-16 23:03:02");
    testUser.setIsProf(0);
    testUser.setIsAdmin(0);
    testUser.setActive(0);
    
    assertEquals(testUser.getId(), 40);
    assertEquals(testUser.getFirstName(), "Bob");
    assertEquals(testUser.getLastName(), "Dave");
    assertEquals(testUser.getUtorid(), "bobD4");
    assertEquals(testUser.getStudentNumber(), "987654321");
    assertEquals(testUser.getEmail(), "bob.dave@mail.utoronto.ca");
    assertEquals(testUser.getPassword(), "password123");
    assertEquals(testUser.getIsProf(), 0);
    assertEquals(testUser.getIsAdmin(), 0);
    assertEquals(testUser.getActive(), 0);
    assertEquals(testUser.getHash(), "TestHash");
    assertEquals(testUser.getCreationDate(), "2016-05-16 23:03:02");
    
    when(mockUser.getId()).thenReturn(2);
    when(mockUser.getFirstName()).thenReturn("Alice");
    when(mockUser.getLastName()).thenReturn("Jane");
    when(mockUser.getUtorid()).thenReturn("janeA6");
    when(mockUser.getStudentNumber()).thenReturn("123456789");
    when(mockUser.getEmail()).thenReturn("alice.jane@mail.utoronto.ca");
    when(mockUser.getPassword()).thenReturn("password");
    when(mockUser.getIsProf()).thenReturn(1);
    when(mockUser.getIsAdmin()).thenReturn(0);
    when(mockUser.getActive()).thenReturn(1);
    when(mockUser.getHash()).thenReturn("testhash");
    when(mockUser.getCreationDate()).thenReturn("2016-03-16 15:00:00");
    
    assertEquals(mockUser.getId(), 2);
    assertEquals(mockUser.getFirstName(), "Alice");
    assertEquals(mockUser.getLastName(), "Jane");
    assertEquals(mockUser.getUtorid(), "janeA6");
    assertEquals(mockUser.getStudentNumber(), "123456789");
    assertEquals(mockUser.getEmail(), "alice.jane@mail.utoronto.ca");
    assertEquals(mockUser.getPassword(), "password");
    assertEquals(mockUser.getIsProf(), 1);
    assertEquals(mockUser.getIsAdmin(), 0);
    assertEquals(mockUser.getActive(), 1);
    assertEquals(mockUser.getHash(), "testhash");
    assertEquals(mockUser.getCreationDate(), "2016-03-16 15:00:00");
    
    verify(mockUser, times(1)).getId();
    verify(mockUser, times(1)).getFirstName();
    verify(mockUser, times(1)).getLastName();
    verify(mockUser, times(1)).getUtorid();
    verify(mockUser, times(1)).getStudentNumber();
    verify(mockUser, times(1)).getEmail();
    verify(mockUser, times(1)).getPassword();
    verify(mockUser, times(1)).getIsProf();
    verify(mockUser, times(1)).getIsAdmin();
    verify(mockUser, times(1)).getActive();
    verify(mockUser, times(1)).getHash();
    verify(mockUser, times(1)).getCreationDate();
  }
  
  @Test
  public void testUserManager() {
    UserManager mockUM = Mockito.mock(UserManager.class);
    when(mockUM.registerUser(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn((Integer) 0);
    assertEquals(mockUM.registerUser(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()), (Integer) 0);
    
    mockUM.activateUser(Mockito.anyString(), Mockito.anyString());
    
    when(mockUM.signInUser(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
    assertEquals(mockUM.signInUser(Mockito.anyString(), Mockito.anyString()), null);
    
    verify(mockUM, times(1)).registerUser(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    verify(mockUM, times(1)).activateUser(Mockito.anyString(), Mockito.anyString());
    verify(mockUM, times(1)).signInUser(Mockito.anyString(), Mockito.anyString());
    /*
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
    */
  }
  
  @Test
  public void testFile() {
    File mockFile = Mockito.mock(File.class);
    
    File testFile = new File("file1.pdf", "/test/", "fileDesc", "fileHash", 16, 1, "timeHash");
    testFile.setId(30);
    testFile.setBlurb("testBlurb");
    
    assertEquals(testFile.getId(), 30);
    assertEquals(testFile.getFileName(), "file1.pdf");
    assertEquals(testFile.getFilePath(), "/test/");
    assertEquals(testFile.getFileDesc(), "fileDesc");
    assertEquals(testFile.getFileHash(), "fileHash");
    assertEquals(testFile.getFileSize(), 16);
    assertEquals(testFile.getFileOwner(), 1);
    assertEquals(testFile.getTimeHash(), "timeHash");
    assertEquals(testFile.getBlurb(), "testBlurb");
    
    when(mockFile.getId()).thenReturn(20);
    when(mockFile.getFileName()).thenReturn("file4.txt");
    when(mockFile.getFilePath()).thenReturn("/test5/");
    when(mockFile.getFileDesc()).thenReturn("file description");
    when(mockFile.getFileHash()).thenReturn("123sad");
    when(mockFile.getFileSize()).thenReturn((long) 18);
    when(mockFile.getFileOwner()).thenReturn(5);
    when(mockFile.getTimeHash()).thenReturn("346sahd");
    when(mockFile.getBlurb()).thenReturn("little blurb of file");
    
    assertEquals(mockFile.getId(), 20);
    assertEquals(mockFile.getFileName(), "file4.txt");
    assertEquals(mockFile.getFilePath(), "/test5/");
    assertEquals(mockFile.getFileDesc(), "file description");
    assertEquals(mockFile.getFileHash(), "123sad");
    assertEquals(mockFile.getFileSize(), (long) 18);
    assertEquals(mockFile.getFileOwner(), 5);
    assertEquals(mockFile.getTimeHash(), "346sahd");
    assertEquals(mockFile.getBlurb(), "little blurb of file");
    
    verify(mockFile, times(1)).getId();    
    verify(mockFile, times(1)).getFileName(); 
    verify(mockFile, times(1)).getFilePath(); 
    verify(mockFile, times(1)).getFileDesc(); 
    verify(mockFile, times(1)).getFileHash(); 
    verify(mockFile, times(1)).getFileSize(); 
    verify(mockFile, times(1)).getFileOwner(); 
    verify(mockFile, times(1)).getTimeHash(); 
    verify(mockFile, times(1)).getBlurb(); 
  }
  
  @Test
  public void testFileManager() {
    FileManager mockFM = Mockito.mock(FileManager.class);
    
    when(mockFM.addFile(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyInt(), Mockito.anyString())).thenReturn((Integer) 0);
    assertEquals(mockFM.addFile(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyInt(), Mockito.anyString()), (Integer) 0);
    
    verify(mockFM, times(1)).addFile(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyInt(), Mockito.anyString());
  }
  
  
}
