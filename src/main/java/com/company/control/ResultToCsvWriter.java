package com.company.control;

import java.io.FileWriter;
import java.io.IOException;

public class ResultToCsvWriter {

    private FileWriter csvWriter;

    public ResultToCsvWriter() throws IOException {
        this.csvWriter = new FileWriter("src/main/resources/out/results.csv");

        csvWriter.append("Algorithm")
                .append(",")
                .append("AverageResult")
                .append(",")
                .append("BestResult")
                .append(",")
                .append("AverageTime")
                .append("\n");
    }

    public void addRow(String algorithm, float averageResult, int bestResult, float averageTime) throws IOException {
        csvWriter.append(algorithm)
                .append(",")
                .append(String.valueOf(averageResult))
                .append(",")
                .append(String.valueOf(bestResult))
                .append(",")
                .append(String.valueOf(averageTime))
                .append("\n");
    }

    public void saveFile() throws IOException {
        csvWriter.flush();
        csvWriter.close();
    }
}
