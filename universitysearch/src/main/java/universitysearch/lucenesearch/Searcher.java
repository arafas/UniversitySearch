package universitysearch.lucenesearch;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;

public class Searcher {

    private IndexSearcher searcher;
    private QueryParser contentQueryParser;

    public Searcher(Path indexDir) throws IOException {
        // open the index directory to search
        Directory directory = FSDirectory.open(indexDir);
        IndexReader indexReader = DirectoryReader.open(directory);
        searcher = new IndexSearcher(indexReader);
        StandardAnalyzer analyzer = new StandardAnalyzer();

        // defining the query parser to search items by content field.
        contentQueryParser = new QueryParser(IndexItem.CONTENT, analyzer);
    }


    /**
     * This method is used to find the indexed items by the content.
     *
     * @param queryString - the query string to search for
     */
    public TopDocs findByContent(String queryString, int numOfResults) throws ParseException, IOException {
        // create query from the incoming query string.
        Query query = contentQueryParser.parse(queryString);
        // execute the query and get the results
        TopDocs queryResults = searcher.search(query, numOfResults);

        if (queryResults.totalHits > 0)
            return queryResults;
        else
            return null;

    }

//    public void close() throws IOException {
//        searcher.close();
//    }
}