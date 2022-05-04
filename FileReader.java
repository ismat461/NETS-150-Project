package HelperClasses;

import java.io.*;
import java.util.*;
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
     * Parses the input line of song data to retrieve all 15 song
     * parameters in the following order:
     * Song, Name, Artist, Featured Artists, Release Date, Genre,
     * Popularity, Danceability, Energy, Loudness, Speechiness,
     * Acousticness, Liveness, Tempo, Valence, and Duration (ms)
     *
     * @return a set of strings containing the parameters
     */
    public ArrayList<String> getParameter(String line) {
        ArrayList<String> parameters = new ArrayList<>();
        String lineRegex = "^\\W?([A-zÀ-ÿ\\s\\d'$/&()!:%@#.&-]+)\\W?[,]" +
                "\\W?([A-zÀ-ÿ\\s,!\\d'$/()-]+)\\W?[,]\\W?([\\sA-zÀ-ÿ-\\d,$']*)" +
                "\\W?[,](\\d+)[,](.*)[,](.*)[,](.*)[,](.*)[,](.*)[,](.*)[,](.*)[,]" +
                "(.*)[,](.*)[,](.*)[,](.*)";
        Matcher lineMatcher = Pattern.compile(lineRegex).matcher(line);
        if (lineMatcher.find()) {
            for (int i = 1; i <= lineMatcher.groupCount(); i++) {
                parameters.add(lineMatcher.group(i).toLowerCase());
            }
        } else {
            System.out.println("This song: " + line + " could not be found.");
        }
        return parameters;
    }

}

