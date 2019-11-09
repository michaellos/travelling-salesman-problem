package com.company;

import com.company.algorithm.HeuristicsAlgorithm;
import com.company.algorithm.GreedyLocalSearchAlgorithm;
import com.company.algorithm.RandomAlgorithm;
import com.company.algorithm.SteepestLocalSearchAlgorithm;
import com.company.control.*;

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

        String instance = BERLIN52;

        int[][] distanceMatrix = DatasetParser.loadDatasetEuc2D(new File(EUC_2D_INSTANCES_PATH + instance + INSTANCE_EXTENSION).getAbsolutePath());

        int[] optimalPermutation = DatasetParser.loadOptimalPermutation(new File(EUC_2D_OPTIMAL_RESULT_PATH + instance + OPTIMAL_RESULT_EXTENSION).getAbsolutePath());
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

        // jakość w czasie (średni błąd bezwzględny / średni czas działania)

        float greedyEfficiency = greedyAverageResult / greedyAverageTime;
        float steepestEfficiency = steepestAverageResult / steepestAverageTime;
        float randomEfficiency = randomAverageResult / randomAverageTime;
        float heuristicEfficiency = heuristicAverageResult / heuristicAverageTime;

        try {
            ResultTask2ToCsvWriter resultTask2ToCsvWriter = new ResultTask2ToCsvWriter(instance);
            resultTask2ToCsvWriter.addRow(GreedyLocalSearchAlgorithm.class.getSimpleName(), greedyAverageResult, greedyMinimumResult, greedyAverageTime, greedyStandardDeviation, greedyEfficiency);
            resultTask2ToCsvWriter.addRow(SteepestLocalSearchAlgorithm.class.getSimpleName(), steepestAverageResult, steepestMinimumResult, steepestAverageTime, steepestStandardDeviation, steepestEfficiency);
            resultTask2ToCsvWriter.addRow(RandomAlgorithm.class.getSimpleName(), randomAverageResult, randomMinimumResult, randomAverageTime, randomStandardDeviation, randomEfficiency);
            resultTask2ToCsvWriter.addRow(HeuristicsAlgorithm.class.getSimpleName(), heuristicAverageResult, heuristicMinimumResult, heuristicAverageTime, heuristicStandardDeviation, heuristicEfficiency);
            resultTask2ToCsvWriter.saveFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // zad 3

        // TODO

        // zad 4

        counter = 0;
        greedySumResult = 0;
        greedyMinimumResult = Integer.MAX_VALUE;
        float[] greedyAverageResults = new float[300];
        int[] greedyMinimumResults = new int[300];
        do {
            int[] greedyPath = GreedyLocalSearchAlgorithm.findPath(distanceMatrix);
            int greedyResult = ResultCalculator.calculateTotalDistance(greedyPath, distanceMatrix) - optimalDistance;
            greedySumResult += greedyResult;
            if (greedyResult < greedyMinimumResult) {
                greedyMinimumResult = greedyResult;
            }
            greedyAverageResults[counter] = (float) greedySumResult / (counter + 1);
            greedyMinimumResults[counter] = greedyMinimumResult;
            counter++;
        } while (counter < 300);

        counter = 0;
        steepestSumResult = 0;
        steepestMinimumResult = Integer.MAX_VALUE;
        float[] steepestAverageResults = new float[300];
        int[] steepestMinimumResults = new int[300];
        do {
            int[] steepestPath = SteepestLocalSearchAlgorithm.findPath(distanceMatrix);
            int steepestResult = ResultCalculator.calculateTotalDistance(steepestPath, distanceMatrix) - optimalDistance;
            steepestSumResult += steepestResult;
            if (steepestResult < steepestMinimumResult) {
                steepestMinimumResult = steepestResult;
            }
            steepestAverageResults[counter] = (float) steepestSumResult / (counter + 1);
            steepestMinimumResults[counter] = steepestMinimumResult;
            counter++;
        } while (counter < 300);

        try {
            ResultTask4ToCsvWriter resultTask4ToCsvWriter = new ResultTask4ToCsvWriter(instance);
            resultTask4ToCsvWriter.addAverageResultsToRow(GreedyLocalSearchAlgorithm.class.getSimpleName(), greedyAverageResults);
            resultTask4ToCsvWriter.addMinimumResultsToRow(GreedyLocalSearchAlgorithm.class.getSimpleName(), greedyMinimumResults);
            resultTask4ToCsvWriter.addAverageResultsToRow(SteepestLocalSearchAlgorithm.class.getSimpleName(), steepestAverageResults);
            resultTask4ToCsvWriter.addMinimumResultsToRow(SteepestLocalSearchAlgorithm.class.getSimpleName(), steepestMinimumResults);
            resultTask4ToCsvWriter.saveFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // zad 5

        greedyResults = new int[100];
        double[] greedyPathSimilarities = new double[100];
        counter = 0;
        do {
            int[] greedyPath = GreedyLocalSearchAlgorithm.findPath(distanceMatrix);
            greedyResults[counter] = ResultCalculator.calculateTotalDistance(greedyPath, distanceMatrix) - optimalDistance;
            greedyPathSimilarities[counter] = ResultCalculator.calculatePathSimilarity(greedyPath, optimalPermutation);
            counter++;
        } while (counter < 100);

        steepestResults = new int[100];
        double[] steepestPathSimilarities = new double[100];
        counter = 0;
        do {
            int[] steepestPath = SteepestLocalSearchAlgorithm.findPath(distanceMatrix);
            steepestResults[counter] = ResultCalculator.calculateTotalDistance(steepestPath, distanceMatrix) - optimalDistance;
            steepestPathSimilarities[counter] = ResultCalculator.calculatePathSimilarity(steepestPath, optimalPermutation);
            counter++;
        } while (counter < 100);

        randomResults = new int[100];
        double[] randomPathSimilarities = new double[100];
        counter = 0;
        do {
            int[] randomPath = RandomAlgorithm.findPath(distanceMatrix);
            randomResults[counter] = ResultCalculator.calculateTotalDistance(randomPath, distanceMatrix) - optimalDistance;
            randomPathSimilarities[counter] = ResultCalculator.calculatePathSimilarity(randomPath, optimalPermutation);
            counter++;
        } while (counter < 100);

        heuristicResults = new int[100];
        double[] heuristicPathSimilarities = new double[100];
        counter = 0;
        do {
            int[] heuristicPath = HeuristicsAlgorithm.findPath(distanceMatrix);
            heuristicResults[counter] = ResultCalculator.calculateTotalDistance(heuristicPath, distanceMatrix) - optimalDistance;
            heuristicPathSimilarities[counter] = ResultCalculator.calculatePathSimilarity(heuristicPath, optimalPermutation);
            counter++;
        } while (counter < 100);

        try {
            ResultTask5ToCsvWriter resultTask5ToCsvWriter = new ResultTask5ToCsvWriter(instance);
            resultTask5ToCsvWriter.addResultsToRow(GreedyLocalSearchAlgorithm.class.getSimpleName(), greedyResults);
            resultTask5ToCsvWriter.addSimilaritiesToRow(GreedyLocalSearchAlgorithm.class.getSimpleName(), greedyPathSimilarities);
            resultTask5ToCsvWriter.addResultsToRow(SteepestLocalSearchAlgorithm.class.getSimpleName(), steepestResults);
            resultTask5ToCsvWriter.addSimilaritiesToRow(SteepestLocalSearchAlgorithm.class.getSimpleName(), steepestPathSimilarities);
            resultTask5ToCsvWriter.addResultsToRow(RandomAlgorithm.class.getSimpleName(), randomResults);
            resultTask5ToCsvWriter.addSimilaritiesToRow(RandomAlgorithm.class.getSimpleName(), randomPathSimilarities);
            resultTask5ToCsvWriter.addResultsToRow(HeuristicsAlgorithm.class.getSimpleName(), heuristicResults);
            resultTask5ToCsvWriter.addSimilaritiesToRow(HeuristicsAlgorithm.class.getSimpleName(), heuristicPathSimilarities);
            resultTask5ToCsvWriter.saveFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}