package universitysearch;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import universitysearch.lucenesearch.Searcher;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by zubairbaig on 3/12/16.
 */
public class MasterSearch extends DBManager{
    private static SessionFactory factory;
    SearchResult searchResult;

    Session session;

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }



    public String searchCoursesAndFiles(String query) {
        session = factory.openSession();
        searchResult = new SearchResult();

        Searcher searcher = null;
        try {
            searcher = new Searcher(Paths.get(System.getenv("OPENSHIFT_DATA_DIR") + "/index"));
            TopDocs results = searcher.findByContent(query, 10);
            if (results !=  null) {
                ScoreDoc[] hits = results.scoreDocs;

                for (int i = 0; i < hits.length; i++) {
                    Document d = searcher.searcher.doc(hits[i].doc);
                    String fileTitle = d.get("title");

                    String sql = "SELECT * FROM files WHERE name = :fileTitle";

                    SQLQuery sqlQuery = session.createSQLQuery(sql);
                    sqlQuery.setParameter("fileTitle", fileTitle);
                    sqlQuery.addEntity(File.class);
                    File file = (File) sqlQuery.uniqueResult();
                    searchResult.addFile(file);
                }
            }

            query = "%" + query + "%";
            String sql = "SELECT * FROM courses WHERE course_code LIKE :query";

            SQLQuery sqlQuery = session.createSQLQuery(sql);
            sqlQuery.addEntity(Course.class);
            sqlQuery.setParameter("query", query);
            List<Course> sqlResults = (List<Course>) sqlQuery.list();
            for (Course course : sqlResults) {
                searchResult.addCourse(course);
            }

            return getJsonResultObj(searchResult);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getJsonResultObj(SearchResult searchResult) {
        String res = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            res = mapper.writeValueAsString(searchResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
