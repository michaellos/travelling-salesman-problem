package com.company.algorithm;

import com.company.control.ResultCalculator;
import com.company.entity.ResultEntity;

import java.util.Random;

public class SteepestLocalSearchAlgorithm extends Algorithm {

    public static ResultEntity findPath(int[][] distanceMatrix, int[] steepestPath) {
        int n = distanceMatrix.length;

        generateStartingPath(distanceMatrix, steepestPath);

        int distance = ResultCalculator.calculateTotalDistance(steepestPath, distanceMatrix);
        int newDistance;
        int bestDistance = Integer.MAX_VALUE;
        int cityA = 0;
        int cityB = 0;
        boolean change;
        int[] firstPath = steepestPath.clone();
        int stepCounter = 0;
        int solutionCounter = 0;

        do {
            change = false;
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    newDistance = calculateNewDistance(distance, i, j, distanceMatrix, steepestPath);
                    solutionCounter++;
                    if (newDistance < bestDistance) {
                        bestDistance = newDistance;
                        cityA = i;
                        cityB = j;
                    }
                }
            }
            if (bestDistance < distance) {
                distance = bestDistance;
                stepCounter++;
                reverseSwap(steepestPath, cityA, cityB);
                change = true;
            }
        } while (change);

        return new ResultEntity(firstPath, steepestPath, stepCounter, solutionCounter);
    }
}
