package no.dblp.factories.factories;

import no.dblp.dbutils.DbFactory;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;

/**
 *
 * @author erlend321
 */
public class IndexFactory {
    
    private volatile static IndexManager indexManager;
    
    private IndexFactory() {
        
    }
    
    public static Index<Node> getPublicationIndex() {
        maybeInitIndexManager();
        return indexManager.forNodes("publications");
    }

    public static Index<Node> getAuthorIndex() {
        maybeInitIndexManager();
        return indexManager.forNodes("authors");
    }
    
    private static void maybeInitIndexManager() {
        if (indexManager == null) {
            synchronized (IndexFactory.class) {
                if (indexManager == null) {
                    GraphDatabaseService db = DbFactory.getInstance();
                    indexManager = db.index();
                }
            }
        }
    }
    
}
