package universitysearch;

import com.sun.jersey.core.header.FormDataContentDisposition;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.TopDocs;
import org.hibernate.SessionFactory;
import universitysearch.lucenesearch.IndexItem;
import universitysearch.lucenesearch.Indexer;
import universitysearch.lucenesearch.PDFSearcher;
import universitysearch.lucenesearch.Searcher;

import java.io.File;
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
        File file2 = new File(uploads, contentDispositionHeader.getFileName());
        try {
            long fileSize = Files.copy(fileInputStream, file2.toPath());
            String digestString = DigestUtils.md5Hex(file2.toString());
            String filePathHash = DigestUtils.md5Hex(System.getenv("OPENSHIFT_DATA_DIR"));
            String obfuscatedFilePath = filePathHash + "/" + file2.getName();

            FileManager fm = new FileManager();
            // Aquire DB connection
            SessionFactory factory = DBManager.getSessionFactory();
            fm.setFactory(factory);

            int res = fm.addFile(file2.getName(), obfuscatedFilePath, "uploadedFile", digestString, fileSize, 1);

            fileInputStream.close();
            initializeFileIndexing(file2);

            // creating an instance of the Searcher class to the query the index
            Searcher searcher = new Searcher(Paths.get(System.getenv("OPENSHIFT_DATA_DIR") + "/index"));
            TopDocs result = searcher.findByContent("Collaborated", 100);
//            for (result.)
//            searcher.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initializeFileIndexing(File file2) throws IOException {
        // Initiate index of the file
        IndexItem item = PDFSearcher.indexPDF(file2);
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
}

