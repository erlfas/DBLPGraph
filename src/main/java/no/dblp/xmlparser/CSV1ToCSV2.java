package no.dblp.xmlparser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.apache.commons.lang3.StringUtils;

/**
 * Converts CSV files of the following format:
 *
 * key,title,authors 
 * journals/cacm/Gentry10;Ola Nordman,Knut Knutsen;Some title
 *
 * to CSV files of the following format:
 *
 * title*author
 * Some title*Ola Nordmann
 * Some title*Knut Knutsen
 *
 * @author erlend321
 */
public class CSV1ToCSV2 {

    public static void main(String[] args) {
        File folder = new File("/Users/erlend321/Documents/csv");

        try {
            StringBuilder csvLines = new StringBuilder();
            long titles = 0;
            long titlesTotal = 0;
            int fileNum = 0;
            int currentFile = 0;
            
            System.out.println(String.format("Going through %d files", folder.listFiles().length));

            for (File file : folder.listFiles()) {
                ++currentFile;
                System.out.println("Handling file " + currentFile);
                csvLines.append("title*author\n");
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";");
                    if (parts.length < 3) {
                        System.out.println("Less than 3 parts in: " + line);
                        continue;
                    }
                    
                    String key = parts[0];
                    String authors = parts[1];
                    String[] authorParts = authors.split(",");
                    String title = parts[2];
                    
                    if (StringUtils.isBlank(title)) {
                        continue;
                    }
                    
                    if (title.contains("\"")) {
                        continue;
                    }
                    
                    ++titles;
                    ++titlesTotal;
                    for (String author : authorParts) {
                        if (StringUtils.isBlank(author)) {
                            continue;
                        }
                        
                        if (Character.isLowerCase(author.charAt(0))) {
                            continue;
                        }
                        
                        csvLines.append(title).append("*").append(author).append("\n");
                    }

                    if (titles >= 5000) {
                        System.out.println("# titles in total = " + titlesTotal + ", writing a new file");
                        titles = 0;
                        ++fileNum;
                        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/erlend321/Documents/csv3/dblpcsv_" + fileNum + ".csv"), "utf-8"))) {
                            writer.write(csvLines.toString());
                        }
                        csvLines = new StringBuilder();
                        csvLines.append("title*author\n");
                    }
                }
            }

            ++fileNum;
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/erlend321/Documents/csv3/dblpcsv_" + fileNum + ".csv"), "utf-8"))) {
                writer.write(csvLines.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
