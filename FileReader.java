package HelperClasses;

import java.io.*;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileReader {

    private Scanner scanner;
    private List<String> lines;

    /**
     * Constructor creates scanner and file objects
     * @param filePath the path to the input file
     */
    public FileReader(String filePath) {
        File file = new File(filePath);
        try {
            this.scanner = new Scanner(file);
        } catch (Exception e) {
            System.out.println(e.getMessage()); //get exception message
        }
    }

    /*********
     * METHODS
     **********/

    /**
     * Reads all lines of an input file
     * @return List of lines in input file as strings
     * @throws IOException
     */
    public List<String> readLines() throws IOException {
        this.lines = new LinkedList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }
        this.scanner.close();
        return lines;
    }

    /**
     * Parses the input line to retrieve first node in sequence
     * @return the integer value of the node
     */
    public void getParameter(String line) {
        String lineRegex = "^\\W?([A-zÀ-ÿ\\s\\d'$/&()!:%@#]+)" +
                "\\W?[,]\\W?([A-zÀ-ÿ\\s,!\\d'$/()-]+)\\W?[,]\\W?([\\sA-zÀ-ÿ\\d,-]*)" +
                "\\W?[,](\\d+)[,](.*)[,](.*)[,](.*)[,](.*)[,](.*)[,](.*)[,](.*)[,](.*)" +
                "[,](.*)[,](.*)[,](.*)";
        Matcher lineMatcher = Pattern.compile(lineRegex).matcher(line);
        if (lineMatcher.find()) {
            for (int i = 1; i <= lineMatcher.groupCount(); i++) {
                System.out.println(lineMatcher.group(i));
            }
            System.out.println();
        } else {
            System.out.println("This song: " + line + " could not be found.");
        }
    }

    /**
     * Parses the input line to retrieve second node in sequence
     * @return the integer value of the node
     */
    public int getSecondNode(String line) {
        Scanner s = new Scanner(line).useDelimiter(" ");
        int first = s.nextInt();
        return s.nextInt();
    }
}

