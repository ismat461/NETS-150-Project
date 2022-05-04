package HelperClasses;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Recommender {
    // TESTTING TESTING? IS IT UPDATED????
    private Map<String, ArrayList<String>> songMap;
    private final int avgAndStdevRowSize = 10;
    private final int avgAndStdevColSize = 2;
    private final int genreIndexInMap = 3;


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

    private double calculateMean(ArrayList<String> songsInGenre, int indexOfParem) {
        double sumParameter = 0.0;
        for (int i = 0; i < songsInGenre.size(); i++) {
            for (String songName: songsInGenre) {
                ArrayList<String> parametersOfSong = songMap.get(songName);
                sumParameter += Double.parseDouble(parametersOfSong.get(indexOfParem));
            }
//            if (i == 0) {
//                System.out.println("this is pop edm sum: " + sumParameter);
//                System.out.println("this is pop edm avg: " + (sumParameter);
//            }
        }
        sumParameter /= songsInGenre.size();
//        return sumParameter / songsInGenre.size();
        return sumParameter;

    }

    private double calculateStandardDev(ArrayList<String> songsInGenre, double mean, int indexOfParam) {
        double sum = 0;
        double squareOfDistFromMean = 0;
        double stDev = 0;
        for (int i = 0; i < songsInGenre.size(); i++) {
            for (String songName : songsInGenre) {
                ArrayList<String> parametersOfSong = songMap.get(songName);
                squareOfDistFromMean += Math.pow(Double.parseDouble(parametersOfSong.get(indexOfParam)) - mean, 2);
                sum += squareOfDistFromMean;
            }
        }
        stDev = Math.sqrt(sum / songsInGenre.size());
        return stDev;
    }
    private double[][] calculateTolerance(String genre) {
        ArrayList<String> songsInGenre = new ArrayList<>();
        double[][] toleranceArray = new double[avgAndStdevRowSize][avgAndStdevColSize];
        double mean = 0.0;
        double stDev = 0.0;
        int row = 0;
        int numberOfSongs = 0;

        // create a set of all the songs with the genre
        for (String songName : songMap.keySet()) {
            String songGenre = getGenre(songName);
            if (songGenre.equals(genre)) {
                songsInGenre.add(String.valueOf(songName));
            }
        }

        // iterate through the songsInGenre list to add the parameters of each song
        for (int i = 4; i < 14; i++) {
            mean = calculateMean(songsInGenre, i);
            stDev = calculateStandardDev(songsInGenre, mean, i);
            toleranceArray[row][0] = mean;
            toleranceArray[row][1] = stDev;
            row++;
        }
        return toleranceArray;
    }

    /**
     *
     */
    public int compareString(String s1, String s2) {
        if (s1.equals(s2)) {
            return 1;
        } else {
            return 0;
        }
    }


    /**
     * Desription: Boolean Hashmap
     * @param twoSongNames
     * Process for both songs: get the genre of the song, calculate
     * the tolerance of each respective parameter, and then
     */
    public void createParameterHashMap(ArrayList<String> twoSongNames) {
        String song1 = twoSongNames.get(0);
        String song2 = twoSongNames.get(1);

        System.out.println("this is song 1's genre: " + getGenre(song1));
        System.out.println("this is song 2's genre: " + getGenre(song2));

        double[][] toleranceForSong1Genre = calculateTolerance(getGenre(song1));
//        double[][] toleranceForSong2Genre = calculateTolerance(getGenre(song2));

//        for (int i = 0; i < toleranceForSong2Genre.length; i++) {
//            System.out.println(toleranceForSong2Genre[i][0]);
//            System.out.println(toleranceForSong2Genre[i][1]);
//        }
//

//          System.out.println(Array.deeptoString(toleranceForSong1Genre));
        //print the array, each line will have the mean and std
        System.out.println(Arrays.deepToString(toleranceForSong1Genre).replace("], ", "]\n"));



        }

    }



