package com.artist.web.bakerscorner.network;

/**
 * Created by User on 05-Apr-18.
 */

public class Utils {

    static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    /**
     * Utility method to convert first letter of a string to Uppercase
     *
     * @param word
     * @return word with first letter uppercase
     */
    public static String convertStringToFirstCapital(String word) {
        return word.toUpperCase().charAt(0) + word.substring(1).toLowerCase();
    }
}
