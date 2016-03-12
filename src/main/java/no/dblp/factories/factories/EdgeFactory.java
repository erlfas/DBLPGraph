package no.dblp.factories.factories;

import java.util.HashMap;
import java.util.Map;
import no.dblp.dbutils.DbFactory;
import no.dblp.factories.model.Author;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;

/**
 *
 * @author erlend321
 */
public class EdgeFactory {
    
    private static final GraphDatabaseService DB = DbFactory.getInstance();
    
    public static Relationship getOrCreateUniqueCoauthorRelationship(String authorA, String authorB) {
        try (Transaction tx = DB.beginTx()) {
            String query =  "MATCH (a:Author {name: {na}}),(b:Author {name: {nb}})"
                            + " MERGE (a)-[r:IS_COAUTHOR_OF]-(b)"
                            + " RETURN r";
            
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("na", authorA);
            parameters.put("nb", authorB);
            
            ResourceIterator<Relationship> resultIterator = DB.execute(query, parameters).columnAs("r");
            Relationship result = resultIterator.next();
            tx.success();
            return result;
        }
    }

    public static Relationship getOrCreateUniqueCoauthorRelationship(Author a, Author b) {
        try (Transaction tx = DB.beginTx()) {
            String query =  "MATCH (a:Author {name: {na}}),(b:Author {name: {nb}})"
                            + " MERGE (a)-[r:IS_COAUTHOR_OF]-(b)"
                            + " RETURN r";
            
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("na", a.getName());
            parameters.put("nb", b.getName());
            
            ResourceIterator<Relationship> resultIterator = DB.execute(query, parameters).columnAs("r");
            Relationship result = resultIterator.next();
            tx.success();
            return result;
        }
    }
    
}
