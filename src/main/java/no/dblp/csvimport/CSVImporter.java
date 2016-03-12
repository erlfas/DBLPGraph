package no.dblp.csvimport;

import java.io.File;
import no.dblp.dbutils.DbFactory;
import org.neo4j.graphdb.GraphDatabaseService;

public class CSVImporter {
    
    private static GraphDatabaseService db = DbFactory.getInstance();
    
    public static void importCSVFile(File csvFile) {
        StringBuilder cypher = new StringBuilder();
        cypher.append("LOAD CSV WITH HEADERS FROM \"")
                .append(csvFile.toString())
                .append("\" AS csvLine")
                .append("MERGE (author:Author {name: csvLine.author})");
        String s = cypher.toString();
    }
    
}
