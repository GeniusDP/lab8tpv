package com.example.multithreading.algorithms;

import java.util.List;

public interface Algorithm {

    List<List<Integer>> multiply(List<List<Integer>> firstMatrix, List<List<Integer>> secondMatrix);

    AlgoName getName();
}
