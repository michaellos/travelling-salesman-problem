package com.company;

import com.company.algorithm.*;
import com.company.control.*;
import com.company.entity.ResultEntity;

import java.io.File;
import java.io.IOException;

import static com.company.entity.NameConstants.*;


public class Main {

    public static void main(String[] args) {
        generateReport();
    }

    private static void testSimulatedAnnealing() {
        String instance = INSTANCES.get(5);

        int[][] distanceMatrix = DatasetParser.loadDatasetEuc2D(new File(EUC_2D_INSTANCES_PATH + instance + INSTANCE_EXTENSION).getAbsolutePath());

        int[] optimalPermutation = DatasetParser.loadOptimalPermutation(new File(EUC_2D_OPTIMAL_RESULT_PATH + instance + OPTIMAL_RESULT_EXTENSION).getAbsolutePath());
        int optimalDistance = ResultCalculator.calculateTotalDistance(optimalPermutation, distanceMatrix);

        int[] startingPath = new int[distanceMatrix.length];

        ResultEntity resultEntity = SimulatedAnnealingAlgorithm.findPath(distanceMatrix, startingPath);
        int[] SAPath = resultEntity.getFinalPath();
        int SAresult = ResultCalculator.calculateTotalDistance(SAPath, distanceMatrix) - optimalDistance;

        resultEntity = GreedyLocalSearchAlgorithm.findPath(distanceMatrix, startingPath);
        int[] greedyPath = resultEntity.getFinalPath();
        int greedyRresult = ResultCalculator.calculateTotalDistance(greedyPath, distanceMatrix) - optimalDistance;


        int[] randomPath = RandomAlgorithm.findPath(distanceMatrix, startingPath, 1);
        int randomRresult = ResultCalculator.calculateTotalDistance(randomPath, distanceMatrix) - optimalDistance;

        System.out.println("Instance: " + instance);
        System.out.println("SA: " + SAresult);
        System.out.println("Greedy: " + greedyRresult);
        System.out.println("Random: " + randomRresult);


        instance = INSTANCES.get(0);

        distanceMatrix = DatasetParser.loadDatasetEuc2D(new File(EUC_2D_INSTANCES_PATH + instance + INSTANCE_EXTENSION).getAbsolutePath());

        optimalPermutation = DatasetParser.loadOptimalPermutation(new File(EUC_2D_OPTIMAL_RESULT_PATH + instance + OPTIMAL_RESULT_EXTENSION).getAbsolutePath());
        optimalDistance = ResultCalculator.calculateTotalDistance(optimalPermutation, distanceMatrix);

        startingPath = new int[distanceMatrix.length];

        resultEntity = SimulatedAnnealingAlgorithm.findPath(distanceMatrix, startingPath);
        SAPath = resultEntity.getFinalPath();
        SAresult = ResultCalculator.calculateTotalDistance(SAPath, distanceMatrix) - optimalDistance;

        resultEntity = GreedyLocalSearchAlgorithm.findPath(distanceMatrix, startingPath);
        greedyPath = resultEntity.getFinalPath();
        greedyRresult = ResultCalculator.calculateTotalDistance(greedyPath, distanceMatrix) - optimalDistance;


        randomPath = RandomAlgorithm.findPath(distanceMatrix, startingPath, 1);
        randomRresult = ResultCalculator.calculateTotalDistance(randomPath, distanceMatrix) - optimalDistance;

        System.out.println("Instance: " + instance);
        System.out.println("SA: " + SAresult);
        System.out.println("Greedy: " + greedyRresult);
        System.out.println("Random: " + randomRresult);
    }

