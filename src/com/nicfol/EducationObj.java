package com.nicfol;

import java.util.Map;

public class EducationObj {

    String name;
    String ugLink;
    String educationLevel;
    String institution;
    String campus;
    Map educationForms;
    float dropout_rate;
    String completionTime;

    Map<Integer,String> applicants;
    Map<Integer,String> intake;
    Map<Integer,String> grades;

    public EducationObj(
            String name,
            String ugLink,
            String educationLevel,
            String institution,
            String campus,
            Map educationForms,
            float dropout_rate,
            String completionTime,
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

        this.applicants = applicants;
        this.intake = intake;
        this.grades = grades;
    }

    public EducationObj(
            String name,
            String ugLink,
            String educationLevel,
            String institution,
            String campus,
            Map educationForms,
            float dropout_rate,
            String completionTime) {

        this.name = name;
        this.ugLink = ugLink;
        this.educationLevel = educationLevel;
        this.institution = institution;
        this.campus = campus;
        this.educationForms = educationForms;
        this.dropout_rate = dropout_rate;
        this.completionTime = completionTime;
    }
}
