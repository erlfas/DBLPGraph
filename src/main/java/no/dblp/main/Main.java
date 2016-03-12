package no.dblp.main;

import java.util.List;
import java.util.Optional;
import no.dblp.dbutils.DbFactory;
import no.dblp.factories.factories.EdgeFactory;
import no.dblp.factories.factories.NodeFactory;
import no.dblp.factories.model.Author;
import no.dblp.factories.model.AuthorInfo;
import no.dblp.searcher.NodeFinder;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Relationship;

/**
 *
 * @author erlend321
 */
public class Main {
    
    public static void main(String[] args) {
        GraphDatabaseService db = DbFactory.getInstance();

        Author knut = NodeFactory.getOrCreateUniqueAuthor(new AuthorInfo("Knut"));
        Author olav = NodeFactory.getOrCreateUniqueAuthor(new AuthorInfo("Olav"));
        Author ole = NodeFactory.getOrCreateUniqueAuthor(new AuthorInfo("Ole"));
        Author rune = NodeFactory.getOrCreateUniqueAuthor(new AuthorInfo("Rune"));
        
        Relationship knutToOlav = EdgeFactory.getOrCreateUniqueCoauthorRelationship(knut, olav);
        
        Optional<Author> knut2 = NodeFinder.getAuthor("Knut");
        if (knut2.isPresent()) {
            System.out.println("Knut exists");
        } else {
            System.out.println("Knut does not exist");
        }
        
        Optional<Author> rune2 = NodeFinder.getAuthor("Rune");
        if (rune2.isPresent()) {
            System.out.println("Rune exists");
        } else {
            System.out.println("Rune does not exist");
        }
        
        List<Author> alleNoder = NodeFinder.getAllAuthors();
        System.out.println("Antall noder: " + alleNoder.size());
        
        db.shutdown();
    }
    
}
