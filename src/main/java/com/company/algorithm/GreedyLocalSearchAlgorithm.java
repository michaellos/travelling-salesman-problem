package com.company.algorithm;

import com.company.control.ResultCalculator;
import com.company.entity.ResultEntity;

import java.util.Random;

public class GreedyLocalSearchAlgorithm {

    private static void reverseSwap(int[] cities, int a, int b) {
        while (a < b) {
            cities[a] ^= cities[b];
            cities[b] ^= cities[a];
            cities[a] ^= cities[b];
            a++;
            b--;
        }
    }

    private static void shuffle(int[] cities, int n) {
        for (int i = n - 1; i >= 0; i--) {
            int id = new Random().nextInt(i + 1);
            int tmp = cities[id];
            cities[id] = cities[i];
            cities[i] = tmp;
        }
    }

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

    private static int calculateNewDistance(int oldDistance, int i, int j, int[][] distanceMatrix, int[] greedyPath) {
        int cityA = greedyPath[i];
        int cityB = greedyPath[j];
        int cityBeforeA = 0;
        int cityAfterB = 0;
        if (i == 0)
            cityBeforeA = greedyPath[distanceMatrix.length - 1];
        else
            cityBeforeA = greedyPath[i - 1];

        if (j == distanceMatrix.length - 1)
            cityAfterB = greedyPath[0];
        else
            cityAfterB = greedyPath[j + 1];

        int newDistance = oldDistance;
        if (cityBeforeA != cityB || cityA != cityAfterB) {
            newDistance = newDistance - (distanceMatrix[cityBeforeA - 1][cityA - 1] + distanceMatrix[cityB - 1][cityAfterB - 1]);
            newDistance += distanceMatrix[cityBeforeA - 1][cityB - 1] + distanceMatrix[cityA - 1][cityAfterB - 1];
        }

        return newDistance;
    }

    public static ResultEntity findPath(int[][] distanceMatrix, int[] greedyPath) {

        int n = distanceMatrix.length;

        for (int i = 0; i < n; i++) {
            greedyPath[i] = i + 1;
        }

        shuffleXOR(greedyPath, n);

        int distance = ResultCalculator.calculateTotalDistance(greedyPath, distanceMatrix);
        int newDistance;
        boolean change;
        int stepCounter = 0;
        int solutionCounter = 0;
        int[] firstPath = greedyPath.clone();

        do {
            change = false;
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    newDistance = calculateNewDistance(distance, i, j, distanceMatrix, greedyPath);
                    solutionCounter++;
                    if (newDistance < distance) {
                        distance = newDistance;
                        stepCounter++;
                        reverseSwap(greedyPath, i, j);
                        change = true;
                        break;
                    }
                }
                if (change)
                    break;
            }
        } while (change);

        return new ResultEntity(firstPath, greedyPath, stepCounter, solutionCounter);
    }
}
