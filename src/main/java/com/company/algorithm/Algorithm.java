package com.company.algorithm;

import java.util.Random;

public abstract class Algorithm {

    static int calculateNewDistance(int oldDistance, int i, int j, int[][] distanceMatrix, int[] path) {
        int cityA = path[i];
        int cityB = path[j];
        int cityBeforeA = 0;
        int cityAfterB = 0;
        if (i == 0)
            cityBeforeA = path[distanceMatrix.length - 1];
        else
            cityBeforeA = path[i - 1];

        if (j == distanceMatrix.length - 1)
            cityAfterB = path[0];
        else
            cityAfterB = path[j + 1];

        int newDistance = oldDistance;
        if (cityBeforeA != cityB || cityA != cityAfterB) {
            newDistance = newDistance - (distanceMatrix[cityBeforeA - 1][cityA - 1] + distanceMatrix[cityB - 1][cityAfterB - 1]);
            newDistance += distanceMatrix[cityBeforeA - 1][cityB - 1] + distanceMatrix[cityA - 1][cityAfterB - 1];
        }

        return newDistance;
    }

    static void reverseSwap(int[] cities, int a, int b) {
        while (a < b) {
            cities[a] ^= cities[b];
            cities[b] ^= cities[a];
            cities[a] ^= cities[b];
            a++;
            b--;
        }
    }

    static void shuffle(int[] cities, int n) {
        for (int i = n - 1; i >= 0; i--) {
            int id = new Random().nextInt(i + 1);
            int tmp = cities[id];
            cities[id] = cities[i];
            cities[i] = tmp;
        }
    }

    static void shuffleXOR(int[] cities, int n) {
        for (int i = n - 1; i >= 0; i--) {
            int id = new Random().nextInt(i + 1);
            if (id != i) {
                cities[id] ^= cities[i];
                cities[i] ^= cities[id];
                cities[id] ^= cities[i];
            }
        }
    }

    static void generateStartingPath(int[][] distanceMatrix, int[] path) {
        int n = distanceMatrix.length;

        for (int i = 0; i < n; i++) {
            path[i] = i + 1;
        }

        shuffleXOR(path, n);
    }
}
