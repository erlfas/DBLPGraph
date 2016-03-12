package no.dblp.factories.factories;

import java.util.HashMap;
import java.util.Map;
import no.dblp.factories.model.AuthorInfo;
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
public class NodeFactory {

    private static final GraphDatabaseService DB = DbFactory.getInstance();
    
    public static Node getOrCreateUniqueAuthorNode(AuthorInfo info) {
        try (Transaction tx = DB.beginTx()) {
            String queryString = "MERGE (author:Author {name: {name}}) RETURN author";
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("name", info.getAuthorName());
            ResourceIterator<Node> resultIterator = DB.execute(queryString, parameters).columnAs("author");
            Node result = resultIterator.next();
            tx.success();
            return result;
        }
    }

    public static Author getOrCreateUniqueAuthor(AuthorInfo info) {
        try (Transaction tx = DB.beginTx()) {
            String queryString = "MERGE (author:Author {name: {name}}) RETURN author";
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("name", info.getAuthorName());
            ResourceIterator<Node> resultIterator = DB.execute(queryString, parameters).columnAs("author");
            Node result = resultIterator.next();
            Long id = result.getId();
            String name = result.getProperty("name").toString();
            tx.success();
            return new Author(id, name);
        }
    }

}
