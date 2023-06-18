package com.example.multithreading.algorithms.blockstriped;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BlockStrippedProcessor implements Runnable {

    private final List<RowColumnMultiplier> rowColumnMultipliers;

    @Override
    public void run() {
        rowColumnMultipliers.forEach(RowColumnMultiplier::multiply);
    }

}
