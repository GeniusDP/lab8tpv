package com.example.multithreading.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

public class MatrixUtil {

    public static List<List<Integer>> zeroMatrix(int rows, int columns) {
        return generateMatrix(rows, columns, () -> 0);
    }

    public static List<List<Integer>> generateMatrix(int rows, int columns, int elem) {
        return generateMatrix(rows, columns, () -> elem);
    }

    public static List<List<Integer>> generateRandomMatrix(int rows, int columns) {
        return generateMatrix(rows, columns, () -> (int) (Math.random() * 200));
    }

    private static List<List<Integer>> generateMatrix(int rows, int columns, Supplier<Integer> matrixElementGetter) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            List<Integer> column = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                column.add(matrixElementGetter.get());
            }
            result.add(column);
        }
        return result;
    }

    public static List<List<Integer>> addMatrices(List<List<Integer>> matrix1, List<List<Integer>> matrix2) {
        for (int i = 0; i < matrix1.size(); i++) {
            for (int j = 0; j < matrix1.get(i).size(); j++) {
                matrix1.get(i).set(j, matrix1.get(i).get(j) + matrix2.get(i).get(j));
            }
        }
        return matrix1;
    }

    public static void printMatrix(List<List<Integer>> result) {
        for (List<Integer> integers : result) {
            System.out.println(integers);
        }
    }

    @SneakyThrows
    public static List<List<Integer>> getFromFile(MultipartFile file, int rows, int cols) {
        List<List<Integer>> matrix = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            matrix.add(new ArrayList<>());
        }
        Scanner scanner = new Scanner(file.getInputStream());
        int el = 0;
        while (scanner.hasNextInt()) {
            int nextInt = scanner.nextInt();
            matrix.get(el / cols).add(nextInt);
            el ++;
        }
        return matrix;
    }

}
