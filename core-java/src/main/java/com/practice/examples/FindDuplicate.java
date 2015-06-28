package com.practice.examples;

import java.util.*;

/**
 * Created by Intel on 6/28/2015.
 * To find the duplicate from n numbers
 */
public class FindDuplicate {
    public static void main(String[] args) {
        int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 2, 303, 91, 12, 13, 0};
        Map<Integer, List<Integer>> duplicateRecords = findDuplicate(numbers);
        if (duplicateRecords.isEmpty()) {
            System.out.print("No duplicate number found");
        } else {
            for (Map.Entry<Integer, List<Integer>> entrySet : duplicateRecords.entrySet()) {
                System.out.println("Duplicate number [" + entrySet.getKey() + "],found at positions " + Arrays.toString(entrySet.getValue().toArray()));
            }
        }
    }

    private static Map<Integer, List<Integer>> findDuplicate(int[] numbers) {

        Map<Integer, List<Integer>> duplicateRecords = new HashMap<Integer, List<Integer>>();

        for (int i = 0; i < numbers.length; i++) {

            if (duplicateRecords.containsKey(numbers[i])) {
                continue;
            }

            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] == numbers[j]) {
                    if (duplicateRecords.containsKey(numbers[i])) {
                        if (!duplicateRecords.get(numbers[i]).contains(j + 1)) {
                            duplicateRecords.get(numbers[i]).add(j + 1);
                        }
                    } else {
                        List<Integer> idxLst = new ArrayList<Integer>();
                        idxLst.add(i + 1);
                        idxLst.add(j + 1);
                        duplicateRecords.put(numbers[i], idxLst);
                    }
                }
            }
        }
        return duplicateRecords;
    }
}
