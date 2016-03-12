package no.dblp.searcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import no.dblp.dbutils.DbFactory;
import no.dblp.factories.model.Author;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;

/**
 *
 * @author erlend321
 */
public class NodeFinder {
    
    private static final GraphDatabaseService DB = DbFactory.getInstance();
    
    public static List<Author> getAllAuthors() {
        List<Author> results = new ArrayList<>();
        try (Transaction tx = DB.beginTx()) {
            String query = "MATCH (author:Author) RETURN author";
            ResourceIterator<Node> resultIterator = DB.execute(query).columnAs("author");
            while (resultIterator.hasNext()) {
                Node node = resultIterator.next();
                String nameReturned = node.getProperty("name").toString();
                Long id = node.getId();
                results.add(new Author(id, nameReturned));
            }
            tx.success();
            return results;
        }
    }
    
    public static Optional<Author> getAuthor(String name) {
        try (Transaction tx = DB.beginTx()) {
            String query = "MATCH (author:Author {name: {name}}) RETURN author";
            Map<String,Object> parameters = new HashMap<>();
            parameters.put("name", name);
            ResourceIterator<Node> resultIterator = DB.execute(query, parameters).columnAs("author");
            
            Optional<Author> author = Optional.empty();
            if (resultIterator.hasNext()) {
                Node result = resultIterator.next();
                String nameReturned = result.getProperty("name").toString();
                Long id = result.getId();
                author = Optional.of(new Author(id,nameReturned));
            }
            
            tx.success();
            return author;
        }
    }
    
}
