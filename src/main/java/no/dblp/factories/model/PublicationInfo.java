package no.dblp.factories.model;

import no.dblp.labels.Labels;

/**
 *
 * @author erlend321
 */
public class PublicationInfo extends NodeInfo {
    
    public PublicationInfo(String title) {
        super("title", title);
    }
    
    public String getTitle() {
        return getIndexValue();
    }
    
    @Override
    public Labels[] getLabels() {
        return new Labels[] { Labels.PUBLICATION };
    }
}
