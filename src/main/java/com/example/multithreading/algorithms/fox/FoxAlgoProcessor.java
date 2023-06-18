package com.example.multithreading.algorithms.fox;

import com.example.multithreading.algorithms.nativealgo.NativeAlgo;
import com.example.multithreading.utils.MatrixUtil;
import java.util.List;

public class FoxAlgoProcessor implements Runnable {
    public static final int BLOCKS_NUMBER = 5;

    private final ExchangeBlockController leftMatrixBlocksController;
    private final ExchangeBlockController rightMatrixBlocksController;
    private final MatrixContainer resultMatrixContainer;
    private final NativeAlgo nativeAlgo;
    private final Integer positionY;
    private final Integer positionX;

    public FoxAlgoProcessor(ExchangeBlockController leftMatrixBlocksController, ExchangeBlockController rightMatrixBlocksController, MatrixContainer resultMatrixContainer, NativeAlgo nativeAlgo, Integer positionY, Integer positionX) {
        this.leftMatrixBlocksController = leftMatrixBlocksController;
        this.rightMatrixBlocksController = rightMatrixBlocksController;
        this.resultMatrixContainer = resultMatrixContainer;
        this.nativeAlgo = nativeAlgo;
        this.positionY = positionY;
        this.positionX = positionX;
    }

    @Override
    public void run() {
        List<List<Integer>> result = getCalculatedBlock();
        setResultToMatrix(result);
    }

    private List<List<Integer>> getCalculatedBlock() {
        List<List<Integer>> result = MatrixUtil.generateMatrix(resultMatrixContainer.getBlockSize(), resultMatrixContainer.getBlockSize(), 0);
        for (int k = 0; k < FoxAlgoProcessor.BLOCKS_NUMBER; k++) {
            int i = (positionY + k) % FoxAlgoProcessor.BLOCKS_NUMBER;
            Position leftMatrixPosition = new Position(i, positionY);
            Position rightMatrixPosition = new Position(positionX, i);
            List<List<Integer>> leftMatrixBlock = leftMatrixBlocksController.getBlock(leftMatrixPosition);
            List<List<Integer>> rightMatrixBlock = rightMatrixBlocksController.getBlock(rightMatrixPosition);
            result = MatrixUtil.addMatrices(result, nativeAlgo.multiply(leftMatrixBlock, rightMatrixBlock));
            leftMatrixBlocksController.shareBlock(leftMatrixPosition, leftMatrixBlock);
            rightMatrixBlocksController.shareBlock(rightMatrixPosition, rightMatrixBlock);
        }
        return result;
    }

    private void setResultToMatrix(List<List<Integer>> result) {
        for (int i = 0; i < resultMatrixContainer.getBlockSize(); i++) {
            for (int j = 0; j < resultMatrixContainer.getBlockSize(); j++) {
                resultMatrixContainer.getMatrix().get(positionY * resultMatrixContainer.getBlockSize() + i).set(positionX * resultMatrixContainer.getBlockSize() + j, result.get(i).get(j));
            }
        }
    }

}
