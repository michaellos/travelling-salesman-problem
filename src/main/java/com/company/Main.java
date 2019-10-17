package com.company;

import com.company.control.DatasetParser;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

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


        double[][] distanceMatrix = DatasetParser.loadDatasetEuc2D(new File("src/main/resources/euc_2D/berlin52.tsp").getAbsolutePath());
        for (double[] row : distanceMatrix) {
            for (double distance : row) {
                System.out.print(distance + " ");
            }
            System.out.println();
        }
    }
}
