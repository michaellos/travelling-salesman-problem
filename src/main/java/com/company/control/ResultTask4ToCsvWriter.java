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
            csvWriter.append(",").append("Result ").append(String.valueOf(i + 1));
        }

        csvWriter.append("\n");
    }

    public void addMinimumResultsToRow(String algorithm, int[] results) throws IOException {
        csvWriter.append(algorithm)
                .append(",")
                .append("MinimumResults")
                .append(",")
                .append(minimumResultsToRow(results));
    }

    public void addAverageResultsToRow(String algorithm, float[] results) throws IOException {
        csvWriter.append(algorithm)
                .append(",")
                .append("AverageResults")
                .append(",")
                .append(averageResultsToRow(results));
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
