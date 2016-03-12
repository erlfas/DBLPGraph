package no.dblp.relationships;

import org.neo4j.graphdb.RelationshipType;

/**
 *
 * @author erlend321
 */
public enum Relationships implements RelationshipType {
    
    IS_COAUTHOR_OF,
    IS_AUTHOR_OF;
    
}
