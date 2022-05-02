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

        // Creating the adjacency list to store nodes and their neighbors
        Map<Integer, Set<Integer>> adjList = new HashMap<>();
        for (String line : lines) {
            fr.getParameter(line);
        }


    }
}

