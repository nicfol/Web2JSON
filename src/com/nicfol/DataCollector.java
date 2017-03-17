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
        ReadFile ugZoomReaderMsc = new ReadFile(workingDirectory + "\\input\\ugZoom\\ugZoomCleanedMsc.csv");

        Scanner inputReader = new Scanner(System.in);

        //Debug first line
        String ugZoomLine = ugZoomReaderBa.readNextLine();
        String firstLineArr[] = ugZoomLine.split(";");
        for(int i = 0; i < firstLineArr.length-1; i++) {
            //System.out.println("   | (" + i + ") " + firstLineArr[i]);
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
                String[] line16Split = line16.split(";");
                if(line16Split[0].equals(name)) {
                    kotNo = line16Split[1];

                    applicants.put(2016, line16Split[5]);
                    intake.put(2016, line16Split[3]);
                    grades.put(2016, line16Split[7]);
                }
            }
            ReadFile k15 = new ReadFile(workingDirectory + "\\input\\kot\\hovedtal2015.csv");
            String line15;
            while((line15 = k15.readNextLine()) != null) {
                String[] lineSplit = line15.split(";");

                for(int i = 0; i < lineSplit.length; i++)
                    System.out.println(i + " " + lineSplit[i]);

                if(lineSplit[1].equals(kotNo)) {
                    System.out.println(name + "555");

                    applicants.put(2016, lineSplit[5]);
                    intake.put(2016, lineSplit[3]);
                    grades.put(2016, lineSplit[7]);
                }
            }




            /* You're fucked up if you do this 3-4-4-0
            System.out.println(name + ", " + campus);
            int year = 0;
            while(year < 2017){
                if(year == 0) {
                    System.out.println("Year");
                    year = Integer.valueOf(inputReader.next());
                }

                System.out.println("Intake");
                String noIntake = inputReader.next();
                System.out.println("Applicants");
                String noApplicants = inputReader.next();
                System.out.println("Grades");
                String noGrades = inputReader.next();

                if(noGrades.equals("0"))
                    noGrades = "Alle optaget";

                System.out.println("Intake: " + noIntake + " Appl: " + noApplicants + " Grade: " + noGrades);


                applicants.put(year, noApplicants);
                intake.put(year, noIntake);
                grades.put(year, noGrades);
                year++;

            }
            */

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
