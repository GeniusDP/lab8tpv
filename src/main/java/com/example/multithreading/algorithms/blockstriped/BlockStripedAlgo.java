package com.example.multithreading.algorithms.blockstriped;

import com.example.multithreading.algorithms.AlgoName;
import com.example.multithreading.algorithms.Algorithm;
import com.example.multithreading.utils.MatrixUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;

public class BlockStripedAlgo implements Algorithm {

    public static final int ROWS_PER_THREAD = 50;

    @Override
    @SneakyThrows
    public List<List<Integer>> multiply(List<List<Integer>> firstMatrix, List<List<Integer>> secondMatrix) {
        List<List<Integer>> result = MatrixUtil.zeroMatrix(firstMatrix.size(), secondMatrix.get(0).size());
        ColumnSharer columnSharer = new ColumnSharer(secondMatrix);

        ExecutorService executorService = Executors.newFixedThreadPool(firstMatrix.size() / ROWS_PER_THREAD);
        performAlgorithm(firstMatrix, result, columnSharer, executorService);
        shutdownAndAwaitTermination(executorService);
        return result;
    }

    @Override
    public AlgoName getName() {
        return AlgoName.BLOCK_STRIPED;
    }

    private void performAlgorithm(List<List<Integer>> firstMatrix, List<List<Integer>> result, ColumnSharer columnSharer, ExecutorService executorService) {
        List<RowColumnMultiplier> rowColumnMultipliers = new ArrayList<>();

        for (int i = 0; i < firstMatrix.size(); i++) {
            rowColumnMultipliers.add(new RowColumnMultiplier(result, firstMatrix.get(i), columnSharer, i));
            if ((i + 1) % ROWS_PER_THREAD == 0 || i == firstMatrix.size() - 1) {
                executorService.submit(new BlockStrippedProcessor(rowColumnMultipliers));
                rowColumnMultipliers = new ArrayList<>();
            }
        }
    }

    void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}
