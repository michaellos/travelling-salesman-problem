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

        int[][] distanceMatrix = DatasetParser.loadDatasetEuc2D(new File(EUC_2D_INSTANCES_PATH + BERLIN52 + INSTANCE_EXTENSION).getAbsolutePath());
        for (int[] row : distanceMatrix) {
            for (int distance : row) {
                System.out.print(distance + " ");
            }
            System.out.println();
        }

        int[] optimalPermutation = DatasetParser.loadOptimalPermutation(new File(EUC_2D_OPTIMAL_RESULT_PATH + BERLIN52 + OPTIMAL_RESULT_EXTENSION).getAbsolutePath());

        int optimalDistance = ResultCalculator.calculateTotalDistance(optimalPermutation, distanceMatrix);
        System.out.println(optimalDistance);

        double similarity = ResultCalculator.calculateSolutionSimilarity(optimalPermutation, optimalPermutation);
        System.out.println(similarity);

        for (String filename : FILENAMES) {
            System.out.println(filename);
        }

        int[] greedyPath = GreedyHeuristicsAlgorithm.findPath(distanceMatrix);
        for (int place : greedyPath) {
            System.out.print(place + " ");
        }


        int[][] distanceMatrix2 = DatasetParser.loadDatasetEuc2D(new File(EUC_2D_INSTANCES_PATH + BERLIN52 + INSTANCE_EXTENSION).getAbsolutePath());
        int[] greedyPath2 = GreedyLocalSearchAlgorithm.findPath(distanceMatrix2);
        int[] steepestPath = SteepestLocalSearchAlgorithm.findPath(distanceMatrix2);
        int[] randomPath = RandomAlgorithm.findPath(distanceMatrix2);
        int distanceGreedy = ResultCalculator.calculateTotalDistance(greedyPath2, distanceMatrix2);
        int distanceSteepest = ResultCalculator.calculateTotalDistance(steepestPath, distanceMatrix2);
        int distanceRandom = ResultCalculator.calculateTotalDistance(randomPath, distanceMatrix2);
        System.out.println();
        System.out.println("Greedy local search: " + Integer.toString(distanceGreedy));
        System.out.println("Steepest local search: " + Integer.toString(distanceSteepest));
        System.out.println("Random: " + Integer.toString(distanceRandom));
        System.out.print("Optimal: " + Integer.toString(optimalDistance));


        int counter = 0;
        long time0 = System.currentTimeMillis();

        do {
            GreedyLocalSearchAlgorithm.findPath(distanceMatrix2);
            counter++;
        } while (System.currentTimeMillis() - time0 < 1000 || counter < 10);

        float t = (float) (System.currentTimeMillis() - time0) / counter;
        System.out.println("\nAverage time Greedy local search: " + t);
    }
}