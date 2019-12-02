package com.company.entity;

public class ResultEntity {

    private int[] startPath;
    private int[] finalPath;
    private long stepNumber;
    private long visitSolutionNumber;

    public ResultEntity(int[] startPath, int[] finalPath, long stepNumber, long visitSolutionNumber) {
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

    public long getStepNumber() {
        return stepNumber;
    }

    public long getVisitSolutionNumber() {
        return visitSolutionNumber;
    }
}
