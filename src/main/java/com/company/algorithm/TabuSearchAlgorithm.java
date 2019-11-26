package com.company.algorithm;

import com.company.control.ResultCalculator;
import com.company.entity.ResultEntity;
import com.company.entity.TabuSearchMove;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TabuSearchAlgorithm extends Algorithm {

    private static int cadenceValue;
    private static final int NOT_IMPROVED_SOLUTION_VALUE = 10;

    public static ResultEntity findPath(int[][] distanceMatrix, int[] tabuSearchPath) {
        int n = distanceMatrix.length;
        cadenceValue = n / 4;

        generateStartingPath(distanceMatrix, tabuSearchPath);

        int distance = ResultCalculator.calculateTotalDistance(tabuSearchPath, distanceMatrix);
        int newDistance;
        int[] bestPath = tabuSearchPath.clone();
        int[][] tabuList = initTabuSearchList(n);
        int notImprovedSolutionCounter = 0;
        int stepCounter = 0;
        int solutionCounter = 0;
        int[] firstPath = tabuSearchPath.clone();

        int actualDistance = distance;
        do {
            List<TabuSearchMove> candidateList = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    newDistance = calculateNewDistance(actualDistance, i, j, distanceMatrix, tabuSearchPath);
                    solutionCounter++;
                    candidateList.add(new TabuSearchMove(i, j, newDistance));
                }
            }
            candidateList.sort(Comparator.comparingInt(TabuSearchMove::getDistance));
            candidateList = candidateList.subList(0, n / 10);
            do {
                TabuSearchMove candidate = candidateList.get(0);
                if (!isTabu(tabuList, candidate.getCityA(), candidate.getCityB()) || candidate.getDistance() < distance) {
                    reverseSwap(tabuSearchPath, candidate.getCityA(), candidate.getCityB());
                    stepCounter++;
                    notImprovedSolutionCounter++;
                    decrementCadence(tabuList);
                    addTabu(tabuList, tabuSearchPath[candidate.getCityA()] - 1, tabuSearchPath[candidate.getCityB()] - 1);
                    actualDistance = candidate.getDistance();
                    if (actualDistance < distance) {
                        notImprovedSolutionCounter = 0;
                        distance = actualDistance;
                        bestPath = tabuSearchPath.clone();
                    }
                }
                candidateList.remove(0);

                if (candidateList.isEmpty()) {
                    break;
                }

                for (TabuSearchMove tabuSearchMove : candidateList) {
                    tabuSearchMove.setDistance(calculateNewDistance(actualDistance, tabuSearchMove.getCityA(), tabuSearchMove.getCityB(), distanceMatrix, tabuSearchPath));
                    solutionCounter++;
                }

                candidateList.sort(Comparator.comparingInt(TabuSearchMove::getDistance));
            } while (!candidateList.isEmpty());

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
            tabuList[cityA][cityB] = cadenceValue;
        } else {
            tabuList[cityB][cityA] = cadenceValue;
        }
    }
}
