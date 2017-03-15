package com.nicfol;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;

public class Main {

    private final static String workingDirectory = System.getProperty("user.dir");           //Working Directory

    public static void main(String[] args) {
        //Files
        String fileAAUlinksBA = workingDirectory + "/input/aauBAlinks.txt";
        String fileAAUlinksMSc = workingDirectory + "/input/aauMSclinks.txt";

        ReadFile aauBALinks = new ReadFile(fileAAUlinksBA);

        String url = "";
        JSONObject obj = new JSONObject();

        while((url = aauBALinks.readNextLine()) != null) {
            String desc = getAAUdesc(url);
            String name = getAAUname(url);

            obj.put(name, desc);
        }

        try (FileWriter file = new FileWriter(workingDirectory + "/output/aauDescriptionsBA.json")) {

            file.write(obj.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Cleans a string
    private static String cleanString(String str2Clean) {
        //Remove various html tags
        str2Clean = str2Clean.replace("<p>", "");
        str2Clean = str2Clean.replace("</p>", " ");
        str2Clean = str2Clean.replace("<div class=\"field-content\">", "");
        str2Clean = str2Clean.replace("<div class=\"field-item even\">", "");
        str2Clean = str2Clean.replace("</div>", "");
        str2Clean = str2Clean.replace("&nbsp;", " ");
        str2Clean = str2Clean.replace("\n ", "\n");
        str2Clean = str2Clean.replace("\">", "");
        str2Clean = str2Clean.replace("<div itemprop=\"articleBody ", "");

        //Clear leading and trailing new lines
        if(str2Clean.startsWith("\n"))
            str2Clean = str2Clean.substring(1, str2Clean.length());
        if(str2Clean.endsWith("\n"))
            str2Clean = str2Clean.substring(0, str2Clean.length()-1);

        return str2Clean;
    }

    private static String[] findUGEdutype(String url) {
        //Strip protocol and domain
        url = url.replace("https://", "");
        url = url.replace("http://", "");
        url = url.replace("ug.dk/uddannelser/", "");
        url = url.replace("www.", "");
        url = url.replace("aau.dk/uddannelser/", "");

        //Split the string into an array based on the forward slashes
        String eduArrTemp[] = url.split("/");

        //Strip the array of the last position (name) cause it's useless and save to a new array

        String[] eduType = new String[eduArrTemp.length-1];
        System.arraycopy(eduArrTemp, 0, eduType, 0, eduArrTemp.length-1);

        return eduArrTemp;
    }

    private static String getAAUdesc(String link) {
        Document doc = null;

        try {
            doc = Jsoup.connect(link + "/fagligt-indhold").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //GET DESCRIPTION
        Element gridG8 = doc.getElementsByClass("spotCon grid g8").first();
        Element descriptionJSe = gridG8.select("[itemprop=articleBody]").first();
        descriptionJSe.getElementsByTag("img").remove();
        descriptionJSe.getElementsByTag("table").unwrap();
        descriptionJSe.getElementsByTag("a").unwrap();

        //Remove all tags but preserve text (parse only text)
        descriptionJSe.text();

        String description = cleanString(descriptionJSe.toString());
        description = Jsoup.parse(description).text();

        return description;
    }

    private static String getAAUname(String link) {
        Document doc = null;

        try {
            doc = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Names from aau website
        Element nameJSe = doc.getElementById("main").getElementsByClass("highlighted doubleSpaced").first();

        String name = nameJSe.unwrap().toString();
        name = name.replace(", Bachelor", "");

        return name;
    }

    private static void ugScraper() {

        Document doc = null;

        String path = "C:\\Users\\nicolai\\Documents\\Github\\Web2JSON\\input\\";
        String address = path + "ugLinks.txt";
        String errorfile = path + "errors.txt";
        String namefile = path + "names.txt";

        FileWriter errorWriter = null;
        FileWriter nameWriter = null;

        BufferedReader reader = null;
        String url = "";

        try {
            reader = new BufferedReader(new FileReader(address));

            File fileError = new File(errorfile);
            File fileName = new File(namefile);

            // creates the file
            fileError.createNewFile();
            fileName.createNewFile();

            // creates a FileWriter Object
            errorWriter = new FileWriter(fileError);
            nameWriter = new FileWriter(fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while ((url = reader.readLine()) != null) {
                System.out.println(url);

                //Get edu type based on url and strip name cause faak u dashes
                String[] eduType = findUGEdutype(url);

                String introduction = "";
                String description = "";

                try {
                    doc = Jsoup.connect(url).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Get title and strip  UG to get education name
                String title = doc.title().replace(" | UddannelsesGuiden", "");
                cleanString(title);

                //Write to file
                nameWriter.write(title + System.lineSeparator());


                //Get introduction
                try {
                    String pixi = "panel-pane pane-entity-field pane-node-field-pixi";
                    introduction = doc.getElementsByClass(pixi).first().getElementsByClass("field-item even").toString();
                } catch (NullPointerException e) {
                    System.out.println("No intro class");
                    errorWriter.write("No intro: " + url + System.lineSeparator());
                }
                introduction = cleanString(introduction);



                //Get description
                try {
                    String row1 = "views-row views-row-1";
                    description = doc.getElementsByClass(row1).first().getElementsByClass("field-content").toString();
                } catch (NullPointerException e) {
                    System.out.println("No description class");
                    errorWriter.write("No descr: " + url + System.lineSeparator());
                }
                description = cleanString(description);

                nameWriter.flush();
                errorWriter.flush();

                System.out.println(title);
                System.out.println();
                for(int i = 0; i < eduType.length; i++)
                    System.out.println(eduType[i]);
                System.out.println();
                System.out.println(introduction);
                System.out.println();
                System.out.println(description);
                System.out.println();

                Thread.sleep(10);

            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        try {
            errorWriter.flush();
            errorWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("---------------------------------------------------");
        System.out.println("--------------------  DONE  -----------------------");
        System.out.println("---------------------------------------------------");
    }
}
