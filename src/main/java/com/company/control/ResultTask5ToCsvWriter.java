package com.company.control;

import java.io.FileWriter;
import java.io.IOException;

public class ResultTask5ToCsvWriter {

    private FileWriter csvWriter;

    public ResultTask5ToCsvWriter(String instance) throws IOException {
        this.csvWriter = new FileWriter("src/main/resources/out/results_task_5_" + instance + ".csv");

        csvWriter.append("Algorithm")
                .append(",")
                .append("Type");

        for (int i = 0; i < 100; i++) {
            csvWriter.append(",").append("Result ").append(String.valueOf(i + 1));
        }

        csvWriter.append("\n");
    }

    public void addResultsToRow(String algorithm, int[] results) throws IOException {
        csvWriter.append(algorithm)
                .append(",")
                .append("Results")
                .append(",")
                .append(resultsToRow(results));
    }

    public void addSimilaritiesToRow(String algorithm, double[] similarities) throws IOException {
        csvWriter.append(algorithm)
                .append(",")
                .append("Similarities")
                .append(",")
                .append(similaritiesToRow(similarities));
    }

    public void saveFile() throws IOException {
        csvWriter.flush();
        csvWriter.close();
    }

    private String resultsToRow(int[] results) {
        StringBuilder row = new StringBuilder();
        for (int result : results) {
            row.append(result).append(",");
        }
        return row.substring(0, row.length() - 1) + "\n";
    }

    private String similaritiesToRow(double[] similarities) {
        StringBuilder row = new StringBuilder();
        for (double result : similarities) {
            row.append(result).append(",");
        }
        return row.substring(0, row.length() - 1) + "\n";
    }
}