    private static void generateReport() {
        for (String instance : INSTANCES) {
            System.out.println(instance);
            int[][] distanceMatrix = DatasetParser.loadDatasetEuc2D(new File(EUC_2D_INSTANCES_PATH + instance + INSTANCE_EXTENSION).getAbsolutePath());

            int[] optimalPermutation = DatasetParser.loadOptimalPermutation(new File(EUC_2D_OPTIMAL_RESULT_PATH + instance + OPTIMAL_RESULT_EXTENSION).getAbsolutePath());
            int optimalDistance = ResultCalculator.calculateTotalDistance(optimalPermutation, distanceMatrix);

            int[] startingPath = new int[distanceMatrix.length];
            int[][] cloneDistanceMatrix = new int[distanceMatrix.length][];

            // pomiar czasu

            int counter = 0;
            long endTime;
            long startTime = System.currentTimeMillis();
            do {
                GreedyLocalSearchAlgorithm.findPath(distanceMatrix, startingPath);
                endTime = System.currentTimeMillis();
                counter++;
            } while (endTime - startTime < 1000 || counter < 10);

            float greedyAverageTime = (float) (endTime - startTime) / counter;

            counter = 0;
            startTime = System.currentTimeMillis();
            do {
                SteepestLocalSearchAlgorithm.findPath(distanceMatrix, startingPath);
                endTime = System.currentTimeMillis();
                counter++;
            } while (endTime - startTime < 1000 || counter < 10);

            float steepestAverageTime = (float) (endTime - startTime) / counter;

            float randomBorderTime = Math.max(greedyAverageTime, steepestAverageTime);

            counter = 0;
            startTime = System.currentTimeMillis();
            do {
                RandomAlgorithm.findPath(distanceMatrix, startingPath, randomBorderTime);
                endTime = System.currentTimeMillis();
                counter++;
            } while (endTime - startTime < 1000 || counter < 10);

            float randomAverageTime = (float) (endTime - startTime) / counter;

            counter = 0;
            startTime = System.currentTimeMillis();
            do {
                HeuristicAlgorithm.findPath(distanceMatrix, startingPath, cloneDistanceMatrix);
                endTime = System.currentTimeMillis();
                counter++;
            } while (endTime - startTime < 1000 || counter < 10);

            float heuristicAverageTime = (float) (endTime - startTime) / counter;

            counter = 0;
            startTime = System.currentTimeMillis();
            do {
                SimulatedAnnealingAlgorithm.findPath(distanceMatrix, startingPath);
                endTime = System.currentTimeMillis();
                counter++;
            } while (endTime - startTime < 1000 || counter < 10);

            float simulatedAnnealingAverageTime = (float) (endTime - startTime) / counter;

            counter = 0;
            startTime = System.currentTimeMillis();
            do {
                TabuSearchAlgorithm.findPath(distanceMatrix, startingPath);
                endTime = System.currentTimeMillis();
                counter++;
            } while (endTime - startTime < 1000 || counter < 10);

            float tabuSearchAverageTime = (float) (endTime - startTime) / counter;

            //pomiar jakości

            int greedySumResult = 0;
            int greedyMinimumResult = Integer.MAX_VALUE;
            int greedySumStepNumber = 0;
            int greedySumVisitSolutionNumber = 0;
            int[] greedyResults = new int[10];
            counter = 0;
            do {
                ResultEntity resultEntity = GreedyLocalSearchAlgorithm.findPath(distanceMatrix, startingPath);
                int[] greedyPath = resultEntity.getFinalPath();
                int greedyResult = ResultCalculator.calculateTotalDistance(greedyPath, distanceMatrix) - optimalDistance;
                greedyResults[counter] = greedyResult;
                greedySumResult += greedyResult;
                greedySumStepNumber += resultEntity.getStepNumber();
                greedySumVisitSolutionNumber += resultEntity.getVisitSolutionNumber();
                if (greedyResult < greedyMinimumResult) {
                    greedyMinimumResult = greedyResult;
                }
                counter++;
            } while (counter < 10);
            float greedyAverageResult = (float) greedySumResult / counter;
            double greedyStandardDeviation = StandardDeviation.calculate(greedyResults);
            float greedyAverageStepNumber = (float) greedySumStepNumber / counter;
            float greedyAverageVisitSolutionNumber = (float) greedySumVisitSolutionNumber / counter;

            int steepestSumResult = 0;
            int steepestMinimumResult = Integer.MAX_VALUE;
            int steepestSumStepNumber = 0;
            int steepestSumVisitSolutionNumber = 0;
            int[] steepestResults = new int[10];
            counter = 0;
            do {
                ResultEntity resultEntity = SteepestLocalSearchAlgorithm.findPath(distanceMatrix, startingPath);
                int[] steepestPath = resultEntity.getFinalPath();
                int steepestResult = ResultCalculator.calculateTotalDistance(steepestPath, distanceMatrix) - optimalDistance;
                steepestResults[counter] = steepestResult;
                steepestSumResult += steepestResult;
                steepestSumStepNumber += resultEntity.getStepNumber();
                steepestSumVisitSolutionNumber += resultEntity.getVisitSolutionNumber();
                if (steepestResult < steepestMinimumResult) {
                    steepestMinimumResult = steepestResult;
                }
                counter++;
            } while (counter < 10);
            float steepestAverageResult = (float) steepestSumResult / counter;
            double steepestStandardDeviation = StandardDeviation.calculate(steepestResults);
            float steepestAverageStepNumber = (float) steepestSumStepNumber / counter;
            float steepestAverageVisitSolutionNumber = (float) steepestSumVisitSolutionNumber / counter;

            int randomSumResult = 0;
            int randomMinimumResult = Integer.MAX_VALUE;
            int[] randomResults = new int[10];
            counter = 0;
            do {
                int[] randomPath = RandomAlgorithm.findPath(distanceMatrix, startingPath, randomBorderTime);
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
                int[] heuristicPath = HeuristicAlgorithm.findPath(distanceMatrix, startingPath, cloneDistanceMatrix);
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

            int simulatedAnnealingSumResult = 0;
            int simulatedAnnealingMinimumResult = Integer.MAX_VALUE;
            long simulatedAnnealingSumStepNumber = 0;
            long simulatedAnnealingSumVisitSolutionNumber = 0;
            int[] simulatedAnnealingResults = new int[10];
            counter = 0;
            do {
                ResultEntity resultEntity = SimulatedAnnealingAlgorithm.findPath(distanceMatrix, startingPath);
                int[] simulatedAnnealingPath = resultEntity.getFinalPath();
                int simulatedAnnealingResult = ResultCalculator.calculateTotalDistance(simulatedAnnealingPath, distanceMatrix) - optimalDistance;
                simulatedAnnealingResults[counter] = simulatedAnnealingResult;
                simulatedAnnealingSumResult += simulatedAnnealingResult;
                simulatedAnnealingSumStepNumber += resultEntity.getStepNumber();
                simulatedAnnealingSumVisitSolutionNumber += resultEntity.getVisitSolutionNumber();
                if (simulatedAnnealingResult < simulatedAnnealingMinimumResult) {
                    simulatedAnnealingMinimumResult = simulatedAnnealingResult;
                }
                counter++;
            } while (counter < 10);
            float simulatedAnnealingAverageResult = (float) simulatedAnnealingSumResult / counter;
            double simulatedAnnealingStandardDeviation = StandardDeviation.calculate(simulatedAnnealingResults);
            float simulatedAnnealingAverageStepNumber = (float) simulatedAnnealingSumStepNumber / counter;
            float simulatedAnnealingAverageVisitSolutionNumber = (float) simulatedAnnealingSumVisitSolutionNumber / counter;

            int tabuSearchSumResult = 0;
            int tabuSearchMinimumResult = Integer.MAX_VALUE;
            int tabuSearchSumStepNumber = 0;
            int tabuSearchSumVisitSolutionNumber = 0;
            int[] tabuSearchResults = new int[10];
            counter = 0;
            do {
                ResultEntity resultEntity = TabuSearchAlgorithm.findPath(distanceMatrix, startingPath);
                int[] tabuSearchPath = resultEntity.getFinalPath();
                int tabuSearchResult = ResultCalculator.calculateTotalDistance(tabuSearchPath, distanceMatrix) - optimalDistance;
                tabuSearchResults[counter] = tabuSearchResult;
                tabuSearchSumResult += tabuSearchResult;
                tabuSearchSumStepNumber += resultEntity.getStepNumber();
                tabuSearchSumVisitSolutionNumber += resultEntity.getVisitSolutionNumber();
                if (tabuSearchResult < tabuSearchMinimumResult) {
                    tabuSearchMinimumResult = tabuSearchResult;
                }
                counter++;
            } while (counter < 10);
            float tabuSearchAverageResult = (float) tabuSearchSumResult / counter;
            double tabuSearchStandardDeviation = StandardDeviation.calculate(tabuSearchResults);
            float tabuSearchAverageStepNumber = (float) tabuSearchSumStepNumber / counter;
            float tabuSearchAverageVisitSolutionNumber = (float) tabuSearchSumVisitSolutionNumber / counter;

            // jakość w czasie (średni błąd bezwzględny / średni czas działania)

            float greedyEfficiency = greedyAverageResult / greedyAverageTime;
            float steepestEfficiency = steepestAverageResult / steepestAverageTime;
            float randomEfficiency = randomAverageResult / randomAverageTime;
            float heuristicEfficiency = heuristicAverageResult / heuristicAverageTime;
            float simulatedAnnealingEfficiency = simulatedAnnealingAverageResult / simulatedAnnealingAverageTime;
            float tabuSearchEfficiency = tabuSearchAverageResult / tabuSearchAverageTime;

            try {
                ResultTask2ToCsvWriter resultTask2ToCsvWriter = new ResultTask2ToCsvWriter(instance);
                resultTask2ToCsvWriter.addRow(GreedyLocalSearchAlgorithm.class.getSimpleName(), greedyAverageResult, greedyMinimumResult, greedyAverageTime, greedyStandardDeviation, greedyEfficiency, greedyAverageStepNumber, greedyAverageVisitSolutionNumber);
                resultTask2ToCsvWriter.addRow(SteepestLocalSearchAlgorithm.class.getSimpleName(), steepestAverageResult, steepestMinimumResult, steepestAverageTime, steepestStandardDeviation, steepestEfficiency, steepestAverageStepNumber, steepestAverageVisitSolutionNumber);
                resultTask2ToCsvWriter.addRow(RandomAlgorithm.class.getSimpleName(), randomAverageResult, randomMinimumResult, randomAverageTime, randomStandardDeviation, randomEfficiency, 0, 0);
                resultTask2ToCsvWriter.addRow(HeuristicAlgorithm.class.getSimpleName(), heuristicAverageResult, heuristicMinimumResult, heuristicAverageTime, heuristicStandardDeviation, heuristicEfficiency, 0, 0);
                resultTask2ToCsvWriter.addRow(SimulatedAnnealingAlgorithm.class.getSimpleName(), simulatedAnnealingAverageResult, simulatedAnnealingMinimumResult, simulatedAnnealingAverageTime, simulatedAnnealingStandardDeviation, simulatedAnnealingEfficiency, simulatedAnnealingAverageStepNumber, simulatedAnnealingAverageVisitSolutionNumber);
                resultTask2ToCsvWriter.addRow(TabuSearchAlgorithm.class.getSimpleName(), tabuSearchAverageResult, tabuSearchMinimumResult, tabuSearchAverageTime, tabuSearchStandardDeviation, tabuSearchEfficiency, tabuSearchAverageStepNumber, tabuSearchAverageVisitSolutionNumber);
                resultTask2ToCsvWriter.saveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // zad 3

            counter = 0;
            int[] greedyStartResults = new int[200];
            int[] greedyFinalResults = new int[200];
            do {
                ResultEntity resultEntity = GreedyLocalSearchAlgorithm.findPath(distanceMatrix, startingPath);
                greedyStartResults[counter] = ResultCalculator.calculateTotalDistance(resultEntity.getStartPath(), distanceMatrix) - optimalDistance;
                greedyFinalResults[counter] = ResultCalculator.calculateTotalDistance(resultEntity.getFinalPath(), distanceMatrix) - optimalDistance;
                counter++;
            } while (counter < 200);

            counter = 0;
            int[] steepestStartResults = new int[200];
            int[] steepestFinalResults = new int[200];
            do {
                ResultEntity resultEntity = SteepestLocalSearchAlgorithm.findPath(distanceMatrix, startingPath);
                steepestStartResults[counter] = ResultCalculator.calculateTotalDistance(resultEntity.getStartPath(), distanceMatrix) - optimalDistance;
                steepestFinalResults[counter] = ResultCalculator.calculateTotalDistance(resultEntity.getFinalPath(), distanceMatrix) - optimalDistance;
                counter++;
            } while (counter < 200);

            try {
                ResultTask3ToCsvWriter resultTask3ToCsvWriter = new ResultTask3ToCsvWriter(instance);
                resultTask3ToCsvWriter.addStartResultsToRow(GreedyLocalSearchAlgorithm.class.getSimpleName(), greedyStartResults);
                resultTask3ToCsvWriter.addFinalResultsToRow(GreedyLocalSearchAlgorithm.class.getSimpleName(), greedyFinalResults);
                resultTask3ToCsvWriter.addStartResultsToRow(SteepestLocalSearchAlgorithm.class.getSimpleName(), steepestStartResults);
                resultTask3ToCsvWriter.addFinalResultsToRow(SteepestLocalSearchAlgorithm.class.getSimpleName(), steepestFinalResults);
                resultTask3ToCsvWriter.saveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // zad 4

            counter = 0;
            int greedySumDistance = 0;
            int greedyMinimumDistance = Integer.MAX_VALUE;
            float[] greedyAverageDistances = new float[300];
            int[] greedyMinimumDistances = new int[300];
            do {
                int[] greedyPath = GreedyLocalSearchAlgorithm.findPath(distanceMatrix, startingPath).getFinalPath();
                int greedyDistance = ResultCalculator.calculateTotalDistance(greedyPath, distanceMatrix);
                greedySumDistance += greedyDistance;
                if (greedyDistance < greedyMinimumDistance) {
                    greedyMinimumDistance = greedyDistance;
                }
                greedyAverageDistances[counter] = (float) greedySumDistance / (counter + 1);
                greedyMinimumDistances[counter] = greedyMinimumDistance;
                counter++;
            } while (counter < 300);

            counter = 0;
            int steepestSumDistance = 0;
            int steepestMinimumDistance = Integer.MAX_VALUE;
            float[] steepestAverageDistances = new float[300];
            int[] steepestMinimumDistances = new int[300];
            do {
                int[] steepestPath = SteepestLocalSearchAlgorithm.findPath(distanceMatrix, startingPath).getFinalPath();
                int steepestDistance = ResultCalculator.calculateTotalDistance(steepestPath, distanceMatrix);
                steepestSumDistance += steepestDistance;
                if (steepestDistance < steepestMinimumDistance) {
                    steepestMinimumDistance = steepestDistance;
                }
                steepestAverageDistances[counter] = (float) steepestSumDistance / (counter + 1);
                steepestMinimumDistances[counter] = steepestMinimumDistance;
                counter++;
            } while (counter < 300);

            try {
                ResultTask4ToCsvWriter resultTask4ToCsvWriter = new ResultTask4ToCsvWriter(instance);
                resultTask4ToCsvWriter.addAverageDistancesToRow(GreedyLocalSearchAlgorithm.class.getSimpleName(), greedyAverageDistances);
                resultTask4ToCsvWriter.addMinimumDistancesToRow(GreedyLocalSearchAlgorithm.class.getSimpleName(), greedyMinimumDistances);
                resultTask4ToCsvWriter.addAverageDistancesToRow(SteepestLocalSearchAlgorithm.class.getSimpleName(), steepestAverageDistances);
                resultTask4ToCsvWriter.addMinimumDistancesToRow(SteepestLocalSearchAlgorithm.class.getSimpleName(), steepestMinimumDistances);
                resultTask4ToCsvWriter.saveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // zad 5

            greedyResults = new int[100];
            double[] greedyPathSimilarities = new double[100];
            counter = 0;
            do {
                int[] greedyPath = GreedyLocalSearchAlgorithm.findPath(distanceMatrix, startingPath).getFinalPath();
                greedyResults[counter] = ResultCalculator.calculateTotalDistance(greedyPath, distanceMatrix) - optimalDistance;
                greedyPathSimilarities[counter] = ResultCalculator.calculatePathSimilarity(greedyPath, optimalPermutation);
                counter++;
            } while (counter < 100);

            steepestResults = new int[100];
            double[] steepestPathSimilarities = new double[100];
            counter = 0;
            do {
                int[] steepestPath = SteepestLocalSearchAlgorithm.findPath(distanceMatrix, startingPath).getFinalPath();
                steepestResults[counter] = ResultCalculator.calculateTotalDistance(steepestPath, distanceMatrix) - optimalDistance;
                steepestPathSimilarities[counter] = ResultCalculator.calculatePathSimilarity(steepestPath, optimalPermutation);
                counter++;
            } while (counter < 100);

            randomResults = new int[100];
            double[] randomPathSimilarities = new double[100];
            counter = 0;
            do {
                int[] randomPath = RandomAlgorithm.findPath(distanceMatrix, startingPath, randomBorderTime);
                randomResults[counter] = ResultCalculator.calculateTotalDistance(randomPath, distanceMatrix) - optimalDistance;
                randomPathSimilarities[counter] = ResultCalculator.calculatePathSimilarity(randomPath, optimalPermutation);
                counter++;
            } while (counter < 100);

            heuristicResults = new int[100];
            double[] heuristicPathSimilarities = new double[100];
            counter = 0;
            do {
                int[] heuristicPath = HeuristicAlgorithm.findPath(distanceMatrix, startingPath, cloneDistanceMatrix);
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
                resultTask5ToCsvWriter.addResultsToRow(HeuristicAlgorithm.class.getSimpleName(), heuristicResults);
                resultTask5ToCsvWriter.addSimilaritiesToRow(HeuristicAlgorithm.class.getSimpleName(), heuristicPathSimilarities);
                resultTask5ToCsvWriter.saveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}