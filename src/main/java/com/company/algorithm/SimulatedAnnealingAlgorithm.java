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


    public static ResultEntity findPath(int[][] distanceMatrix, int[] path) {
        return findPath(distanceMatrix, path, 100);
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

    //    ToDo: improve performance
    public static ResultEntity findPath(int[][] distanceMatrix, int[] path, float temperature) {

//        Parameter defining how many neighbour to look at at given temperature
        final int s = 10;

        int n = distanceMatrix.length;

        for (int i = 0; i < n; i++) {
            path[i] = i + 1;
        }

        shuffleXOR(path, n);

//        Czy to jest potrzebne?
        int[] firstPath = path.clone();
        int stepCounter = 0;
        int solutionCounter = 0;
        int[] bestPath = path.clone();
        int distance = ResultCalculator.calculateTotalDistance(path, distanceMatrix);
        int bestDistance = distance;
        boolean globalChange = false;
        int newDistance;
        int numberNoChange = 0;

        do {
            globalChange = false;
            int counter = 0;

            do {
                for (int i = 0; i < n - 1; i++) {
                    for (int j = i + 1; j < n; j++) {
                        counter++;
                        newDistance = calculateNewDistance(distance, i, j, distanceMatrix, path);

//                        ToDo: remove duplicated code
                        if (newDistance < distance) {
                            distance = newDistance;
                            reverseSwap(path, i, j);
                            if (bestDistance > distance) {
                                bestDistance = distance;
                                bestPath = path.clone();
                            }
                            globalChange = true;
                        } else {
                            if (Math.exp((distance - newDistance) / temperature) > Math.random()) {
                                distance = newDistance;
                                reverseSwap(path, i, j);
                            }
                        }
                    }
                }
            } while (counter < s * n * n);

            temperature = calculateTemperature(temperature);
            if(!globalChange) {
                numberNoChange++;
            }
            else {
                numberNoChange = 0;
            }
//            ToDo: add better stop condition
        } while (numberNoChange < 10 && temperature > 0);
//        Should it return ResultEntity?
        return new ResultEntity(firstPath, bestPath, stepCounter, solutionCounter);
    }

    //    ToDo: add better cooling function
    private static float calculateTemperature(float temperature) {
        return (float) (temperature * 0.9);
    }
}
