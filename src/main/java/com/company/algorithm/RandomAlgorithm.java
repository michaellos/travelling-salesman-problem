package com.company.algorithm;

import java.util.Random;

public class RandomAlgorithm {

    private static void shuffleXOR(int[] cities, int n) {
        for (int i = n - 1; i >= 0; i--) {
            int id = new Random().nextInt(i + 1);
            if (id != i) {
                cities[id] ^= cities[i];
                cities[i] ^= cities[id];
                cities[id] ^= cities[i];
            }
        }
    }

    public static int[] findPath(int[][] distanceMatrix, int[] randomPath) {

        int n = distanceMatrix.length;

        for (int i = 0; i < n; i++) {
            randomPath[i] = i + 1;
        }

        shuffleXOR(randomPath, n);

        return randomPath;
    }
}
