package universitysearch.lucenesearch;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.IOException;

/**
 * Created by zubairbaig on 3/11/16.
 */
public class PDFSearcher {
    private static final String INDEX_DIR = System.getenv("OPENSHIFT_DATA_DIR" + "/index");

    public static IndexItem indexPDF(java.io.File file) throws IOException {
        PDDocument document = PDDocument.load(file);
        String content = new PDFTextStripper().getText(document);
        document.close();
        return new IndexItem((long)file.getName().hashCode(), file.getName(), content);
    }
}
