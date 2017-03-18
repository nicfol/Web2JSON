package com.nicfol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class DataCollector {
    private final static String workingDirectory = System.getProperty("user.dir");           //Working Directory



    public static void main(String[] args) {
        ReadFile ugZoomReaderBa = new ReadFile(workingDirectory + "\\input\\ugZoom\\ugZoomCleanedBa.csv");

        //Debug first line
        String ugZoomLineBa;
        ugZoomReaderBa.readNextLine(); //Skip first line

        Map<String,EducationObj> bachelorMap = new TreeMap<>();

        while((ugZoomLineBa = ugZoomReaderBa.readNextLine()) != null) {

            String ugSplitArr[] = ugZoomLineBa.split(";");

            String name = ugSplitArr[0];
            String ugLink = ugSplitArr[1];
            String educationLevel = ugSplitArr[3];
            String institution = "Aalborg Universitet";
            String campus = ugSplitArr[4];

            Map<Integer,String> educationForms = new TreeMap<>();

            float dropout_rate = Float.valueOf(ugSplitArr[6]);
            String completionTime = ugSplitArr[5];

            Map<Integer,String> applicants = new TreeMap<>();
            Map<Integer,String> intake = new TreeMap<>();
            Map<Integer,String> grades = new TreeMap<>();

            for(int i = 1; i <= 5; i++) {
                    educationForms.put(i, ugSplitArr[i + 7]);
            }

            ReadFile k16 = new ReadFile(workingDirectory + "\\input\\kot\\hovedtal2016.csv");
            String line16;
            String kotNo = "";
            while((line16 = k16.readNextLine()) != null) {
                String[] lineSplit = line16.split(";");

                if(lineSplit[0].equalsIgnoreCase(name)) {
                    kotNo = lineSplit[1];

                    String noAppli = lineSplit[5];
                    String noIntake = lineSplit[3];
                    String noGrades = lineSplit[7];

                    if(!Character.isDigit(noGrades.charAt(0)))
                        noGrades = "Alle optaget, ledige pladser";

                    noAppli = noAppli.replace(".", "");

                    applicants.put(2016, noAppli);
                    intake.put(2016, noIntake);
                    grades.put(2016, noGrades);
                }
            }

            ReadFile k15 = new ReadFile(workingDirectory + "\\input\\kot\\hovedtal2015.csv");
            String line15;
            while((line15 = k15.readNextLine()) != null) {
                String[] lineSplit = line15.split(";");

                if(lineSplit[1].equalsIgnoreCase(kotNo)) {
                    int year = 2015;

                    String noAppli = lineSplit[5];
                    String noIntake = lineSplit[3];
                    String noGrades = lineSplit[7];

                    if(!Character.isDigit(noGrades.charAt(0)))
                        noGrades = "Alle optaget, ledige pladser";

                    noAppli = noAppli.replace(".", "");

                    applicants.put(year, noAppli);
                    intake.put(year, noIntake);
                    grades.put(year, noGrades);
                }
            }

            ReadFile k14 = new ReadFile(workingDirectory + "\\input\\kot\\hovedtal2014.csv");
            String line14;
            while((line14 = k14.readNextLine()) != null) {
                String[] lineSplit = line14.split(";");

                    if(lineSplit[1].equalsIgnoreCase(kotNo)) {
                        int year = 2014;

                        String noAppli = lineSplit[5];
                        String noIntake = lineSplit[3];
                        String noGrades = lineSplit[7];

                        if(!Character.isDigit(noGrades.charAt(0)))
                            noGrades = "Alle optaget, ledige pladser";

                        noAppli = noAppli.replace(".", "");

                        applicants.put(year, noAppli);
                        intake.put(year, noIntake);
                        grades.put(year, noGrades);
                    }
            }

            ReadFile k13 = new ReadFile(workingDirectory + "\\input\\kot\\hovedtal2013.csv");
            String line13;
            while((line13 = k13.readNextLine()) != null) {
                String[] lineSplit = line13.split(";");

                if(lineSplit[1].equalsIgnoreCase(kotNo)) {
                    int year = 2013;

                    String noAppli = lineSplit[5];
                    String noIntake = lineSplit[3];
                    String noGrades = lineSplit[7];

                    if(!Character.isDigit(noGrades.charAt(0)))
                        noGrades = "Alle optaget, ledige pladser";

                    noAppli = noAppli.replace(".", "");

                    applicants.put(year, noAppli);
                    intake.put(year, noIntake);
                    grades.put(year, noGrades);
                }
            }

            ReadFile k12 = new ReadFile(workingDirectory + "\\input\\kot\\hovedtal2012.csv");
            String line12;
            while((line12 = k12.readNextLine()) != null) {
                String[] lineSplit = line12.split(";");

                if(lineSplit[1].equalsIgnoreCase(kotNo)) {
                    int year = 2012;

                    String noAppli = lineSplit[5];
                    String noIntake = lineSplit[3];
                    String noGrades = lineSplit[7];

                    if(!Character.isDigit(noGrades.charAt(0)))
                        noGrades = "Alle optaget, ledige pladser";
                    noAppli = noAppli.replace(".", "");

                    applicants.put(year, noAppli);
                    intake.put(year, noIntake);
                    grades.put(year, noGrades);
                }
            }

            ReadFile k11 = new ReadFile(workingDirectory + "\\input\\kot\\hovedtal2011.csv");
            String line11;
            while((line11 = k11.readNextLine()) != null) {
                String[] lineSplit = line11.split(";");

                if(lineSplit[1].equalsIgnoreCase(kotNo)) {
                    int year = 2011;

                    String noAppli = lineSplit[5];
                    String noIntake = lineSplit[3];
                    String noGrades = lineSplit[7];

                    if(!Character.isDigit(noGrades.charAt(0)))
                        noGrades = "Alle optaget, ledige pladser";
                    noAppli = noAppli.replace(".", "");

                    applicants.put(year, noAppli);
                    intake.put(year, noIntake);
                    grades.put(year, noGrades);
                }
            }

            EducationObj education = new EducationObj(name, ugLink, educationLevel, institution, campus, educationForms,
                    dropout_rate, completionTime, applicants, intake, grades);

            bachelorMap.put(name, education);
        }


        ReadFile ugZoomReaderMsc = new ReadFile(workingDirectory + "\\input\\ugZoom\\ugZoomCleanedMsc.csv");

        String ugZoomLineMsc;
        ugZoomReaderMsc.readNextLine();

        Map<String,EducationObj> masterMap = new TreeMap<>();

        while((ugZoomLineMsc = ugZoomReaderMsc.readNextLine()) != null) {

            String ugSplitArr[] = ugZoomLineMsc.split(";");

            String name = ugSplitArr[0];
            String ugLink = ugSplitArr[1];
            String educationLevel = ugSplitArr[3];
            String institution = "Aalborg Universitet";
            String campus = ugSplitArr[4];

            Map<Integer, String> educationForms = new TreeMap<>();

            for (int i = 0; i < ugSplitArr.length ; i++) {
                System.out.print(i + " = " + ugSplitArr[i] + " | ");
            }

            System.out.println();

            float dropout_rate = Float.valueOf(ugSplitArr[6]);
            String completionTime = ugSplitArr[5];

            for (int i = 1; i <= 5; i++) {
                educationForms.put(i, ugSplitArr[i + 7]);
            }

            EducationObj education = new EducationObj(name, ugLink, educationLevel, institution, campus, educationForms,
                    dropout_rate, completionTime);

            masterMap.put(name, education);
        }


        Map<String,Map> eduMap = new TreeMap<>();
        eduMap.put("Bachelors", bachelorMap);
        eduMap.put("Masters", masterMap);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String stringMap = gson.toJson(eduMap);

        try (FileWriter file = new FileWriter(workingDirectory + "/output/eduDesc.json")) {
            file.write(stringMap);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
