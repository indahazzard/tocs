package Main;

import java.util.Arrays;

public class Search {
    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000};
//        int keyToSearch = 10 * 11;

        for (int size : sizes) {
            int[] array = Main.generateArray(size);
            Arrays.sort(array);
            int[] comparesCountInterAvg = new int[size];
            int[] iterationCountInterAvg = new int[size];
            int[] foundsCountInterAvg = new int[size];

            int[] comparesCountBinAvg = new int[size];
            int[] iterationCountBinAvg = new int[size];
            int[] foundsCountBinAvg = new int[size];

            int[] forInterpolation = Arrays.copyOf(array, array.length);
            long interpolationStart = System.nanoTime();
            for (int i = 0; i <= forInterpolation.length - 1; i++) {
                int comparesCountInter = 0;
                int iterationCountInter = 0;
                int[] interpolationIndex = interpolationSearch(forInterpolation, 0, forInterpolation.length - 1, forInterpolation[i], comparesCountInter, iterationCountInter);
                comparesCountInterAvg[i] = interpolationIndex[1];
                iterationCountInterAvg[i] = interpolationIndex[2];
                if (foundsCountBinAvg[0] != -1) {
                    foundsCountInterAvg[i] = interpolationIndex[0];
                }
            }
            long interpolationEnd = System.nanoTime();
            System.out.println("Interpolation search for average(T): " + size + " took " + (interpolationEnd - interpolationStart));
            int avgComparesInter = Arrays.stream(comparesCountInterAvg).sum() / size;
            System.out.println("avg compares: " + avgComparesInter + " inter for size " + size);
            int avgIterInter = Arrays.stream(iterationCountInterAvg).sum() / size;
            System.out.println("avg iters: " + avgIterInter + " inter for size " + size);

//            System.out.println("founds: " + foundsCountInterAvg.length + " inter for size " + size);
            System.out.println("---------------------------------------------------------");

            // Binary
            int[] forBinary = Arrays.copyOf(array, array.length);
            long binaryStart = System.nanoTime();
            for (int j = 0; j <= forBinary.length - 1; j++) {
                int comparesCountBin = 0;
                int iterationCountBin = 0;

                int[] binaryIndex = binarySearch(
                        forBinary,
                        0,
                        forBinary.length - 1,
                        forBinary[j],
                        comparesCountBin,
                        iterationCountBin
                );

                comparesCountBinAvg[j] = binaryIndex[1];
                iterationCountBinAvg[j] = binaryIndex[2];

                if (foundsCountBinAvg[0] != -1) {
                    foundsCountInterAvg[j] = foundsCountBinAvg[0];
                }
            }
            long binaryEnd = System.nanoTime();
            System.out.println("Binary search for average(T): " + size + " took " + (binaryEnd - binaryStart));
            int avgComparesBin = Arrays.stream(comparesCountBinAvg).sum() / size;
            System.out.println("avg compares: " + avgComparesBin + " binary for size " + size);
            int avgIterBin = Arrays.stream(iterationCountBinAvg).sum() / size;
            System.out.println("avg iters: " + avgIterBin + " binary for size " + size);
//            System.out.println("founds: " + foundsCountBinAvg.length + " inter for size " + size);
            System.out.println("---------------------------------------------------------");

        }
    }

        public static int[] interpolationSearch(int[] arr, int lowest, int highest, int x, int comparesCount, int iterationCount) {
        int position;

        if (lowest <= highest && x >= arr[lowest] && x <= arr[highest]) {
            comparesCount++;
            position = lowest + ((x - arr[lowest]) * (highest - lowest) / (arr[highest] - arr[lowest]));
            if (arr[position] == x) {
                comparesCount++;

                return new int[]{position, comparesCount, iterationCount};
            }

            if (arr[position] < x) {
                comparesCount++;
                iterationCount++;

                return interpolationSearch(arr, position + 1, highest, x, comparesCount, iterationCount);
            }

            if (arr[position] > x) {
                comparesCount++;
                iterationCount++;

                return interpolationSearch(arr, lowest, position - 1, x, comparesCount, iterationCount);
            }
        }

        return new int[]{-1, comparesCount, iterationCount};
    }


    public static int[] binarySearch(int[] arr, int left, int right, int x, int comparesCount, int iterationCount) {
        if (right >= left) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == x) {
                comparesCount++;
                return new int[]{mid, comparesCount, iterationCount};
            }

            if (arr[mid] > x) {
                iterationCount++;

                comparesCount++;
                return binarySearch(arr, left, mid - 1, x, comparesCount, iterationCount);
            }
            iterationCount++;

            return binarySearch(arr, mid + 1, right, x, comparesCount, iterationCount);
        }

        return new int[]{-1, comparesCount, iterationCount};
    }
}
