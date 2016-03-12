package no.dblp.xmlparser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Converts the huge DBLP XML file to a number of CSV files
 * on the following format:
 * 
 * key,title,authors
 * journals/cacm/Gentry10;Ola Nordman,Knut Knutsen;Some title
 * 
 * @author erlend321
 */
public class DBLPXMLToCSV1 {

    public static void main(String[] args) {

        BufferedReader reader = null;

        // key,title,authors
        // journals/cacm/Gentry10;Ola Nordman,Knut Knutsen;Some title
        try {
            reader = new BufferedReader(new FileReader("/Users/erlend321/Downloads/dblp.xml"));

            StringBuilder csvLines = new StringBuilder();
            int numAuthors = 0;
            long numPublications = 0;
            int fileNum = 0;

            String line = null;
            while ((line = reader.readLine()) != null) {
                if (XMLAnalyzer.isStartOfPublication(line)) {
                    ++numPublications;
                    csvLines.append(XMLAnalyzer.getKey(line)).append(";");
                } else if (XMLAnalyzer.isEndOfPublication(line)) {
                    csvLines.append("\n");
                    if (numPublications >= 100000) {
                        numPublications = 0;
                        System.out.println("# publications = " + numPublications);
                        ++fileNum;
                        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/erlend321/Documents/csv/dblpcsv_" + fileNum + ".txt"), "utf-8"))) {
                            writer.write(csvLines.toString());
                            csvLines = new StringBuilder();
                        }
                    }
                    numAuthors = 0;
                } else if (XMLAnalyzer.isTitleElement(line)) {
                    csvLines.append(";").append(XMLAnalyzer.getTitle(line));
                } else if (XMLAnalyzer.isAuthorElement(line)) {
                    ++numAuthors;
                    if (numAuthors == 1) {
                        csvLines.append(XMLAnalyzer.getAuthor(line));
                    } else {
                        csvLines.append(",").append(XMLAnalyzer.getAuthor(line));
                    }
                }
            }

            System.out.println("# publications = " + numPublications);
            ++fileNum;
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/erlend321/Documents/csv/dblpcsv_" + fileNum + ".txt"), "utf-8"))) {
                writer.write(csvLines.toString());
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBLPXMLToCSV1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBLPXMLToCSV1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
