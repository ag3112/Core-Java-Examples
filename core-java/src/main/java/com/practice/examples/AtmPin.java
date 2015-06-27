package com.practice.examples;

import java.text.DecimalFormat;

/**
 * Created by Intel on 6/27/2015.
 * Program to print all atm pins
 */
public class AtmPin {

    public static void main(String[] args) {

        DecimalFormat df = new DecimalFormat("0000.#");

        for (int i = 0; i <= 9999; i++) {
            System.out.println(df.format(i));
        }

    }
}
