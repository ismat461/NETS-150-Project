package HelperClasses;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Recommender {
    private Map<String, ArrayList<String>> songMap;
    private final int AVGANDSTDEVROWSIZE = 10;
    private final int AVGANDSTDEVCOLSIZE = 2;
    private final int GENREINDEX = 3;
    private final int BOOLEANARRAYLENGTH = 14;
    private final int THRESHOLD = 8;


    /**
     * Constructor that initializes the base map of songs
     */
    public Recommender(Map<String, ArrayList<String>> songMap) {
        this.songMap = songMap;
    }


    /*
     * METHODS
     */

    /**
     * Randomly chooses 10 songs from an input list of songs
     *
     * @param songs
     * @return list of 10 songs
     */
    public ArrayList<String> get10Songs(ArrayList<String> songs) {
        ArrayList<String> tenSongs = new ArrayList<>();
        Random randomInts = new Random();
        int currInt = randomInts.nextInt(songs.size() - 1);
        int count = 10;
        while (count > 0)  {
            tenSongs.add(songs.get(currInt));
            int nextInt = randomInts.nextInt(songs.size() - 1);
            do {
                if (currInt != nextInt) {
                    currInt = nextInt;
                    count -= 1;
                } else {
                    nextInt = randomInts.nextInt(songs.size() - 1);
                }
            } while (currInt == nextInt);
        }
        return tenSongs;
    }

    /**
     * Randomly chooses 10 songs from the songMap
     *
     * @return list of 10 songs
     */
    public ArrayList<String> getInitSongs() {
        ArrayList<String> tenSongs = new ArrayList<>();
        Random randomInts = new Random();
        Object[] songs = songMap.keySet().toArray();
        int currInt = randomInts.nextInt(songMap.size() - 1);
        int count = 10;
        while (count > 0)  {
            tenSongs.add((String) songs[currInt]);
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
        System.out.println("Here is Your List of 10 Songs: " + tenSongs);
        System.out.println("Here is the List of the Corresponding Artists: " +
                this.getArtists(tenSongs));

        return tenSongs;
    }

    /**
     * Returns a list of the artists of the input songs
     *
     * @param list of names of songs
     * @return list of artists of the songs given
     */
    public ArrayList<String> getArtists(ArrayList<String> songNames) {
        ArrayList<String> artists = new ArrayList<>();
        for (String s : songNames) {
            artists.add(songMap.get(s).get(0));
        }
        return artists;
    }

    /**
     *  Finds and returns genre for a single song
     * @param a song's name
     * @return the song's genre
     */
    private String getGenre(String songName) {
        ArrayList<String> parameters = this.songMap.get(songName);
        // the genre is the third index of the parameters array
        return parameters.get(GENREINDEX);
    }

    private double calculateMean(ArrayList<String> songsInGenre, int indexOfParem) {
        double sumParameter = 0.0;
            for (String songName: songsInGenre) {
                ArrayList<String> parametersOfSong = songMap.get(songName);
                sumParameter += Double.parseDouble(parametersOfSong.get(indexOfParem));
            }
        sumParameter /= songsInGenre.size();
        return sumParameter;
    }

    private double calculateStandardDev(ArrayList<String> songsInGenre, double mean, int indexOfParam) {
        double sum = 0;
        double squareOfDistFromMean = 0;
        double stDev = 0;
        for (String songName : songsInGenre) {
            ArrayList<String> parametersOfSong = songMap.get(songName);
            squareOfDistFromMean = Math.pow(Double.parseDouble(parametersOfSong.get(indexOfParam)) - mean, 2);
            sum += squareOfDistFromMean;
        }
        stDev = Math.sqrt(sum / songsInGenre.size());
        return stDev;
    }

    private double[][] calculateTolerance(String genre) {
        ArrayList<String> songsInGenre = new ArrayList<>();
        double[][] toleranceArray = new double[AVGANDSTDEVROWSIZE][AVGANDSTDEVCOLSIZE];
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
    public int compareStrings(ArrayList<String> song1Parameters, String parameter, int index) {
        if (song1Parameters.get(index).equals(parameter)) {
            return 1;
        } else {
            return 0;
        }
    }

    public int compareQuantities(double[][] tolerances, String parameter, int index) {
        double paramAsDouble = Double.parseDouble(parameter);
        double meanMinusStDev = tolerances[index][0] - tolerances[index][1];
        double meanPlusStDev = tolerances[index][0] + tolerances[index][1];
        if (paramAsDouble >= meanMinusStDev && paramAsDouble <= meanPlusStDev) {
            return 1;
        } else {
            return 0;
        }
    }

    // iterate through the parameter hashmap of every song,
    // and find the songs that exceed the threshold of similarity
    public ArrayList<String> songFit(Map<String, ArrayList<Integer>> parameterMap) {
        Set<String> songNames = parameterMap.keySet();
        ArrayList<String> recommendedSongs = new ArrayList<>();
        ArrayList<Integer> songParameterBooleanList = new ArrayList<>();
        int count = 0;

        for (String songName: songNames) {
            songParameterBooleanList = parameterMap.get(songName);
            for (int i = 0; i < 14; i++) {
                if (songParameterBooleanList.get(i) == 1) {
                    count++;
                }
            }
            if (count >= THRESHOLD) {
                String songRec = songName + " by " + songMap.get(songName).get(0) + ". " +
                        "the artists featured: "  + songMap.get(songName).get(1) +
                        ". release date: " + songMap.get(songName).get(2)
                        + ". genre of song: " + songMap.get(songName).get(3) + ".";
                recommendedSongs.add(songRec);
//                System.out.println(songRec);
            }
            count = 0;
        }
        return recommendedSongs;
    }

    /**
     *
     * @param songFit1
     * @param songFit2
     * @return
     */
    public ArrayList<String> getIntersectionOfFits(ArrayList<String> songFit1, ArrayList<String> songFit2) {
        ArrayList<String> songsInIntersection = new ArrayList<>();
        if (songFit1.size() <= songFit2.size()) {
            for (String song : songFit1) {
                if (songFit2.contains(song)) {
                    songsInIntersection.add(song);
                }
            }
        } else {
            for (String songName : songFit2) {
                if (songFit1.contains(songName)) {
                    songsInIntersection.add(songName);
                }
            }
        }

        return songsInIntersection;
    }

    private Map<String, ArrayList<Integer>> parameterMapHelper(double[][] tolerances, String inputSong) {
        Map<String, ArrayList<Integer>> booleanMap = new HashMap<>();
        ArrayList<String> inputSongParameters = songMap.get(inputSong);
        Set<String> songNames = songMap.keySet();
        int withinRange = 0;
        int indexOfCurrParam = 0;

        // iterating through all of the songs in the songMap
        for (String songName : songNames) {
            // skip the song name, so we don't compare a song to itself
            if (!songName.equals(inputSong)) {
                ArrayList<String> parameters = songMap.get(songName);
                for (String currParam : parameters) {
                    // if we are evaluating a string parameter, determine if the strings are the same
                    if (parameters.indexOf(currParam) <= 3) {
                        indexOfCurrParam = parameters.indexOf(currParam);
                        withinRange = compareStrings(inputSongParameters, currParam, indexOfCurrParam);
                        // we are checking that the second song we're comparing to, isn't already
                        // a key in the boolean hashmap
                        if (!booleanMap.containsKey(songName)) {
                            // add the song to the array list
                            ArrayList<Integer> booleanArray = new ArrayList<>();
                            booleanArray.add(withinRange);
                            booleanMap.put(songName, booleanArray);
                        } else {
                            booleanMap.get(songName).add(withinRange);
                        }
                    } else {
                        indexOfCurrParam = parameters.indexOf(currParam) - 4;
                        withinRange = compareQuantities(tolerances, currParam, indexOfCurrParam);
                        booleanMap.get(songName).add(withinRange);
                    }
                }
            }
        }
        return booleanMap;
    }

    /**
     * Desription: Boolean Hashmap
     * @param twoSongNames
     * Process for both songs: get the genre of the song, calculate
     * the tolerance of each respective parameter, and then find the
     * songs that are within the tolerance range
     */
    public void getRecommendation(ArrayList<String> twoSongNames) {
        String song1 = twoSongNames.get(0);
        String song2 = twoSongNames.get(1);

        double[][] toleranceForSong1Genre = calculateTolerance(getGenre(song1));
        Map<String, ArrayList<Integer>> booleanMapForSong1 = parameterMapHelper(toleranceForSong1Genre, song1);
        Map<String, ArrayList<Integer>> booleanMapForSong2 = parameterMapHelper(toleranceForSong1Genre, song2);

        // find the songs that are similar to user selected song 1 and 2 respectively
        ArrayList<String> songsThatFit1 = songFit(booleanMapForSong1);
        ArrayList<String> songsThatFit2 = songFit(booleanMapForSong2);


        ArrayList<String> songsInIntersection = get10Songs(getIntersectionOfFits(songsThatFit1, songsThatFit2));
        if (songsInIntersection != null) {
            for (String song : songsInIntersection) {
                System.out.println(song);
            }
        } else {
            songsInIntersection.addAll(songsThatFit1);
            songsInIntersection.addAll(songsThatFit2);
            for (String song : get10Songs(songsInIntersection)) {
                System.out.println(song);
            }
        }
    }

    /**
     * HELPER FUNCTIONS
     */

}



