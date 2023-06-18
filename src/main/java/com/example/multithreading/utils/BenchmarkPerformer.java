package com.example.multithreading.utils;

import com.example.multithreading.algorithms.Algorithm;
import com.example.multithreading.algorithms.Result;
import java.util.List;

public class BenchmarkPerformer {

    public static Result performBenchmark(Algorithm multiplicationAlgo, List<List<Integer>> firstMatrix, List<List<Integer>> secondMatrix) {
        long startTime = System.nanoTime();

        List<List<Integer>> resultMatrix = multiplicationAlgo.multiply(firstMatrix, secondMatrix);

        long duration = System.nanoTime() - startTime;
        return new Result(multiplicationAlgo.getName(), (long) (duration / 1e6));
    }
}
