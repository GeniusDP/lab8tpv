package com.example.multithreading.dto;

import java.util.List;

public record AnalyzerResponse(List<String> filePaths, Long time) {

}
