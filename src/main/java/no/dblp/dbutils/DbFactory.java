package no.dblp.dbutils;

import java.io.File;
import org.neo4j.graphdb.ConstraintViolationException;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

/**
 *
 * @author erlend321
 */
public class DbFactory {

    private volatile static GraphDatabaseService singleton;

    private DbFactory() {

    }

    public static GraphDatabaseService getInstance() {
        if (singleton == null) {
            synchronized (DbFactory.class) {
                if (singleton == null) {
                    File storeDir = new File("/Users/erlend321/Documents/Neo4j/dblp6.graphdb");

                    singleton = new GraphDatabaseFactory()
                            .newEmbeddedDatabaseBuilder(storeDir)
                            .newGraphDatabase();

                    // only needed the first time (creation of db)
                    try (Transaction tx = singleton.beginTx()) {
                        singleton.schema()
                                .constraintFor(DynamicLabel.label("Author"))
                                .assertPropertyIsUnique("name")
                                .create();

                        tx.success();
                    } catch (ConstraintViolationException e) {

                    }
                    
                    try (Transaction tx = singleton.beginTx()) {
                        singleton.schema()
                                .constraintFor(DynamicLabel.label("Publication"))
                                .assertPropertyIsUnique("title")
                                .create();
                        
                        tx.success();
                    } catch (ConstraintViolationException e) {
                        
                    }

                    registerShutdownHook(singleton);
                }
            }
        }
        return singleton;
    }

    private static void registerShutdownHook(final GraphDatabaseService service) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                service.shutdown();
            }
        });
    }

}
