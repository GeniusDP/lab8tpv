package com.example.multithreading.algorithms.fox;

import com.example.multithreading.algorithms.AlgoName;
import com.example.multithreading.algorithms.Algorithm;
import com.example.multithreading.algorithms.nativealgo.NativeAlgo;
import com.example.multithreading.utils.MatrixUtil;
import java.util.ArrayList;
import java.util.List;

public class FoxAlgo implements Algorithm {

    @Override
    public List<List<Integer>> multiply(List<List<Integer>> firstMatrix, List<List<Integer>> secondMatrix) {
        MatrixContainer leftMatrixContainer = new MatrixContainer(firstMatrix);
        ExchangeBlockController leftMatrixBlocksController = getExchangeBlockController(leftMatrixContainer);
        MatrixContainer rightMatrixContainer = new MatrixContainer(secondMatrix);
        ExchangeBlockController rightMatrixBlocksController = getExchangeBlockController(rightMatrixContainer);
        MatrixContainer resultMatrixContainer = new MatrixContainer(MatrixUtil.generateMatrix(firstMatrix.size(), firstMatrix.size(), 0));
        List<Thread> threads = getThreads(leftMatrixBlocksController, rightMatrixBlocksController, resultMatrixContainer);
        joinThreads(threads);
        return resultMatrixContainer.getMatrix();
    }

    private ExchangeBlockController getExchangeBlockController(MatrixContainer leftMatrixContainer) {
        ExchangeBlockController leftMatrixBlocksController = new ExchangeBlockController();
        for (int i = 0; i < FoxAlgoProcessor.BLOCKS_NUMBER; i++) {
            for (int j = 0; j < FoxAlgoProcessor.BLOCKS_NUMBER; j++) {
                Position position = new Position(j, i);
                leftMatrixBlocksController.shareBlock(position, leftMatrixContainer.getSubMatrix(position));
            }
        }
        return leftMatrixBlocksController;
    }

    @Override
    public AlgoName getName() {
        return AlgoName.FOX;
    }

    private void joinThreads(List<Thread> threads) {
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Thread> getThreads(ExchangeBlockController leftMatrixBlocksController, ExchangeBlockController rightMatrixBlocksController, MatrixContainer resultMatrixContainer) {
        NativeAlgo nativeAlgo = new NativeAlgo();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < FoxAlgoProcessor.BLOCKS_NUMBER; i++) {
            for (int j = 0; j < FoxAlgoProcessor.BLOCKS_NUMBER; j++) {
                FoxAlgoProcessor foxAlgoProcessor = new FoxAlgoProcessor(leftMatrixBlocksController, rightMatrixBlocksController, resultMatrixContainer, nativeAlgo, i, j);
                Thread thread = new Thread(foxAlgoProcessor);
                threads.add(thread);
                thread.start();
            }
        }
        return threads;
    }
}
