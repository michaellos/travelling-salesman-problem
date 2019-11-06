package com.company;

import com.company.algorithm.GreedyHeuristicsAlgorithm;
import com.company.algorithm.GreedyLocalSearchAlgorithm;
import com.company.algorithm.RandomAlgorithm;
import com.company.algorithm.SteepestLocalSearchAlgorithm;
import com.company.control.DatasetParser;
import com.company.control.ResultCalculator;

import java.io.File;

import static com.company.entity.NameConstants.*;

public class Main {

    public static void main(String[] args) {

//        int[][] distanceMatrix = DatasetParser.loadDatasetEuc2D(new File(EUC_2D_INSTANCES_PATH + BERLIN52 + INSTANCE_EXTENSION).getAbsolutePath());
//        for (int[] row : distanceMatrix) {
//            for (int distance : row) {
//                System.out.print(distance + " ");
//            }
//            System.out.println();
//        }
//
//        int[] optimalPermutation = DatasetParser.loadOptimalPermutation(new File(EUC_2D_OPTIMAL_RESULT_PATH + BERLIN52 + OPTIMAL_RESULT_EXTENSION).getAbsolutePath());
//
//        int optimalDistance = ResultCalculator.calculateTotalDistance(optimalPermutation, distanceMatrix);
//        System.out.println(optimalDistance);
//
//        double similarity = ResultCalculator.calculateSolutionSimilarity(optimalPermutation, optimalPermutation);
//        System.out.println(similarity);
//
//        for (String filename : FILENAMES) {
//            System.out.println(filename);
//        }
//
//        int[] greedyPath = GreedyHeuristicsAlgorithm.findPath(distanceMatrix);
//        for (int place : greedyPath) {
//            System.out.print(place + " ");
//        }


        int[][] distanceMatrix = DatasetParser.loadDatasetEuc2D(new File(EUC_2D_INSTANCES_PATH + BERLIN52 + INSTANCE_EXTENSION).getAbsolutePath());

        int[] greedyPath = GreedyLocalSearchAlgorithm.findPath(distanceMatrix);
        int[] steepestPath = SteepestLocalSearchAlgorithm.findPath(distanceMatrix);
        int[] randomPath = RandomAlgorithm.findPath(distanceMatrix);
        int[] heuristicPath = GreedyHeuristicsAlgorithm.findPath(distanceMatrix);

        int greedyDistance = ResultCalculator.calculateTotalDistance(greedyPath, distanceMatrix);
        int steepestDistance = ResultCalculator.calculateTotalDistance(steepestPath, distanceMatrix);
        int randomDistance = ResultCalculator.calculateTotalDistance(randomPath, distanceMatrix);
        int heuristicDistance = ResultCalculator.calculateTotalDistance(heuristicPath, distanceMatrix);

        int[] optimalPermutation = DatasetParser.loadOptimalPermutation(new File(EUC_2D_OPTIMAL_RESULT_PATH + BERLIN52 + OPTIMAL_RESULT_EXTENSION).getAbsolutePath());
        int optimalDistance = ResultCalculator.calculateTotalDistance(optimalPermutation, distanceMatrix);

        System.out.println();
        System.out.println("Greedy local search: " + greedyDistance);
        System.out.println("Steepest local search: " + steepestDistance);
        System.out.println("Random: " + randomDistance);
        System.out.println("Heuristic: " + heuristicDistance);
        System.out.println("Optimal: " + optimalDistance);


        int counter = 0;
        long endTime;
        long startTime = System.currentTimeMillis();
        do {
            GreedyLocalSearchAlgorithm.findPath(distanceMatrix);
            endTime = System.currentTimeMillis();
            counter++;
        } while (endTime - startTime < 1000 || counter < 10);

        System.out.println(counter);

        float t = (float) (endTime- startTime) / counter;
        System.out.println("\nAverage time Greedy local search: " + t);
    }
}