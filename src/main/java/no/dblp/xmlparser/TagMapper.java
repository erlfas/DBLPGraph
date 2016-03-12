package no.dblp.xmlparser;

public class TagMapper {

    public static boolean isPublication(String tag) {
        switch (tag) {
            case "article":
            case "inproceedings":
            case "proceedings":
            case "book":
            case "incollection":
            case "phdthesis":
            case "mastersthesis":
//            case "www":
                return true;
            default:
                return false;
        }
    }
    
    public static boolean isTitle(String tag) {
        return "title".equals(tag);
    }
    
    public static boolean isAuthor(String tag) {
        return "author".equals(tag);
    }
    
    public static boolean isMarkup(String tag) {
        switch (tag) {
            case "ref":
            case "sup":
            case "sub":
            case "i":
            case "tt":
                return true;
            default:
                return false;
        }
    }
    
}
