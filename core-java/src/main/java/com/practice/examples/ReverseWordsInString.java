package com.practice.examples;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Intel on 6/28/2015.
 * Program to reverse the words in the given String
 *
 */
public class ReverseWordsInString {

    public static void main(String[] args) {
        // Input String
        String plainString = "You are bad !!";


        String reverseString = reverseAtPos(plainString);
        // Reverse String
        System.out.println(reverseString);

    }

    private static String reverseAtPos(String plainString) {
        String[] stringArray = plainString.split(" ");
        String[] reverseStringArray = new String[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            reverseStringArray[i] = new StringBuilder(stringArray[i]).reverse().toString();
        }

        StringBuilder reverseString = new StringBuilder();
        for (int i = reverseStringArray.length - 1; i >= 0; i--) {
            reverseString.append(reverseStringArray[i]).append(" ");
        }

        return StringUtils.trimToEmpty(reverseString.toString());
    }
}
