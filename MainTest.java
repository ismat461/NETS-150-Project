package Recommendations;
import HelperClasses.FileReader;

import java.io.*;
import java.util.*;


public class MainTest {
    public static void main(String[] args) {
        // Reading in the data from the txt file
        FileReader fr = new FileReader("config/data.txt");
        List<String> lines = new LinkedList<>();
        try {
            lines = fr.readLines();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Creating the map to store song names and their corresponding parameters
        Map<String, ArrayList<String>> songMap = new HashMap<>();
        for (String line : lines) {
            ArrayList<String> parameters = fr.getParameter(line);
            String songName = parameters.get(0);
            parameters.remove(0);
            songMap.put(songName, parameters);
//            System.out.println(songMap.get(songName));
        }
//        System.out.println(songMap.keySet());

        Recommender recommender = new Recommender(songMap);

        /*
         * Randomly chooses 10 songs from the list
         */
        ArrayList<String> initial10Songs = recommender.initial10Songs();
        ArrayList<String> initial10Artists = recommender.getArtists(initial10Songs);
        System.out.println("Here is Your List of 10 Songs: " + initial10Songs);
        System.out.println("Here is the List of the Corresponding Artists: " + initial10Artists);

        /*
         * The following code is for the interactive terminal.
         * The program will prompt user to choose one of the randomly
         * generated song names, and will only continue if the input
         * matches one of the given songs.
         */
        System.out.println();
        System.out.println("Please Enter Your Preferred Song Choice in " +
                "the Next Line in the Same Way it is Provided Above. Only the Song Name is Needed.");

        Scanner sc = new Scanner(System.in); // creates a scanner to read in inputs
        String songInput = sc.nextLine();

        boolean invalidSong = true;

        do {
            if (!initial10Songs.contains(songInput)) {
                System.out.println("You Entered [ " + songInput +
                        " ] Which Was Not One of the Listed Songs. Please Try Again.");
                songInput = sc.nextLine();
            } else {
                invalidSong = false;
                System.out.println("You Entered [ " + songInput + " ]. Your Recommendation Will Be Provided Shortly. " );
            }
        } while (invalidSong);

        /*
         * The program will begin its recommendation system.
         */
    }
}

