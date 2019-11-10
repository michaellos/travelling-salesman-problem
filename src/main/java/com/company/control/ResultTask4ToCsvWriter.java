package com.company.control;

import java.io.FileWriter;
import java.io.IOException;

public class ResultTask4ToCsvWriter {

    private FileWriter csvWriter;

    public ResultTask4ToCsvWriter(String instance) throws IOException {
        this.csvWriter = new FileWriter("src/main/resources/out/results_task_4_" + instance + ".csv");

        csvWriter.append("Algorithm")
                .append(",")
                .append("Type");

        for (int i = 0; i < 300; i++) {
            csvWriter.append(",").append("Distance ").append(String.valueOf(i + 1));
        }

        csvWriter.append("\n");
    }

    public void addMinimumDistancesToRow(String algorithm, int[] distances) throws IOException {
        csvWriter.append(algorithm)
                .append(",")
                .append("MinimumDistance")
                .append(",")
                .append(minimumResultsToRow(distances));
    }

    public void addAverageDistancesToRow(String algorithm, float[] distances) throws IOException {
        csvWriter.append(algorithm)
                .append(",")
                .append("AverageDistance")
                .append(",")
                .append(averageResultsToRow(distances));
    }

    public void saveFile() throws IOException {
        csvWriter.flush();
        csvWriter.close();
    }

    private String minimumResultsToRow(int[] results) {
        StringBuilder row = new StringBuilder();
        for (int result : results) {
            row.append(result).append(",");
        }
        return row.substring(0, row.length() - 1) + "\n";
    }

    private String averageResultsToRow(float[] results) {
        StringBuilder row = new StringBuilder();
        for (float result : results) {
            row.append(result).append(",");
        }
        return row.substring(0, row.length() - 1) + "\n";
    }
}
