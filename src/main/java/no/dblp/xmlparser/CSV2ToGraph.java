package no.dblp.xmlparser;

import java.io.File;
import no.dblp.dbutils.DbFactory;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.QueryExecutionException;
import org.neo4j.graphdb.Transaction;

/**
 *
 * @author erlend321
 */
public class CSV2ToGraph {
    
    public static void main(String[] args) {
        
        GraphDatabaseService DB = DbFactory.getInstance();
        
        try (Transaction tx = DB.beginTx()) {
            DB.execute("CREATE INDEX ON :Author(name)");
            DB.execute("CREATE INDEX ON :Publication(title)");
            tx.success();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        try {
            
            File folder = new File("/Users/erlend321/Documents/csv3");
            
            System.out.println("Going through " + folder.listFiles().length + " files.");
            int fileNum = 0;
            
            for (File file : folder.listFiles()) {
                if (file.toString().contains("DS")) {
                    continue;
                }
                
                ++fileNum;
                System.out.println("Importing file nubmer " + fileNum);
                
                StringBuilder cypher = new StringBuilder();
                cypher.append("USING PERIODIC COMMIT")
                        .append(" LOAD CSV WITH HEADERS FROM '")
                        .append("file://")
                        .append(file.toString())
                        .append("'")
                        .append(" AS csvLine")
                        .append(" FIELDTERMINATOR '*'")
                        .append(" MERGE (author:Author {name : csvLine.author})")
                        .append(" MERGE (publication:Publication {title : csvLine.title})")
                        .append(" CREATE (author)-[:IS_AUTHOR_OF]->(publication)");
                
                try {
                    DB.execute(cypher.toString());
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        DB.shutdown();
        
    }
    
}