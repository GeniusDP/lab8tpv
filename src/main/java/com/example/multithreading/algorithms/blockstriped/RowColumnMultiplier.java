package com.example.multithreading.algorithms.blockstriped;

import java.util.List;

public class RowColumnMultiplier {
    private final List<List<Integer>> resultMatrix;
    private final List<Integer> row;
    private final ColumnSharer columnSharer;
    private final int rowNumber;
    private int columnNumber;

    public RowColumnMultiplier(List<List<Integer>> resultMatrix, List<Integer> row, ColumnSharer columnSharer, int rowNumber) {
        this.resultMatrix = resultMatrix;
        this.row = row;
        this.columnSharer = columnSharer;
        this.rowNumber = rowNumber;
        this.columnNumber = rowNumber;
    }

    public void multiply() {
        for (int i = 0; i < row.size(); i++) {
            List<Integer> column = columnSharer.getColumn(columnNumber);
            int result = 0;
            for (int j = 0; j < column.size(); j++) {
                result += row.get(j) * column.get(j);
            }
            resultMatrix.get(rowNumber).set(columnNumber, result);
            columnSharer.returnColumn(columnNumber, column);
            columnNumber++;
            columnNumber = columnNumber % resultMatrix.size();
        }
    }
}
