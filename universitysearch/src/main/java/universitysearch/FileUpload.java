package universitysearch;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zubairbaig on 3/2/16.
 */
@Path("/fileUpload")
public class FileUpload {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public File processFileUpload(@FormDataParam("file") InputStream fileInputStream,
                                  @FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {

        String filePath = contentDispositionHeader.getFileName();
        long filesize = contentDispositionHeader.getSize();
        Map<String, String> map =  contentDispositionHeader.getParameters();
//        String output = "Jersey say : " + msg;
//        DBManager dbManager = new DBManager();
////        Connection conn = dbManager.connectDB();
        File file = new File();
        file.setFileName("testFileName");

        return file;
    }

}