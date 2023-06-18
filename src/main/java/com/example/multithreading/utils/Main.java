package com.example.multithreading.utils;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<List<Integer>> lists = MatrixUtil.generateMatrix(1000, 1000, 3);
        try (FileWriter fileWriter = new FileWriter("src/main/resources/matrix.txt");){
            for (List<Integer> list : lists) {
                String result = "";
                for (Integer el : list) {
                    result += el + " ";
                }
                fileWriter.append(result);
            }

        } catch (Exception e) {

        }

    }

}
