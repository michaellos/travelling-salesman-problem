package com.company;

import com.company.control.DatasetParser;
import com.company.control.ResultCalculator;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

import static com.company.entity.NameConstants.*;

public class Main {

    private static Random r = new Random();

    private static void shuffle(int[] cities, int n) {
        for (int i = n - 1; i >= 0; i--) {
            int id = r.nextInt(i + 1);
            int tmp = cities[id];
            cities[id] = cities[i];
            cities[i] = tmp;
        }
    }

    private static void shuffleXOR(int[] cities, int n) {
        for (int i = n - 1; i >= 0; i--) {
            int id = r.nextInt(i + 1);
            if (id != i) {
                cities[id] ^= cities[i];
                cities[i] ^= cities[id];
                cities[id] ^= cities[i];
            }
        }
    }

    public static void main(String[] args) {

        int counter = 0;
        int n = 10;
        int[] cities = new int[n];
        for (int i = 0; i < n; i++) {
            cities[i] = i + 1;
        }

        long time0 = System.currentTimeMillis();

        do {
            shuffle(cities, n);
            counter++;
        } while (System.currentTimeMillis() - time0 < 1000 || counter < 10);

        Arrays.stream(cities).forEach(city -> System.out.print(city + " "));
        float t = (float) (System.currentTimeMillis() - time0) / counter;
        System.out.println("\nAverage time: " + t);


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
    }
}