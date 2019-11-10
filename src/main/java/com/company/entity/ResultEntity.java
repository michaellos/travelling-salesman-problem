package com.company.entity;

public class ResultEntity {

    private int[] startPath;
    private int[] finalPath;
    private int stepNumber;
    private int visitSolutionNumber;

    public ResultEntity(int[] startPath, int[] finalPath, int stepNumber, int visitSolutionNumber) {
        this.startPath = startPath;
        this.finalPath = finalPath;
        this.stepNumber = stepNumber;
        this.visitSolutionNumber = visitSolutionNumber;
    }

    public int[] getStartPath() {
        return startPath;
    }

    public int[] getFinalPath() {
        return finalPath;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public int getVisitSolutionNumber() {
        return visitSolutionNumber;
    }
}
