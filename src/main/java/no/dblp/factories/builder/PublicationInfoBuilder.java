package no.dblp.factories.builder;

import no.dblp.factories.model.PublicationInfo;
import no.dblp.labels.Labels;

/**
 *
 * @author erlend321
 */
public class PublicationInfoBuilder {

    private final PublicationInfo publication;
    
    public PublicationInfoBuilder(String category, String title, String key, String mdate) {
        publication = new PublicationInfo(title);
        publication.addLabels(Labels.PUBLICATION);
        publication.addProperty("category", category);
        publication.addProperty("title", title);
        publication.addProperty("key", key);
        publication.addProperty("mate", mdate);
    }
    
    public PublicationInfoBuilder addAuthor(String name) {
        publication.addProperty("author", name);
        return this;
    }
    
    public PublicationInfoBuilder setPages(String pages) {
        publication.addProperty("pages", pages);
        return this;
    }
    
    public PublicationInfoBuilder setYear(String year) {
        publication.addProperty("year", year);
        return this;
    }
    
    public PublicationInfoBuilder setVolume(String volume) {
        publication.addProperty("volume", volume);
        return this;
    }
    
    public PublicationInfoBuilder setJournal(String journal) {
        publication.addProperty("journal", journal);
        return this;
    }
    
    public PublicationInfoBuilder setNumber(String number) {
        publication.addProperty("number", number);
        return this;
    }
    
    public PublicationInfoBuilder setEe(String ee) {
        publication.addProperty("ee", ee);
        return this;
    }
    
    public PublicationInfoBuilder setUrl(String url) {
        publication.addProperty("url", url);
        return this;
    }
    
    public PublicationInfoBuilder setCrossref(String crossref) {
        publication.addProperty("crossref", crossref);
        return this;
    }
    
    public PublicationInfoBuilder setBookTitle(String bookTitle) {
        publication.addProperty("booktitle", bookTitle);
        return this;
    }
    
    public PublicationInfoBuilder setKey(String key) {
        publication.addProperty("key", key);
        return this;
    }
    
    public PublicationInfoBuilder setMdate(String mdate) {
        publication.addProperty("mdate", mdate);
        return this;
    }
    
    public PublicationInfo build() {
        return publication;
    }
}
