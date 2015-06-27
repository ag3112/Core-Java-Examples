package com.practice.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Intel on 6/27/2015.
 * Program to flatten the List of Object using Recursion.
 */
public class ListFlatter {

    public static void main(String[] args) {

        List lst = new ArrayList();
        // Some flat numbers
        lst.add(1);
        lst.add(2);

        // A sub list of type integer
        List<Integer> intLst = new ArrayList<Integer>();
        intLst.add(3);
        intLst.add(4);
        lst.add(intLst);

        // A sub list containing objects
        List<Integer> subLst = new ArrayList<Integer>();
        subLst.add(5);
        subLst.add(6);
        lst.add(subLst);

        // Integer Array
        lst.add(new Integer[]{7, 8});

        List<Integer> flatLst = flatter(lst);

        for (Integer i : flatLst) {
            System.out.print(i + "\t");
        }

    }

    private static List<Integer> flatter(List lst) {

        List<Integer> flatLst = new ArrayList<Integer>();
        for (Object obj : lst) {

            if (obj instanceof Integer) {
                flatLst.add((Integer) obj);
            }

            if (obj instanceof List) {
                List<Integer> l = flatter((List) obj);
                flatLst.addAll(l);
            }

            if (obj instanceof Integer[]) {
                List<Integer> objLst = Arrays.asList((Integer[]) obj);
                List<Integer> l = flatter(objLst);
                flatLst.addAll(l);
            }
        }
        return flatLst;
    }

}
