package com.example.multithreading.algorithms.fox;

import static com.example.multithreading.algorithms.fox.FoxAlgoProcessor.BLOCKS_NUMBER;

import java.util.ArrayList;
import java.util.List;

public class MatrixContainer {
    private final int blockSize;
    private final List<List<Integer>> matrix;

    public MatrixContainer(List<List<Integer>> matrix) {
        this.matrix = matrix;
        this.blockSize = matrix.size() / BLOCKS_NUMBER;
    }

    public List<List<Integer>> getSubMatrix(Position position) {
        List<List<Integer>> subMatrix = new ArrayList<>();
        for (int i = position.getY() * blockSize; i < (position.getY() + 1) * blockSize; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = position.getX() * blockSize; j < (position.getX() + 1) * blockSize; j++) {
                row.add(matrix.get(i).get(j));
            }
            subMatrix.add(row);
        }
        return subMatrix;
    }

    public List<List<Integer>> getMatrix() {
        return matrix;
    }

    public int getBlockSize() {
        return blockSize;
    }
}
