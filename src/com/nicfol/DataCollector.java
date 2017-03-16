package com.nicfol;

public class DataCollector {
    private final static String workingDirectory = System.getProperty("user.dir");           //Working Directory

    public static void main(String[] args) {
        ReadFile ugZoomReader = new ReadFile(workingDirectory + "\\input\\hovedtal2016.csv");
        String ugZoomLine = "";

        while((ugZoomLine = ugZoomReader.readNextLine()) != null) {
            System.out.println();
            String ugSplitArr[] = ugZoomLine.split(";");
            int cnt = 1;
            for(int i = 0; i < ugSplitArr.length; i++) {
                System.out.print(cnt + " | " + ugSplitArr[i]);
                cnt++;
            }
        }


    }
}
