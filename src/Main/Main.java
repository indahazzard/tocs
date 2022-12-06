package Main;

import java.util.Arrays;
import java.util.Random;

public class Main {
    private static final boolean showRes = false;

    public static void main(String[] args) {
        int[] lengths = {10000, 30000, 90000, 270000, 810000};
        // Test data
        // int[] lengths = {10, 30, 90, 270, 810};

        for (int length : lengths) {
            int[] array = Main.generateArray(length);

            int[] forBubble = Arrays.copyOf(array, array.length);
            long bubbleSortStart = System.currentTimeMillis();
            int[] bubblesortSortedArray = Main.bubbleSort(forBubble);
            long bubbleSortEnd = System.currentTimeMillis();
            System.out.println("Bubble sort for: " + length + " took " + (bubbleSortEnd - bubbleSortStart));

            int[] forComb2 = Arrays.copyOf(array, array.length);
            long comb2sortSortStart = System.currentTimeMillis();
            int[] comb2sortSortedArray = Main.comb2(forComb2);
            long comb2sortSortEnd = System.currentTimeMillis();
            System.out.println("comb2 sort for: " + length + " took " + (comb2sortSortEnd - comb2sortSortStart));

            int[] forCounting = Arrays.copyOf(array, array.length);
            long countingsortSortStart = System.currentTimeMillis();
            int[] countingsortSortedArray = Main.countingSort(forCounting);
            long countingsortSortEnd = System.currentTimeMillis();
            System.out.println("counting sort for: " + length + " took " + (countingsortSortEnd - countingsortSortStart));

            if (Main.showRes) {
                System.out.println(Arrays.toString(bubblesortSortedArray));
                System.out.println(Arrays.toString(comb2sortSortedArray));
                System.out.println(Arrays.toString(countingsortSortedArray));
            }
        }
    }

    public static int[] generateArray(int length) {
        Random rd = new Random();
        int[] arr = new int[length];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = rd.nextInt(65535);
        }

        return arr;
    }

    public static int[] bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        return arr;
    }

    public static int[] comb2(int[] arr) {
        int gap = arr.length;
        int shrink = 2;
        boolean sorted = false;

        while (!sorted) {
            gap = gap / shrink;
            if (gap <= 1) {
                gap = 1;
                sorted = true;
            }

            int i = 0;
            while ((i + gap) < arr.length) {
                if (arr[i] > arr[i + gap]) {
                    int temp = arr[i];
                    arr[i] = arr[i + gap];
                    arr[i + gap] = temp;
                    sorted = false;
                }
                i++;
            }
        }

        return arr;
    }


    public static int[] countingSort(int[] arr) {
        int[] array1 = new int[arr.length + 1];

        int x = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > x)
                x = arr[i];
        }
        int[] countArray = new int[x + 1];

        for (int i = 0; i < x; ++i) {
            countArray[i] = 0;
        }

        for (int j : arr) {
            countArray[j]++;
        }

        for (int i = 1; i <= x; i++) {
            countArray[i] += countArray[i - 1];
        }

        for (int i = arr.length - 1; i >= 0; i--) {
            array1[countArray[arr[i]] - 1] = arr[i];
            countArray[arr[i]]--;
        }

        for (int i = 0; i < arr.length; i++) {
            arr[i] = array1[i];
        }

        return arr;
    }
}
