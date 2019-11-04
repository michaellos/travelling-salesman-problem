package com.company.algorithm;

import java.util.Random;

public class GreedyHeuristicsAlgorithm {

    public static int[] findPath(int[][] distanceMatrix) {
        int[] greedyPath = new int[distanceMatrix.length];


        int firstPlace = new Random().nextInt(distanceMatrix.length) + 1;
        greedyPath[0] = firstPlace;
        for (int i = 0; i < distanceMatrix.length; i++) {
            distanceMatrix[i][firstPlace - 1] = 0;
        }
        for (int i = 1; i < distanceMatrix.length; i++) {
            int minDistance = Integer.MAX_VALUE;
            int minDistancePlace = 0;
            for (int j = 0; j < distanceMatrix.length; j++) {
                int currentDistance = distanceMatrix[greedyPath[i - 1] - 1][j];
                if (currentDistance != 0 && currentDistance < minDistance) {
                    minDistance = currentDistance;
                    minDistancePlace = j + 1;
                }
            }
            greedyPath[i] = minDistancePlace;
            for (int j = 0; j < distanceMatrix.length; j++) {
                distanceMatrix[j][minDistancePlace - 1] = 0;
            }
        }
        return greedyPath;
    }
}
