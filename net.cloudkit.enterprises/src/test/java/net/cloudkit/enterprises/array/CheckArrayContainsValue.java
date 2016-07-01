package net.cloudkit.enterprises.array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * How to Check if an Array Contains a Value in Java?
 */
public class CheckArrayContainsValue {


    // How to check if an array contains a certain value?
    // This is a frequently used operation by a lot of developers. It can be done in several different ways, but the time complexity could be very different. In the following I will show the time each method takes.

    // 1. 4 Different Ways to Check If an Array Contains a Value

    // Use List:

    public static boolean useList(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }
    // Use Set:

    public static boolean useSet(String[] arr, String targetValue) {
        Set<String> set = new HashSet<>(Arrays.asList(arr));
        return set.contains(targetValue);
    }
    // Use Self-Defined Loop:

    public static boolean useLoop(String[] arr, String targetValue) {
        for (String s : arr) {
            if (s.equals(targetValue))
                return true;
        }
        return false;
    }
    // Use Array.binarySearech():

    public static boolean useArraysBinarySearch(String[] arr, String targetValue) {
        int a = Arrays.binarySearch(arr, targetValue);
        if (a > 0)
            return true;
        else
            return false;
    }
    // 2. Time Complexity

    // The approximate time complexity can be compared by using the following code.

    public static void main(String[] args) {
        String[] arr = new String[]{"AB", "BC", "CD", "DE", "EF"};

        long startTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            useList(arr, "A");
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("useList:  " + duration / 1000000);

        startTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            useSet(arr, "A");
        }
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("useSet:  " + duration / 1000000);

        startTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            useLoop(arr, "A");
        }
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("useLoop:  " + duration / 1000000);

        startTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            useArraysBinarySearch(arr, "A");
        }
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("useArrayBinary:  " + duration / 1000000);
    }
    // The difference is clear from the result:

    // useList:  13
    // useSet:  72
    // useLoop:  5
    // useArraysBinarySearch:  9
    // Using self-defined method is most efficient. A lot of developers use the first method, but it is inefficient.
    //
    // Arrays.binarySearch() method is more efficient if the array is sorted.
}
