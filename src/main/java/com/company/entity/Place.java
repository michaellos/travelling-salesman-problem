package com.company.entity;

public class Place {

    private int id;
    private double xCoordinate;
    private double yCoordinate;

    public Place(int id, double xCoordinate, double yCoordinate) {
        this.id = id;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getId() {
        return id;
    }

    public double getXCoordinate() {
        return xCoordinate;
    }

    public double getYCoordinate() {
        return yCoordinate;
    }
}
