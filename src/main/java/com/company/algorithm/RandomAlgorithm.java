package com.company.algorithm;

public class RandomAlgorithm extends Algorithm {

    public static int[] findPath(int[][] distanceMatrix, int[] randomPath, float time) {
        int n = distanceMatrix.length;

        for (int i = 0; i < n; i++) {
            randomPath[i] = i + 1;
        }

        long endTime;
        long startTime = System.currentTimeMillis();
        do {
            shuffleXOR(randomPath, n);
            endTime = System.currentTimeMillis();
        } while (endTime - startTime < time);

        return randomPath;
    }
}
