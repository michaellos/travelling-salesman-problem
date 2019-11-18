package com.company.algorithm;

import com.company.control.ResultCalculator;
import com.company.entity.ResultEntity;

import java.util.Random;

public class SimulatedAnnealingAlgorithm {

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

    private static void reverseSwap(int[] cities, int a, int b) {
        while (a < b) {
            cities[a] ^= cities[b];
            cities[b] ^= cities[a];
            cities[a] ^= cities[b];
            a++;
            b--;
        }
    }

    private static double getStartingTemperature(int[][] distanceMatrix, int[] path) {
        int maxDifference = getMaxNeighborDifference(distanceMatrix, path);
        double logValue = Math.log(0.99);

        return -(maxDifference / logValue);
    }

    private static int getMaxNeighborDifference(int[][] distanceMatrix, int[] path) {
        int maxDifference = 0;

        for (int i = 0; i < 10000; i++) {
            int difference = getSampleDifference(distanceMatrix, path);
            maxDifference = Math.max(maxDifference, difference);
        }

        return maxDifference;
    }

    private static int getSampleDifference(int[][] distanceMatrix, int[] path) {
        path = RandomAlgorithm.findPath(distanceMatrix, path);
        int distance = ResultCalculator.calculateTotalDistance(path, distanceMatrix);
        Random random = new Random();
        int i = random.nextInt(path.length);
        int j = random.nextInt(path.length);
        int distanceFromNeighbor = calculateNewDistance(distance, i, j, distanceMatrix, path);
        return Math.abs(distance - distanceFromNeighbor);
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

    private static double coolingTemperature(double temperature, double coolingParameter) {
        return temperature * coolingParameter;
    }

    private static ResultEntity findPath(int[][] distanceMatrix, int[] path, double temperature, double coolingParameter, int stepParameter) {

        int n = distanceMatrix.length;

        for (int i = 0; i < n; i++) {
            path[i] = i + 1;
        }

        shuffleXOR(path, n);

        int[] firstPath = path.clone();
        int stepCounter = 0;
        int solutionCounter = 0;
        int[] bestPath = path.clone();
        int distance = ResultCalculator.calculateTotalDistance(path, distanceMatrix);
        int bestDistance = distance;
        boolean change;
        int newDistance;
        int numberNoChange = 0;

        do {
            change = false;
            int counter = 0;

            do {
                for (int i = 0; i < n - 1; i++) {
                    for (int j = i + 1; j < n; j++) {
                        counter++;
                        newDistance = calculateNewDistance(distance, i, j, distanceMatrix, path);
                        solutionCounter++;

                        if (newDistance < distance) {
                            distance = newDistance;
                            stepCounter++;
                            reverseSwap(path, i, j);
                            if (bestDistance > distance) {
                                bestDistance = distance;
                                bestPath = path.clone();
                            }
                            change = true;
                        } else {
                            if (Math.exp((distance - newDistance) / temperature) > Math.random()) {
                                distance = newDistance;
                                stepCounter++;
                                reverseSwap(path, i, j);
                            }
                        }
                    }
                }
            } while (counter < stepParameter * n * n);

            temperature = coolingTemperature(temperature, coolingParameter);
            if (!change) {
                numberNoChange++;
            } else {
                numberNoChange = 0;
            }
        } while (numberNoChange < 10);

        return new ResultEntity(firstPath, bestPath, stepCounter, solutionCounter);
    }

    public static ResultEntity findPath(int[][] distanceMatrix, int[] path) {

//        Parameters
        double startingTemperature = getStartingTemperature(distanceMatrix, path);
        double coolingParameter = 0.9;
        int stepParameter = 20;

        return findPath(distanceMatrix, path, startingTemperature, coolingParameter, stepParameter);
    }
}
