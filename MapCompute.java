package com.thekarlbrown;

import java.util.*;

public class MapCompute {
    /**
     * I am assuming we are storing the data in a ArrayList<String> as it is most
     * efficient and flexible for future additions (See http://bigocheatsheet.com/)
     * I obtain the raw input as a string array since the page quantity is preset
     * This allows for the addition of new pages to our hashmap
     * I am utilizing a hash table/map to rapidly perform all 3 operations
     * It is implied that a hash map knows iii, but I have a function to illustrate at populateHashMap()
     * NOTE: For the tied most occurring words, the first instance seen will be returned
     * This can be changed to the last by changing populateMap check to >=, moving comparison outside if, & removing initial set
     * The core code is just populateMap() for iii and i, and mapContains for ii, but is contained in a compiled class for proof
     */
    public static void main(String[] args) {
        //initialize class
        MapCompute mapCompute = new MapCompute();
        //test data
        mapCompute.addPages(new String[]{"test", "data", "for", "executive", "office", "executive", "data", "president", "executive", "test",
                "president", "president", "president", "executive", "executive", "of", "of", "of", "of", "of", "of", "of", "of", "of",
                "of", "of", "of", "of", "of", "the", "president", "internship"});
        //create map while keeping track of leaderboard
        mapCompute.populateMap();
        //i
        System.out.println(mapCompute.mostFrequentOutput());
        //ii
        if (mapCompute.mapContains("executive")) {
            System.out.println("The hashmap contains 'executive'");
        }
        if (!mapCompute.mapContains("nothere")) {
            System.out.println("The hashmap doesn't contain 'nothere'");
        }
        //iii
        System.out.println(mapCompute.totalHashmapContents());
    }

    List<String> page_list;
    Map<String, Integer> page_map;
    String highestWord, currentMapKey;
    int currentMapValue;
    int highestWordCount;

    /**
     * default constructor
     */
    public MapCompute() {
        highestWordCount = 0;
        page_list = new ArrayList<String>();
        page_map = new HashMap<String, Integer>();
    }

    /**
     * Create the hashmap, obtain the highest key,
     */
    public void populateMap() {
        //set initially for performance saving check for new keys
        //checks to see if you are adding on or not
        if (highestWordCount == 0) {
            highestWord = page_list.get(0);
            highestWordCount = 1;
        }
        //main loop
        for (int x = 0; x < page_list.size(); x++) {
            currentMapKey = page_list.get(x);
            if (page_map.containsKey(currentMapKey)) {
                currentMapValue = page_map.get(currentMapKey);
                currentMapValue++;
                if (currentMapValue > highestWordCount) {
                    highestWordCount++;
                    highestWord = currentMapKey;
                }
                page_map.put(currentMapKey, currentMapValue);
            } else {
                page_map.put(currentMapKey, 1);
            }
        }
    }

    /**
     * Return a boolean that checks to answer ii (no different than calling the function)
     * @param word - Value to check
     */
    public boolean mapContains(String word) { return page_map.containsKey(word);}

    /**
     * Add pages to our list
     * @param rawinput - Raw data from the pages
     */
    public void addPages(String[] rawinput) {
        for (int x = 0; x < rawinput.length; x++) {
            page_list.add(x, rawinput[x]);
        }
    }

    /**
     * Clear our list
     */
    public void clearPages() {
        page_list.clear();
    }

    /**
     * Output the most frequent word and its count for i
     * @return String containing most frequent word and count
     */
    public String mostFrequentOutput() {
        return "The most frequent word is: '" + highestWord + "' occurring " + highestWordCount + " times";
    }

    /**
     * Output each item of hashmap as proof of iii
     * @return String containing each word and its count separated by line breaks
     */
    public String totalHashmapContents() {
        String output = "Here is the current contents of our hashmap:\n";
        for (String word : page_map.keySet()) {
            output = output + "Word: '" + word + "' Occurences: " + page_map.get(word) + "\n";
        }
        return output;
    }
}

