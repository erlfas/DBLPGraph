package no.dblp.xmlparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erlend321
 */
public class DBLPXMLAnalyzer {

    public static void main(String[] args) {

        try {

            File folder = new File("/Users/erlend321/Documents/csv3");
            long num = 0;
            int fileNum = 0;
            for (File file : folder.listFiles()) {
                ++fileNum;
                System.out.println("Searching file " + fileNum);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("Jan Arne Telle")) {
                        ++num;
                    }
                }
            }

            System.out.println("Found " + num + " publications by Jan Arne Telle.");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBLPXMLAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBLPXMLAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
