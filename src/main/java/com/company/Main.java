package com.company;

import com.company.algorithm.HeuristicsAlgorithm;
import com.company.algorithm.GreedyLocalSearchAlgorithm;
import com.company.algorithm.RandomAlgorithm;
import com.company.algorithm.SteepestLocalSearchAlgorithm;
import com.company.control.DatasetParser;
import com.company.control.ResultCalculator;
import com.company.control.ResultToCsvWriter;
import com.company.control.StandardDeviation;

import java.io.File;
import java.io.IOException;

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

        int[] optimalPermutation = DatasetParser.loadOptimalPermutation(new File(EUC_2D_OPTIMAL_RESULT_PATH + BERLIN52 + OPTIMAL_RESULT_EXTENSION).getAbsolutePath());
        int optimalDistance = ResultCalculator.calculateTotalDistance(optimalPermutation, distanceMatrix);

        //pomiar jakości

        int greedySumResult = 0;
        int greedyMinimumResult = Integer.MAX_VALUE;
        int[] greedyResults = new int[10];
        int counter = 0;
        do {
            int[] greedyPath = GreedyLocalSearchAlgorithm.findPath(distanceMatrix);
            int greedyResult = ResultCalculator.calculateTotalDistance(greedyPath, distanceMatrix) - optimalDistance;
            greedyResults[counter] = greedyResult;
            greedySumResult += greedyResult;
            if (greedyResult < greedyMinimumResult) {
                greedyMinimumResult = greedyResult;
            }
            counter++;
        } while (counter < 10);
        float greedyAverageResult = (float) greedySumResult / counter;
        double greedyStandardDeviation = StandardDeviation.calculate(greedyResults);

        int steepestSumResult = 10;
        int steepestMinimumResult = Integer.MAX_VALUE;
        int[] steepestResults = new int[10];
        counter = 0;
        do {
            int[] steepestPath = SteepestLocalSearchAlgorithm.findPath(distanceMatrix);
            int steepestResult = ResultCalculator.calculateTotalDistance(steepestPath, distanceMatrix) - optimalDistance;
            steepestResults[counter] = steepestResult;
            steepestSumResult += steepestResult;
            if (steepestResult < steepestMinimumResult) {
                steepestMinimumResult = steepestResult;
            }
            counter++;
        } while (counter < 10);
        float steepestAverageResult = (float) steepestSumResult / counter;
        double steepestStandardDeviation = StandardDeviation.calculate(steepestResults);

        int randomSumResult = 0;
        int randomMinimumResult = Integer.MAX_VALUE;
        int[] randomResults = new int[10];
        counter = 0;
        do {
            int[] randomPath = RandomAlgorithm.findPath(distanceMatrix);
            int randomResult = ResultCalculator.calculateTotalDistance(randomPath, distanceMatrix) - optimalDistance;
            randomResults[counter] = randomResult;
            randomSumResult += randomResult;
            if (randomResult < randomMinimumResult) {
                randomMinimumResult = randomResult;
            }
            counter++;
        } while (counter < 10);
        float randomAverageResult = (float) randomSumResult / counter;
        double randomStandardDeviation = StandardDeviation.calculate(randomResults);

        int heuristicSumResult = 0;
        int heuristicMinimumResult = Integer.MAX_VALUE;
        int[] heuristicResults = new int[10];
        counter = 0;
        do {
            int[] heuristicPath = HeuristicsAlgorithm.findPath(distanceMatrix);
            int heuristicResult = ResultCalculator.calculateTotalDistance(heuristicPath, distanceMatrix) - optimalDistance;
            heuristicResults[counter] = heuristicResult;
            heuristicSumResult += heuristicResult;
            if (heuristicResult < heuristicMinimumResult) {
                heuristicMinimumResult = heuristicResult;
            }
            counter++;
        } while (counter < 10);
        float heuristicAverageResult = (float) heuristicSumResult / counter;
        double heuristicStandardDeviation = StandardDeviation.calculate(heuristicResults);

        // pomiar czasu

        counter = 0;
        long endTime;
        long startTime = System.currentTimeMillis();
        do {
            GreedyLocalSearchAlgorithm.findPath(distanceMatrix);
            endTime = System.currentTimeMillis();
            counter++;
        } while (endTime - startTime < 1000 || counter < 10);

        float greedyAverageTime = (float) (endTime - startTime) / counter;

        counter = 0;
        startTime = System.currentTimeMillis();
        do {
            SteepestLocalSearchAlgorithm.findPath(distanceMatrix);
            endTime = System.currentTimeMillis();
            counter++;
        } while (endTime - startTime < 1000 || counter < 10);

        float steepestAverageTime = (float) (endTime - startTime) / counter;

        counter = 0;
        startTime = System.currentTimeMillis();
        do {
            RandomAlgorithm.findPath(distanceMatrix);
            endTime = System.currentTimeMillis();
            counter++;
        } while (endTime - startTime < 1000 || counter < 10);

        float randomAverageTime = (float) (endTime - startTime) / counter;

        counter = 0;
        startTime = System.currentTimeMillis();
        do {
            HeuristicsAlgorithm.findPath(distanceMatrix);
            endTime = System.currentTimeMillis();
            counter++;
        } while (endTime - startTime < 1000 || counter < 10);

        float heuristicAverageTime = (float) (endTime - startTime) / counter;

        try {
            ResultToCsvWriter resultToCsvWriter = new ResultToCsvWriter();
            resultToCsvWriter.addRow(GreedyLocalSearchAlgorithm.class.getSimpleName(), greedyAverageResult, greedyMinimumResult, greedyAverageTime, greedyStandardDeviation);
            resultToCsvWriter.addRow(SteepestLocalSearchAlgorithm.class.getSimpleName(), steepestAverageResult, steepestMinimumResult, steepestAverageTime, steepestStandardDeviation);
            resultToCsvWriter.addRow(RandomAlgorithm.class.getSimpleName(), randomAverageResult, randomMinimumResult, randomAverageTime, randomStandardDeviation);
            resultToCsvWriter.addRow(HeuristicsAlgorithm.class.getSimpleName(), heuristicAverageResult, heuristicMinimumResult, heuristicAverageTime, heuristicStandardDeviation);
            resultToCsvWriter.saveFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}