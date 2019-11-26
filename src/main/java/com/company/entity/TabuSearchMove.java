package com.company.entity;

public class TabuSearchMove {

    private int cityA;
    private int cityB;
    private int distance;

    public TabuSearchMove(int cityA, int cityB, int distance) {
        this.cityA = cityA;
        this.cityB = cityB;
        this.distance = distance;
    }

    public int getCityA() {
        return cityA;
    }

    public int getCityB() {
        return cityB;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
