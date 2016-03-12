package no.dblp.xmlparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLAnalyzer {
    
    public static String getKey(String line) {
        Pattern p = Pattern.compile("key=\"[a-zA-Z_0-9/]+\"");
        Matcher m = p.matcher(line);
        if (m.find()) {
            return m.group().replace("key=", "").replace("\"", "");
        }
        
        return "";
    }
    
    public static String getTitle(String line) {
        return line.replace("<title>", "").replace("</title>", "");
    }
    
    public static String getAuthor(String line) {
        return line.replace("<author>", "").replace("</author>", "");
    }
    
    public static boolean isAuthorElement(String line) {
        return line.contains("<author>");
    }
    
    public static boolean isTitleElement(String line) {
        return line.contains("<title>");
    }

    public static boolean isStartOfPublication(String line) {
        if (line.contains("<article")) {
            return true;
        } else if (line.contains("<inproceeding")) {
            return true;
        } else if (line.contains("<proceedings")) {
            return true;
        } else if (line.contains("<book")) {
            return true;
        } else if (line.contains("<incollection")) {
            return true;
        } else if (line.contains("<phdthesis")) {
            return true;
        } else if (line.contains("<mastersthesis")) {
            return true;
        }
        
        return false;
    }
    
    public static boolean isEndOfPublication(String line) {
        if (line.contains("</article")) {
            return true;
        } else if (line.contains("</inproceeding")) {
            return true;
        } else if (line.contains("</proceedings")) {
            return true;
        } else if (line.contains("</book")) {
            return true;
        } else if (line.contains("</incollection")) {
            return true;
        } else if (line.contains("</phdthesis")) {
            return true;
        } else if (line.contains("</mastersthesis")) {
            return true;
        }
        
        return false;
    }
    
}
