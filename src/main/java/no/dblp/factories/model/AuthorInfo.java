package no.dblp.factories.model;

import no.dblp.labels.Labels;

/**
 *
 * @author erlend321
 */
public final class AuthorInfo extends NodeInfo {
    
    public AuthorInfo(String name) {
        super("name", name);
    }
    
    public String getAuthorName() {
        return getIndexValue();
    }
    
    @Override
    public Labels[] getLabels() {
        return new Labels[] { Labels.AUTHOR };
    }
}
