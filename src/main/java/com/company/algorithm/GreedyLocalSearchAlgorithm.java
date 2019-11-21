package com.company.algorithm;

import com.company.control.ResultCalculator;
import com.company.entity.ResultEntity;

public class GreedyLocalSearchAlgorithm extends Algorithm {

    public static ResultEntity findPath(int[][] distanceMatrix, int[] greedyPath) {
        int n = distanceMatrix.length;

        generateStartingPath(distanceMatrix, greedyPath);

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
