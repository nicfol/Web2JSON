package com.nicfol;

import sun.applet.resources.MsgAppletViewer;

import java.util.ArrayList;
import java.util.Map;

public class EducationObj {

    String name;
    String ugLink;
    String educationLevel;
    String institution;
    String campus;
    String[] educationForms;
    float dropout_rate;
    String completionTime;
    String[][] similarEducations;
    String[][] possibleJobs;

    Map<Integer,Integer> applicants;
    Map<Integer,Integer> intake;
    Map<Integer,Integer> grades;


    //int[][] applicants;
    //int[] intake;
    //float[] grades;

    public EducationObj(
                        String name,
                        String ugLink,
                        String educationLevel,
                        String institution,
                        String campus,
                        String[] educationForms,
                        float dropout_rate,
                        String completionTime,
                        //String[][] similarEducations,
                        //String[][] possibleJobs,
                        Map applicants,
                        Map intake,
                        Map grades) {

        this.name = name;
        this.ugLink = ugLink;
        this.educationLevel = educationLevel;
        this.institution = institution;
        this.campus = campus;
        this.educationForms = educationForms;
        this.dropout_rate = dropout_rate;
        this.completionTime = completionTime;
        this.similarEducations = similarEducations;
        this.possibleJobs = possibleJobs;

        this.applicants = applicants;
        this.intake = intake;
        this.grades = grades;
    }
}
