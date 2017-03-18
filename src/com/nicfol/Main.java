package com.nicfol;


import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import sun.nio.cs.ext.MS874;

import java.io.*;

public class Main {

    private final static String workingDirectory = System.getProperty("user.dir");           //Working Directory

    public static void main(String[] args) {


        //Files
        String fileAAUlinksBA = workingDirectory + "/input/aau/aauBAlinks.txt";
        String fileAAUlinksMSc = workingDirectory + "/input/aau/aauMSclinks.txt";

        ReadFile aauBALinks = new ReadFile(fileAAUlinksBA);
        ReadFile aauMSCLinks = new ReadFile(fileAAUlinksMSc);

        String Baurl = "";
        String MScurl = "";

        JSONObject baOBJ = new JSONObject();
        JSONObject mscOBJ = new JSONObject();


        while((Baurl = aauBALinks.readNextLine()) != null) {
            //System.out.println(Baurl);

            String desc = getAAUdesc(Baurl);
            String name = getAAUname(Baurl);

            baOBJ.put(name, desc);
        }

        try (FileWriter file = new FileWriter(workingDirectory + "/output/aauDescriptionsBA.json")) {
            file.write(baOBJ.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


        while((MScurl = aauMSCLinks.readNextLine()) != null) {
            //System.out.println(MScurl);

            String desc = getAAUdesc(MScurl);
            String name = getAAUname(MScurl);

            cleanString(name);

            mscOBJ.put(name, desc);
        }

        try (FileWriter file = new FileWriter(workingDirectory + "/output/aauDescriptionsMSc.json")) {
            file.write(mscOBJ.toJSONString());
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

    private static String getAAUdesc(String link) {
        Document doc = null;

        String splitLink[] = link.split("/");

        try {
            //if(splitLink[6].equals("specialiseringer")) {
            if(splitLink.length == 8) {
                doc = Jsoup.connect(link).get();
            } else {
                doc = Jsoup.connect(link + "/fagligt-indhold").get();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //GET DESCRIPTION
        Element gridG8 = doc.getElementsByClass("spotCon grid g8").first();
        Element descriptionJSe = gridG8.select("[itemprop=articleBody]").first();
        if(descriptionJSe != null) {
            descriptionJSe.getElementsByTag("img").remove();
            descriptionJSe.getElementsByTag("table").unwrap();
            descriptionJSe.getElementsByTag("a").unwrap();

            //Remove all tags but preserve text (parse only text)
            descriptionJSe.text();

            String description = cleanString(descriptionJSe.toString());
            description = Jsoup.parse(description).text();

            return description;
        } else {
            gridG8.getElementsByTag("img").remove();
            gridG8.getElementsByTag("table").unwrap();
            gridG8.getElementsByTag("a").unwrap();

            //Remove all tags but preserve text (parse only text)
            gridG8.text();

            String description = cleanString(gridG8.toString());
            description = Jsoup.parse(description).text();

            return description;
        }
    }

    private static String getAAUname(String link) {
        Document doc = null;

        try {
            doc = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Names from aau website
        String splitLink[] = link.split("/");

        Element nameJSe;
        if(splitLink.length == 8) {
            nameJSe = doc.getElementById("main").getElementsByClass("highlighted ").first();
        } else {
            nameJSe = doc.getElementById("main").getElementsByClass("highlighted doubleSpaced").first();
        }

        //If it doesn't fit... Make it fit..
        if(nameJSe == null) {
            nameJSe = doc.getElementById("main").getElementsByClass("highlighted ").first();
        }

        String name = nameJSe.unwrap().toString();
        name = name.replace(", Bachelor", "");
        name = name.replace(", Kandidat", "");
        name = name.replace(", kandidat", "");

        return name;
    }
}
