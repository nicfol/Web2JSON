package com.nicfol;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;

public class Main {

    static Document doc = null;

    public static void main(String[] args) {

        String address = "C:\\Users\\nicolai\\Documents\\Github\\Web2JSON\\input.txt";
        String errorfile = "C:\\Users\\nicolai\\Documents\\Github\\Web2JSON\\errors.txt";

        FileWriter writer = null;
        BufferedReader reader = null;
        String url = "";

        try {
            reader = new BufferedReader(new FileReader(address));

            File file = new File(errorfile);

            // creates the file
            file.createNewFile();

            // creates a FileWriter Object
            writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while ((url = reader.readLine()) != null) {
                System.out.println(url);

                //Get edu type based on url and strip name cause faak u dashes
                String[] eduType = findEdutype(url);

                String introduction = "";
                String description = "";

                try {
                    doc = Jsoup.connect(url).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Get introduction
                try {
                    String pixi = "panel-pane pane-entity-field pane-node-field-pixi";
                    introduction = doc.getElementsByClass(pixi).first().getElementsByClass("field-item even").toString();
                } catch (NullPointerException e) {
                    System.out.println("No intro class");
                    writer.write("No intro: " + url + System.lineSeparator());
                }
                introduction = cleanString(introduction);

                //Get description
                try {
                    String row1 = "views-row views-row-1";
                    description = doc.getElementsByClass(row1).first().getElementsByClass("field-content").toString();
                } catch (NullPointerException e) {
                    System.out.println("No description class");
                    writer.write("No descr: " + url + System.lineSeparator());
                }
                description = cleanString(description);

                //Get title and strip  UG to get education name
                String title = doc.title().replace(" | UddannelsesGuiden", "");

                writer.flush();

                System.out.println(title);
                System.out.println();
                for(int i = 0; i < eduType.length; i++)
                    System.out.println(eduType[i]);
                System.out.println();
                System.out.println(introduction);
                System.out.println();
                System.out.println(description);
                System.out.println();


            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    //Cleans a string
    private static String cleanString(String str2Clean) {
        //Remove various html tags
        str2Clean = str2Clean.replace("<p>", "");
        str2Clean = str2Clean.replace("</p>", "");
        str2Clean = str2Clean.replace("<div class=\"field-content\">", "");
        str2Clean = str2Clean.replace("<div class=\"field-item even\">", "");
        str2Clean = str2Clean.replace("</div>", "");
        str2Clean = str2Clean.replace("&nbsp;", " ");
        str2Clean = str2Clean.replace("<strong>", "");
        str2Clean = str2Clean.replace("</strong>", "");
        str2Clean = str2Clean.replace("\n ", "\n");

        //Clear leading and trailing new lines
        if(str2Clean.startsWith("\n"))
            str2Clean = str2Clean.substring(1, str2Clean.length());
        if(str2Clean.endsWith("\n"))
            str2Clean = str2Clean.substring(0, str2Clean.length()-1);

        return str2Clean;
    }

    private static String[] findEdutype(String url) {
        //Strip protocol and domain
        url = url.replace("https://", "");
        url = url.replace("https://", "");
        url = url.replace("ug.dk/uddannelser/", "");
        url = url.replace("www.", "");

        //Split the string into an array based on the forward slashes
        String eduArrTemp[] = url.split("/");

        //Strip the array of the last position (name) cause it's useless and save to a new array

        String[] eduType = new String[eduArrTemp.length-1];
        System.arraycopy(eduArrTemp, 0, eduType, 0, eduArrTemp.length-1);

        return eduType;
    }
}
