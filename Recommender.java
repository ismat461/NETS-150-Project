package Recommendations;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Recommender {
    Map<String, ArrayList<String>> songMap;

    /*
     * Constructor that initializes the base map
     */
    public Recommender(Map<String, ArrayList<String>> songMap) {
        this.songMap = songMap;
    }

    /**
     * METHODS
     */

    /*
     * Creates song map to be a mapping of song title to the 14 remaing
     * parameters from the data.txt file
     */
   public void makeMap(Set<String> parameters) {

   }

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

}
