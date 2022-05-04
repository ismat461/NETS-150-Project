package HelperClasses;

import java.io.*;
import java.util.*;

public class MainTest {
    static ArrayList<String> initial10Songs;
    static ArrayList<String> twoSongNames = new ArrayList<>();

    public static void main(String[] args) {
        Recommender recommender = new Recommender(createSongNameToAudioFeatureMap());
        initial10Songs = recommender.getInitSongs();
        interactiveTerminal();
        recommender.getRecommendation(twoSongNames);
    }

    public static Map<String, ArrayList<String>> createSongNameToAudioFeatureMap(){
        // Reading in the data from the txt file
        FileReader fr = new FileReader("data.txt");
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
        }
        return songMap;
    }

    /*
     * The following code is for the interactive terminal.
     * The program will prompt user to choose one of the randomly
     * generated song names, and will only continue if the input
     * matches one of the given songs.
     */
    public static void interactiveTerminal() {
        System.out.println();
        System.out.println("Please Enter Two Songs You'd Like to Listen to in the " +
                "Next Two Lines in the Same Way it is Provided Above. Only the " +
                "Song Name is Needed.");

        Scanner sc = new Scanner(System.in); // creates a scanner to read in inputs
        String songInput1 = sc.nextLine();
        String songInput2 = sc.nextLine();

        boolean invalidSong = true;

        do {
            if (!initial10Songs.contains(songInput1) || !initial10Songs.contains(songInput2)) {
                System.out.println("You Entered a Song Which Was Not One of the Listed Songs. " +
                        "Please Try Again.");
                songInput1 = sc.nextLine();
                songInput2 = sc.nextLine();
            } else {
                invalidSong = false;
                System.out.println("You Entered [ " + songInput1 + " ] " + "and " + "[ " + songInput2 + " ]. " +
                        "Your Recommendation Will Be Provided Shortly. " );
                System.out.println();
            }
        } while (invalidSong);

        twoSongNames.add(songInput1);
        twoSongNames.add(songInput2);
        System.out.println();
    }
}

