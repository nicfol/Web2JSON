package com.nicfol;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.lang.model.element.Name;
import java.io.*;

public class Main {

    public static void main(String[] args) {

        String path = "C:\\Users\\nicolai\\Documents\\Github\\Web2JSON\\";
        String inputBA = path + "aauBAlinks.txt";
        String inputMSC = path + "aauMSClinks.txt";

        String faglightIndhold = "/fagligt-indhold";

        BufferedReader readerLinks = null;
        BufferedReader hovedtal = null;
        String url = "";

        try {
            readerLinks = new BufferedReader(new FileReader(inputBA));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Document doc = null;
            while ((url = readerLinks.readLine()) != null) {

                try {
                    doc = Jsoup.connect(url).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String eduLevel = findEdutype(url)[0]; //Ba or MSc
                String eduName = findEdutype(url)[1]; //Name

                //Name from website
                Element nameJSe = doc.getElementById("main").getElementsByClass("highlighted doubleSpaced").first();

                String name = nameJSe.unwrap().toString();

                name = name.replace(", Bachelor", "");
                //System.out.println(name);

                hovedtal = new BufferedReader(new FileReader(path + "hovedtal2016.csv"));
                String temp = "";
                int cnt = 1;
                while((temp = hovedtal.readLine()) != null) {
                    String arr[] = temp.split(",");
                    if(arr[0].matches(".*\\b" + name + "\\b.*")){
                        System.out.println(cnt + " == " +name + " =========== " + arr[0]);
                    }
                    cnt++;
                }


                Element gridG8 = doc.getElementsByClass("spotCon grid g8").first();
                Element descriptionJSe = gridG8.select("[itemprop=articleBody]").first();
                descriptionJSe.getElementsByTag("img").remove();
                descriptionJSe.getElementsByTag("a").unwrap();
                descriptionJSe.getElementsByTag("p").unwrap();

                //description.text(); //Remove all tags but preserve text (parse only text)

                String description = cleanString(descriptionJSe.toString());
                //System.out.print(description);


                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Cleans a string
    private static String cleanString(String str2Clean) {
        //Remove various html tags
        //str2Clean = str2Clean.replace("<p>", "");
        //str2Clean = str2Clean.replace("</p>", "");
        str2Clean = str2Clean.replace("<div class=\"field-content\">", "");
        str2Clean = str2Clean.replace("<div class=\"field-item even\">", "");
        str2Clean = str2Clean.replace("</div>", "");
        str2Clean = str2Clean.replace("&nbsp;", " ");
        str2Clean = str2Clean.replace("<strong>", "");
        str2Clean = str2Clean.replace("</strong>", "");
        str2Clean = str2Clean.replace("\n ", "\n");
        str2Clean = str2Clean.replace("\">", "");
        //str2Clean = str2Clean.replace("<a href=\"", "");
        //str2Clean = str2Clean.replace("</a>", "");
        str2Clean = str2Clean.replace("<div itemprop=\"articleBody ", "");


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

    private static void ugScraper() {

        Document doc = null;

        String path = "C:\\Users\\nicolai\\Documents\\Github\\Web2JSON\\";
        String address = path + "input.txt";
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
                String[] eduType = findEdutype(url);

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
