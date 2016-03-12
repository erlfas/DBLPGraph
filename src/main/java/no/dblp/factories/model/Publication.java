package no.dblp.factories.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Publication {

    private final String title;
    private final List<String> authors;
    
    public Publication(String title) {
        this.title = title;
        this.authors = new ArrayList<>();
    }
    
    public List<String> getAuthors() {
        return authors.stream().collect(Collectors.toList());
    }
    
    public void addAuthor(String author) {
        this.authors.add(author);
    }

    public String getTitle() {
        return title;
    }
    
}
