package com.nicfol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class DataCollector {
    private final static String workingDirectory = System.getProperty("user.dir");           //Working Directory



    public static void main(String[] args) {
        ReadFile ugZoomReaderBa = new ReadFile(workingDirectory + "\\input\\ugZoom\\ugZoomCleanedBa.csv");
        ReadFile ugZoomReaderMsc = new ReadFile(workingDirectory + "\\input\\ugZoom\\ugZoomCleanedMsc.csv");

        //Debug first line
        String ugZoomLine = ugZoomReaderBa.readNextLine();
        String firstLineArr[] = ugZoomLine.split(";");
        for(int i = 0; i < firstLineArr.length-1; i++) {
            System.out.println("   | (" + i + ") " + firstLineArr[i]);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        EducationObj education;

        Map<String,EducationObj> bachelorMap = new TreeMap<>();

        while((ugZoomLine = ugZoomReaderBa.readNextLine()) != null) {
            String ugSplitArr[] = ugZoomLine.split(";");

            String name = ugSplitArr[0];
            String ugLink = ugSplitArr[1];
            String educationLevel = ugSplitArr[3];
            String institution = "Aalborg University";
            String campus = ugSplitArr[4];

            Map<Integer,String> educationForms = new TreeMap<>();

            float dropout_rate = Float.valueOf(ugSplitArr[6]);
            String completionTime = ugSplitArr[5];

            Map<Integer,Integer> applicants = new TreeMap<>();
            Map<Integer,Integer> intake = new TreeMap<>();
            Map<Integer,Integer> grades = new TreeMap<>();


            educationForms.put(5, ugSplitArr[12]);
            educationForms.put(4, ugSplitArr[11]);
            educationForms.put(3, ugSplitArr[10]);
            educationForms.put(2, ugSplitArr[9]);
            educationForms.put(1, ugSplitArr[8]);


            applicants.put(2017, 1);
            applicants.put(2016, 2);
            applicants.put(2015, 3);


            education = new EducationObj(name, ugLink, educationLevel, institution, campus, educationForms,
                    dropout_rate, completionTime, applicants, intake, grades);

            bachelorMap.put(name, education);
        }

        Map<String,EducationObj> masterMap = new TreeMap<>();




        Map<String,Map> eduMap = new TreeMap<>();
        eduMap.put("Bacheloruddannelse", bachelorMap);
        eduMap.put("Kandidatuddannelse", masterMap);

        String stringMap = gson.toJson(eduMap);

        try (FileWriter file = new FileWriter(workingDirectory + "/output/eduDescTest.json")) {
            file.write(stringMap);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
