package com.nicfol;

public class DataCollector {
    private final static String workingDirectory = System.getProperty("user.dir");           //Working Directory

    public static void main(String[] args) {
        ReadFile ugZoomReader = new ReadFile(workingDirectory + "/input/ugZoom.csv");



    }
}
