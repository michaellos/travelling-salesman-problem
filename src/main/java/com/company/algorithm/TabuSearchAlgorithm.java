package com.company.algorithm;

import com.company.control.ResultCalculator;
import com.company.entity.ResultEntity;

import java.util.Random;

public class TabuSearchAlgorithm {

    private static final int CADENCE_VALUE = 3;
    private static final int NOT_IMPROVED_SOLUTION_VALUE = 1000;

    public static ResultEntity findPath(int[][] distanceMatrix, int[] tabuSearchPath) {
        int n = distanceMatrix.length;

        for (int i = 0; i < n; i++) {
            tabuSearchPath[i] = i + 1;
        }

        shuffleXOR(tabuSearchPath, n);

        int distance = ResultCalculator.calculateTotalDistance(tabuSearchPath, distanceMatrix);
        int newDistance;
        int[] bestPath = tabuSearchPath.clone();
        int cityA = 0;
        int cityB = 0;
        int[][] tabuList = initTabuSearchList(n);
        int notImprovedSolutionCounter = 0;
        int stepCounter = 0;
        int solutionCounter = 0;
        int[] firstPath = tabuSearchPath.clone();

        do {
            int currentBestDistance = Integer.MAX_VALUE;
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    newDistance = calculateNewDistance(distance, i, j, distanceMatrix, tabuSearchPath);
                    solutionCounter++;
                    if ((newDistance < currentBestDistance && !isTabu(tabuList, i, j)) || newDistance < distance) {
                        currentBestDistance = newDistance;
                        cityA = i;
                        cityB = j;
                    }
                }
            }
            reverseSwap(tabuSearchPath, cityA, cityB);
            notImprovedSolutionCounter++;
            decrementCadence(tabuList);
            addTabu(tabuList, tabuSearchPath[cityA] - 1, tabuSearchPath[cityB] - 1);
            if (currentBestDistance < distance) {
                notImprovedSolutionCounter = 0;
                stepCounter++;
                distance = currentBestDistance;
                bestPath = tabuSearchPath.clone();
            }
        } while (notImprovedSolutionCounter < NOT_IMPROVED_SOLUTION_VALUE);

        return new ResultEntity(firstPath, bestPath, stepCounter, solutionCounter);
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

    private static int[][] initTabuSearchList(int n) {
        int[][] tabuSearchList = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tabuSearchList[i][j] = 0;
            }
        }
        return tabuSearchList;
    }

    private static int calculateNewDistance(int oldDistance, int i, int j, int[][] distanceMatrix, int[] path) {
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

    private static boolean isTabu(int[][] tabuList, int cityA, int cityB) {
        if (cityA < cityB) {
            return tabuList[cityA][cityB] > 0;
        } else {
            return tabuList[cityB][cityA] > 0;
        }
    }

    private static void decrementCadence(int[][] tabuList) {
        for (int i = 0; i < tabuList.length - 1; i++) {
            for (int j = i + 1; j < tabuList.length; j++) {
                if (tabuList[i][j] > 0) {
                    tabuList[i][j]--;
                }
            }
        }
    }

    private static void addTabu(int[][] tabuList, int cityA, int cityB) {
        if (cityA < cityB) {
            tabuList[cityA][cityB] = CADENCE_VALUE;
        } else {
            tabuList[cityB][cityA] = CADENCE_VALUE;
        }
    }

    private static void reverseSwap(int[] cities, int a, int b) {
        while (a < b) {
            cities[a] ^= cities[b];
            cities[b] ^= cities[a];
            cities[a] ^= cities[b];
            a++;
            b--;
        }
    }
}
