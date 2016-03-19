package universitysearch;

import com.sun.jersey.core.header.ContentDisposition;
import com.sun.jersey.core.header.FormDataContentDisposition;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.SessionFactory;
import universitysearch.lucenesearch.IndexItem;
import universitysearch.lucenesearch.Indexer;
import universitysearch.lucenesearch.PDFSearcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by zubairbaig on 3/2/16.
 */
public class FileUpload {

    public void saveFile(InputStream fileInputStream, FormDataContentDisposition contentDispositionHeader) {
        File uploads = new File(System.getenv("OPENSHIFT_DATA_DIR"));
        String tHash = DigestUtils.md5Hex(String.valueOf(System.currentTimeMillis()));
        File file2 = new File(uploads, tHash + "-" + contentDispositionHeader.getFileName());
        try {
            long fileSize = Files.copy(fileInputStream, file2.toPath());
            FileInputStream fileHash = new FileInputStream(file2);
            String digestString = DigestUtils.md5Hex(fileHash);
            String filePathHash = DigestUtils.md5Hex(System.getenv("OPENSHIFT_DATA_DIR"));
            String filePath = file2.getAbsolutePath().
                substring(0,file2.getAbsolutePath().lastIndexOf(File.separator));
         // get the path of the file without the filename
            String obfuscatedFilePath = filePathHash + filePath.replace(System.getenv("OPENSHIFT_DATA_DIR"), "").replace("\\", "/") + "/";
            FileManager fm = new FileManager();
            // Aquire DB connection
            SessionFactory factory = DBManager.getSessionFactory();
            fm.setFactory(factory);
            String fileName = file2.getName();
            int res = fm.addFile(fileName, obfuscatedFilePath, "uploadedFile", digestString, fileSize, 1, tHash);

            fileInputStream.close();
            initializeFileIndexing(file2);

            // creating an instance of the Searcher class to the query the index
//            Searcher searcher = new Searcher(Paths.get(System.getenv("OPENSHIFT_DATA_DIR") + "/index"));
//            TopDocs result = searcher.findByContent("zubair", 100);
//            for (result.)
//            searcher.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
<<<<<<< HEAD
    
    public void saveFile(File fileInput, FileManager fm, String coursePath, int userID) {
      // Method used for saving files from CMD line
      String tHash = DigestUtils.md5Hex(String.valueOf(System.currentTimeMillis()));
      File uploads = new File(System.getenv("OPENSHIFT_DATA_DIR") + coursePath);
      File file2 = new File(uploads, tHash + "-" + fileInput.getName());
      FileInputStream fis = null;
      try {
          fis = new FileInputStream(fileInput);
          boolean success = (new File(file2.getAbsolutePath().substring(0,file2.getAbsolutePath().lastIndexOf(File.separator)))).mkdirs();
          System.out.println(success);
          Files.copy(fis, file2.toPath());
          long fileSize = fileInput.length();
          String filePath = fileInput.getAbsolutePath().
              substring(0,fileInput.getAbsolutePath().lastIndexOf(File.separator));
          // get the path of the file without the filename
          String fileName = fileInput.getName();
          FileInputStream fileHash = new FileInputStream(fileInput);;
          String fileCheck = DigestUtils.md5Hex(fileHash);
          // get the md5 of the content in the file
          String filePathHash = DigestUtils.md5Hex(System.getenv("OPENSHIFT_DATA_DIR"));
          String obfuscatedFilePath = filePathHash  + coursePath;
          // obscure the path to the data directory
           
          System.out.println(fileSize);
          System.out.println(filePath.replace("\\", "/"));
          System.out.println(fileName);
          System.out.println(fileCheck);
          System.out.println(obfuscatedFilePath);
          System.out.println(tHash);
          int res = fm.addFile(fileName, obfuscatedFilePath, "uploadedFile", fileCheck, fileSize, userID, tHash);
          fis.close();
          fileHash.close();
          fis.close();

      } catch (IOException e) {
          e.printStackTrace();
      }
  }
=======

    private void initializeFileIndexing(File file) throws IOException {
        // Initiate index of the file
        String ext = FilenameUtils.getExtension(file.getName());
        IndexItem item = null;
        if (ext.equals("pdf")) {
            item = PDFSearcher.indexPDF(file);
        } else if (ext.equals("txt")) {
            item = PDFSearcher.indexTxt(file);
        }

        Path path = Paths.get(System.getenv("OPENSHIFT_DATA_DIR") + "/index");
        Indexer indexer = new Indexer(path);
        indexer.index(item);
        indexer.close();
    }

//    //Print the results
//    private static void print(int result) {
//        if(result==1)
//            System.out.println("The document contains the search keyword");
//        else
//            System.out.println("The document does not contain the search keyword");
//
//    }
>>>>>>> develop
}

