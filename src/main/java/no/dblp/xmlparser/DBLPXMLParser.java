package no.dblp.xmlparser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import no.dblp.dbutils.DbFactory;
import no.dblp.factories.factories.EdgeFactory;
import no.dblp.factories.factories.NodeFactory;
import no.dblp.factories.model.Author;
import no.dblp.factories.model.AuthorInfo;
import no.dblp.factories.model.Publication;
import no.dblp.searcher.NodeFinder;
import org.neo4j.graphdb.GraphDatabaseService;

/**
 * Constructs a coauthor graph while parsing the DBLP XML file.
 * 
 * The method is slow and therefore deprecated.
 * 
 * @author erlend321
 */
@Deprecated
public class DBLPXMLParser {

    public static void main(String[] args) {
        List<Publication> publications = new ArrayList<>();
        long numPublications = 0;

        try {
            GraphDatabaseService db = DbFactory.getInstance();

            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader streamReader = factory.createXMLStreamReader(new FileReader("/Users/erlend321/Downloads/dblp.xml"));

            boolean insidePublication = false;
            boolean insideTitle = false;
            boolean insideAuthor = false;
            StringBuilder title = new StringBuilder();
            StringBuilder author = new StringBuilder();
            Publication publication = new Publication("");
            

            while (streamReader.hasNext()) {
                int event = streamReader.next();
                if (event == XMLEvent.START_ELEMENT) {
                    String tag = streamReader.getLocalName();
                    if (TagMapper.isPublication(tag)) {
                        insidePublication = true;
                    } else if (TagMapper.isTitle(tag)) {
                        insideTitle = true;
                    } else if (TagMapper.isAuthor(tag)) {
                        insideAuthor = true;
                    } else if (TagMapper.isMarkup(tag)) {
                        if (insideTitle) {
                            title.append("<").append(tag).append(">");
                        }
                    }
                } else if (event == XMLEvent.END_ELEMENT) {
                    String tag = streamReader.getLocalName();
                    if (TagMapper.isPublication(tag)) {
                        insidePublication = false;
                        numPublications++;
                        if (publications.size() >= 500) {
                            System.out.println("# publications = " + numPublications);
                            publications.stream()
                                    .map(p -> p.getAuthors())
                                    .forEach(authors -> {
                                        authors.stream()
                                                .forEach(a -> NodeFactory.getOrCreateUniqueAuthor(new AuthorInfo(a)));
                                    });

                            publications.stream()
                                    .map(p -> p.getAuthors())
                                    .forEach(authorList -> {
                                        for (int i = 0; i < authorList.size(); ++i) {
                                            for (int j = i + 1; j < authorList.size(); ++j) {
                                                EdgeFactory.getOrCreateUniqueCoauthorRelationship(authorList.get(i), authorList.get(j));
                                            }
                                        }
                                    });

                            publications.clear();
                        }
                        publications.add(publication);
                    } else if (TagMapper.isTitle(tag)) {
                        insideTitle = false;
                        publication = new Publication(title.toString());
                        title = new StringBuilder();
                    } else if (TagMapper.isAuthor(tag)) {
                        insideAuthor = false;
                        publication.addAuthor(author.toString());
                        author = new StringBuilder();
                    } else if (TagMapper.isMarkup(tag)) {
                        if (insideTitle) {
                            title.append("</").append(tag).append(">");
                        }
                    }
                } else if (event == XMLEvent.CHARACTERS) {
                    if (insideTitle) {
                        title.append(streamReader.getText());
                    } else if (insideAuthor) {
                        author.append(streamReader.getText());
                    }
                }
            }

        } catch (XMLStreamException | FileNotFoundException ex) {

        }
        
        System.out.println("# publications = " + numPublications);

        publications.stream()
                .map(p -> p.getAuthors())
                .forEach(authors -> {
                    authors.stream()
                            .forEach(a -> NodeFactory.getOrCreateUniqueAuthor(new AuthorInfo(a)));
                });

        publications.stream()
                .map(p -> p.getAuthors())
                .forEach(authorList -> {
                    for (int i = 0; i < authorList.size(); ++i) {
                        for (int j = i + 1; j < authorList.size(); ++j) {
                            EdgeFactory.getOrCreateUniqueCoauthorRelationship(authorList.get(i), authorList.get(j));
                        }
                    }
                });

        publications.clear();
        
        List<Author> alleNoder = NodeFinder.getAllAuthors();
        
        System.out.println("# authors = " + alleNoder.size());
    }

}
