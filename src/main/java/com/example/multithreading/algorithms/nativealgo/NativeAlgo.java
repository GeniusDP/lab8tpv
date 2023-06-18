package com.example.multithreading.algorithms.nativealgo;

import com.example.multithreading.algorithms.AlgoName;
import com.example.multithreading.algorithms.Algorithm;
import com.example.multithreading.utils.MatrixUtil;
import java.util.List;

public class NativeAlgo implements Algorithm {

    @Override
    public List<List<Integer>> multiply(List<List<Integer>> firstMatrix, List<List<Integer>> secondMatrix) {
        List<List<Integer>> result = MatrixUtil.generateMatrix(firstMatrix.size(), secondMatrix.get(0).size(), 0);
        for (int i = 0; i < firstMatrix.size(); i++) {
            for (int j = 0; j < secondMatrix.get(0).size(); j++) {
                int sum = 0;
                for (int k = 0; k < firstMatrix.get(0).size(); k++) {
                    sum += firstMatrix.get(i).get(k) * secondMatrix.get(k).get(j);
                }
                result.get(i).set(j, sum);
            }
        }
        return result;
    }

    @Override
    public AlgoName getName() {
        return AlgoName.NATIVE;
    }
}
