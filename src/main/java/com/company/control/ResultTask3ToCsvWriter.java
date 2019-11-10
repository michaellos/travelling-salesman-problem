package com.company.control;

import java.io.FileWriter;
import java.io.IOException;

public class ResultTask3ToCsvWriter {

    private FileWriter csvWriter;

    public ResultTask3ToCsvWriter(String instance) throws IOException {
        this.csvWriter = new FileWriter("src/main/resources/out/results_task_3_" + instance + ".csv");

        csvWriter.append("Algorithm")
                .append(",")
                .append("Type");

        for (int i = 0; i < 200; i++) {
            csvWriter.append(",").append("Result ").append(String.valueOf(i + 1));
        }

        csvWriter.append("\n");
    }

    public void addStartResultsToRow(String algorithm, int[] results) throws IOException {
        csvWriter.append(algorithm)
                .append(",")
                .append("StartResult")
                .append(",")
                .append(resultsToRow(results));
    }

    public void addFinalResultsToRow(String algorithm, int[] results) throws IOException {
        csvWriter.append(algorithm)
                .append(",")
                .append("FinalResult")
                .append(",")
                .append(resultsToRow(results));
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
}
