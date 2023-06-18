package com.example.multithreading.algorithms.blockstriped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.SneakyThrows;

public class ColumnSharer {

    private final Map<Integer, List<Integer>> sharedColumns = new HashMap<>();
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public ColumnSharer(List<List<Integer>> matrix) {
        for (int i = 0; i < matrix.get(0).size(); i++) {
            List<Integer> column = getColumnOfMatrix(i, matrix);
            sharedColumns.put(i, column);
        }
    }

    private List<Integer> getColumnOfMatrix(int i, List<List<Integer>> matrix) {
        return matrix.stream().map(row -> row.get(i)).toList();
    }

    @SneakyThrows
    public List<Integer> getColumn(int columnNumber) {
        try {
            lock.lock();
            while (!sharedColumns.containsKey(columnNumber)) {
                condition.await();
            }
            return sharedColumns.get(columnNumber);
        } finally {
            lock.unlock();
        }
    }

    public void returnColumn(int columnNumber, List<Integer> column) {
        try {
            lock.lock();
            sharedColumns.put(columnNumber, column);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
