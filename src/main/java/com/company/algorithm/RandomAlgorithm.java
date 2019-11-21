package com.company.algorithm;

import java.util.Random;

public class RandomAlgorithm extends Algorithm {

    public static int[] findPath(int[][] distanceMatrix, int[] randomPath) {
        generateStartingPath(distanceMatrix, randomPath);

        return randomPath;
    }
}
