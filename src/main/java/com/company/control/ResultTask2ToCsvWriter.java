package com.company.control;

import java.io.FileWriter;
import java.io.IOException;

public class ResultTask2ToCsvWriter {

    private FileWriter csvWriter;

    public ResultTask2ToCsvWriter(String instance) throws IOException {
        this.csvWriter = new FileWriter("src/main/resources/out/results_task_2_" + instance + ".csv");

        csvWriter.append("Algorithm")
                .append(",")
                .append("AverageResult")
                .append(",")
                .append("BestResult")
                .append(",")
                .append("AverageTime")
                .append(",")
                .append("StandardDeviation")
                .append(",")
                .append("Efficiency")
                .append("\n");
    }

    public void addRow(String algorithm, float averageResult, int bestResult, float averageTime, double standardDeviation, float efficiency) throws IOException {
        csvWriter.append(algorithm)
                .append(",")
                .append(String.valueOf(averageResult))
                .append(",")
                .append(String.valueOf(bestResult))
                .append(",")
                .append(String.valueOf(averageTime))
                .append(",")
                .append(String.valueOf(standardDeviation))
                .append(",")
                .append(String.valueOf(efficiency))
                .append("\n");
    }

    public void saveFile() throws IOException {
        csvWriter.flush();
        csvWriter.close();
    }
}
