package com.company.algorithm;

import java.util.Random;

public class HeuristicsAlgorithm {

    public static int[] findPath(int[][] distanceMatrix) {
        int[][] cloneDistanceMatrix = new int[distanceMatrix.length][];
        for (int i = 0; i< distanceMatrix.length; i++) {
            cloneDistanceMatrix[i] = distanceMatrix[i].clone();
        }

        int[] greedyPath = new int[cloneDistanceMatrix.length];


        int firstPlace = new Random().nextInt(cloneDistanceMatrix.length) + 1;
        greedyPath[0] = firstPlace;
        for (int i = 0; i < cloneDistanceMatrix.length; i++) {
            cloneDistanceMatrix[i][firstPlace - 1] = 0;
        }
        for (int i = 1; i < cloneDistanceMatrix.length; i++) {
            int minDistance = Integer.MAX_VALUE;
            int minDistancePlace = 0;
            for (int j = 0; j < cloneDistanceMatrix.length; j++) {
                int currentDistance = cloneDistanceMatrix[greedyPath[i - 1] - 1][j];
                if (currentDistance != 0 && currentDistance < minDistance) {
                    minDistance = currentDistance;
                    minDistancePlace = j + 1;
                }
            }
            greedyPath[i] = minDistancePlace;
            for (int j = 0; j < cloneDistanceMatrix.length; j++) {
                cloneDistanceMatrix[j][minDistancePlace - 1] = 0;
            }
        }
        return greedyPath;
    }
}
