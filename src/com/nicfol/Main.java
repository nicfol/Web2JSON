package com.nicfol;

import org.jsoup.Jsoup;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.json.simple.JSONObject;

import java.io.IOException;

public class Main {

    static Document doc = null;

    public static void main(String[] args) {

        String url = "https://www.ug.dk/uddannelser/universitetsuddannelser/bacheloruddannelser/naturvidenskabeligebacheloruddannelser/matematikfysikkemiogdatalogi/medialogy";

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
        String pixi = "panel-pane pane-entity-field pane-node-field-pixi";
        try {
            introduction = doc.getElementsByClass(pixi).first().getElementsByClass("field-item even").toString();
        } catch (NullPointerException e) {
            System.out.println("No intro class");
        }
        introduction = cleanString(introduction);

        //Get description
        String row1 = "views-row views-row-1";
        try {
            description = doc.getElementsByClass(row1).first().getElementsByClass("field-content").toString();
        } catch (NullPointerException e) {
            System.out.println("No description class");
        }
        description = cleanString(description);

        //Get title and strip  UG to get education name
        String title = doc.title().replace(" | UddannelsesGuiden", "");

        System.out.println(title);
        System.out.println();
        for(int i = 0; i < eduType.length; i++)
            System.out.println(eduType[i]);
        System.out.println();
        System.out.println(introduction);
        System.out.println();
        System.out.println(description);
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
        url = url.replace("www.ug.dk/uddannelser/", "");

        //Split the string into an array based on the forward slashes
        String eduArrTemp[] = url.split("/");

        //Strip the array of the last position (name) cause it's useless and save to a new array

        String[] eduType = new String[eduArrTemp.length-1];
        System.arraycopy(eduArrTemp, 0, eduType, 0, eduArrTemp.length-1);

        return eduType;
    }
}
