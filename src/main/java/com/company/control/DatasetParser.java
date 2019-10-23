package com.company.control;

import com.company.entity.Place;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatasetParser {

    public static int[][] loadDatasetEuc2D(String filename) {
        List<Place> places = getPlacesEuc2D(filename);
        int size = places.size();
        int[][] placesMatrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    placesMatrix[i][i] = 0;
                } else {
                    placesMatrix[i][j] = (int) Math.round(getEuclideanDistanceBetweenTwoPlaces(places.get(i), places.get(j)));
                }
            }
        }

        return placesMatrix;
    }

    public static int[] loadOptimalPermutation(String filename) {
        List<Integer> optimalPermutation = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
            String line;
            while ((line = br.readLine()) != null) {
                optimalPermutation.add(Integer.parseInt(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return optimalPermutation.stream().mapToInt(Integer::intValue).toArray();
    }

    private static List<Place> getPlacesEuc2D(String filename) {
        List<Place> places = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] args = line.trim().replaceAll(" +", " ").split(" ");
                places.add(new Place(Integer.parseInt(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return places;
    }

    private static double getEuclideanDistanceBetweenTwoPlaces(Place place1, Place place2) {
        return Math.sqrt(getDistanceToSecondPower(place1.getXCoordinate(), place2.getXCoordinate()) + getDistanceToSecondPower(place1.getYCoordinate(), place2.getYCoordinate()));
    }

    private static double getDistanceToSecondPower(double coordinate1, double coordinate2) {
        return Math.pow(Math.abs(coordinate1 - coordinate2), 2.0);
    }
}