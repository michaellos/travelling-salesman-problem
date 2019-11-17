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

    public static ResultEntity findPath(int[][] distanceMatrix, int[] path) {
        return findPath(distanceMatrix, path, 100);
    }

//    ToDo: improve performance
    public static ResultEntity findPath(int[][] distanceMatrix, int[] path, float temperature) {

        int n = distanceMatrix.length;

        for (int i = 0; i < n; i++) {
            path[i] = i + 1;
        }

        shuffleXOR(path, n);

//        Czy to jest potrzebne?
        int[] firstPath = path.clone();
        int stepCounter = 0;
        int solutionCounter = 0;
        int[] bestPath = new int[n];
        int distance = ResultCalculator.calculateTotalDistance(path, distanceMatrix);
        int bestDistance = distance;

        do {
//            ToDo: add better condition probably depending on temperature (like stairs)
            for (int i = 0; i < n; i++) {
                generateNewPath(path, n);

                // perhaps no need to calculate distance from scratch
                int newDistance = ResultCalculator.calculateTotalDistance(path, distanceMatrix);

                // ToDo: remove duplicated code
                if (newDistance <= distance) {
                    distance = newDistance;
                    if (bestDistance > distance) {
                        bestDistance = distance;
                        bestPath = path.clone();
                    }
                }
                else {
                    if (Math.exp((distance - newDistance) / temperature) > Math.random())  {
                        distance = newDistance;
                        if (bestDistance > distance) {
                            bestDistance = distance;
                            bestPath = path.clone();
                        }
                    }
                }
            }
            temperature = calculateTemperature(temperature);
//            ToDo: add better stop condition
        }while(temperature > 50);

//        Should it return ResultEntity?
        return new ResultEntity(firstPath, bestPath, stepCounter, solutionCounter);
    }

//    ToDo: add better cooling function
    private static float calculateTemperature(float temperature) {
        return temperature - 1;
    }

    //    ToDo: implement finding new path
    private static void generateNewPath(int[] path, int n) {
        shuffleXOR(path, n);
    }
}
