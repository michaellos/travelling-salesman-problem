package com.company.algorithm;

import com.company.control.ResultCalculator;
import com.company.entity.ResultEntity;

import java.util.Random;

public class TabuSearchAlgorithm extends Algorithm {

    private static final int CADENCE_VALUE = 3;
    private static final int NOT_IMPROVED_SOLUTION_VALUE = 1000;

    public static ResultEntity findPath(int[][] distanceMatrix, int[] tabuSearchPath) {
        int n = distanceMatrix.length;

        generateStartingPath(distanceMatrix, tabuSearchPath);

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

    private static int[][] initTabuSearchList(int n) {
        int[][] tabuSearchList = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tabuSearchList[i][j] = 0;
            }
        }
        return tabuSearchList;
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
}
