package com.nicfol;

import java.util.ArrayList;
import java.util.List;

public class EducationObj {

    String name;
    String educationLevel;
    String institution;
    String campus;
    int[][] applicants;
    int[] intake;
    float[] grades;
    String[] educationForm;
    float dropout_rate;
    String completionTime;
    String ugLink;
    String[][] similarEducations;
    String[][] possibleJobs;

    public EducationObj(String name, String educationLevel, int[][] applicants){
        this.name = name;
        this.educationLevel = educationLevel;
        this.applicants = applicants;
    }

    public String getName() {
        return this.name;
    }
}
