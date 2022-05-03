package HelperClasses;

import java.io.IOException;
import java.util.*;

public class Recommender {
    private Map<String, ArrayList<String>> songMap;

    /*
     * Constructor that initializes the base map
     */

    public Recommender(Map<String, ArrayList<String>> songMap) {
        this.songMap = songMap;
    }

    /**
     * METHODS
     */

    public ArrayList<String> initial10Songs() {
        ArrayList<String> initialTenSongs = new ArrayList<>();
        Object[] songs = songMap.keySet().toArray();
        Random randomInts = new Random();
        int currInt = randomInts.nextInt(songMap.size() - 1);
        int count = 10;
        while (count > 0)  {
            initialTenSongs.add((String) songs[currInt]);
            int nextInt = randomInts.nextInt(songMap.size() - 1);
            do {
                if (currInt != nextInt) {
                    currInt = nextInt;
                    count -= 1;
                } else {
                    nextInt = randomInts.nextInt(songMap.size() - 1);
                }
            } while (currInt == nextInt);
        }
        return initialTenSongs;
    }

    public ArrayList<String> getArtists(ArrayList<String> songNames) {
        ArrayList<String> artists = new ArrayList<>();
        for (String s : songNames) {
            artists.add(songMap.get(s).get(0));
        }
        return artists;
    }

    public ArrayList<String> randomizeSongs() {
        /*
         * Randomly chooses 10 songs from the list
         */
        ArrayList<String> initial10Songs = this.initial10Songs();
        System.out.println("Here is Your List of 10 Songs: " + initial10Songs);
        System.out.println("Here is the List of the Corresponding Artists: " +
                                                    this.getArtists(initial10Songs));
        return initial10Songs;
    }

    //genre for a single song
    private String getGenre(String songName) {
       ArrayList<String> parameters = this.songMap.get(songName);
       // the genre is the third index of the parameters array

        return parameters.get(3);
    }

    private double calculateInterval() {
        // create a set of all the songs with the genre

        return 1.1;
    }


    public void createParameterHashMap(ArrayList<String> twoSongNames) {
        String song1 = twoSongNames.get(0);
        String song2 = twoSongNames.get(1);

        //System.out.println(getGenre(song1));

        // Process for song 1; get the genre and calculate the avg and standard deviation of all the
        // parameters for all songs of the genre

    }


}
