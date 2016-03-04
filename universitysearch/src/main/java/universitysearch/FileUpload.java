package universitysearch;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by zubairbaig on 3/2/16.
 */
@Path("/fileUpload")
public class FileUpload {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public UploadedFile processFileUpload(@FormDataParam("file") InputStream fileInputStream,
                                          @FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {

        Map<String, String> map = contentDispositionHeader.getParameters();
        UploadedFile file = new UploadedFile();
        file.setFileName("testFileName");
        saveFile(fileInputStream, contentDispositionHeader);


        return file;
    }

    private void saveFile(InputStream fileInputStream, FormDataContentDisposition contentDispositionHeader) {
        File uploads = new File(System.getenv("OPENSHIFT_DATA_DIR"));
        File file2 = new File(uploads, contentDispositionHeader.getFileName());
        try {
            long fileSize = Files.copy(fileInputStream, file2.toPath());


            String digestString = DigestUtils.sha1Hex(fileInputStream);

            PreparedStatement stmt;
            //STEP 4: Execute a query
            if (DBManager.conn == null) {
                DBManager.connectDB();
            }

            stmt = DBManager.conn.prepareStatement("INSERT into files (name, path, size, description, hash, owner_id) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, contentDispositionHeader.getFileName());
            stmt.setString(2, System.getenv("OPENSHIFT_DATA_DIR"));
            stmt.setLong(3, fileSize);
            stmt.setString(4, "filefile");
            stmt.setString(5, digestString);
            stmt.setInt(6, 1);

            int resp = stmt.executeUpdate();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

