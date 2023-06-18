package com.example.multithreading.algorithms.fox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExchangeBlockController {

    private final Map<Position, List<List<Integer>>> matrixBlocksMap = new HashMap<>();

    public synchronized void shareBlock(Position position, List<List<Integer>> matrixBlock) {
        matrixBlocksMap.put(position, matrixBlock);
        notifyAll();
    }

    public synchronized List<List<Integer>> getBlock(Position position) {
        try {
            while (!matrixBlocksMap.containsKey(position)) {
                wait();
            }
            List<List<Integer>> matrixBlock = matrixBlocksMap.get(position);
            matrixBlocksMap.remove(position);
            return matrixBlock;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
