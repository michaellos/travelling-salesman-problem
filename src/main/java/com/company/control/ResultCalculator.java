package com.company.control;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResultCalculator {

    public static int calculateTotalDistance(int[] placePermutation, int[][] distanceMatrix) {
        int totalDistance = 0;
        for (int i = 0; i < placePermutation.length; i++) {
            totalDistance += distanceMatrix[placePermutation[i] - 1][placePermutation[(i + 1) % placePermutation.length] - 1];
        }
        return totalDistance;
    }

    public static double calculatePathSimilarity(int[] placePermutation, int[] placeOptimalPermutation) {
        double theSameRoad = 0;
        List<Integer> placeOptimalPermutationList = Arrays.stream(placeOptimalPermutation).boxed().collect(Collectors.toList());
        for (int i = 0; i < placePermutation.length; i++) {
            int place = placePermutation[i];
            int neighborPlaceFromPermutation = placePermutation[(i + 1) % placePermutation.length];
            int neighborPlaceFromOptimalPermutation = placeOptimalPermutation[(placeOptimalPermutationList.indexOf(place) + 1) % placeOptimalPermutation.length];
            if (neighborPlaceFromPermutation == neighborPlaceFromOptimalPermutation) {
                theSameRoad++;
            }
        }
        return theSameRoad / placePermutation.length;
    }
}