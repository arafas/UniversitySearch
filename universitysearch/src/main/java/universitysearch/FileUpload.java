package universitysearch;

import com.sun.jersey.core.header.FormDataContentDisposition;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.SessionFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

