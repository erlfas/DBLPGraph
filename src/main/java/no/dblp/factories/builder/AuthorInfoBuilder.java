package no.dblp.factories.builder;

import no.dblp.factories.model.AuthorInfo;

/**
 *
 * @author erlend321
 */
public class AuthorInfoBuilder {

    private AuthorInfo author;
    
    public AuthorInfoBuilder(String name) {
        author = new AuthorInfo(name);
    }
    
    public AuthorInfo build() {
        return author;
    }
}
