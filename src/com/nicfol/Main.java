package com.nicfol;

import com.sun.xml.internal.fastinfoset.util.CharArray;
import org.jsoup.Jsoup;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.json.simple.JSONObject;

import java.io.IOException;

public class Main {

    static Document doc = null;

    public static void main(String[] args) {

        String url = "https://www.ug.dk/uddannelser/universitetsuddannelser/bacheloruddannelser/humanistiskebacheloruddannelser/erhvervssprog/interkulturel-markedskommunikation";

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

        //Get description
        String row1 = "views-row views-row-1";
        try {
            description = doc.getElementsByClass(row1).first().getElementsByClass("field-content").toString();
        } catch (NullPointerException e) {
            System.out.println("No description class");
        }

        //Get title
        String title = doc.title();
        title.split("(?!^)");

        char[] titleChar = title.toCharArray();
        char[] titleNew = new char[title.length()];

        for(int i = 0; i < title.length(); i++) {
            if(titleChar[i] == '|' && titleChar[i+2] == 'U'){
                break;
            } else {
                titleNew[i] = titleChar[i];
            }
        }
        String titleNewString = new String(titleNew);


        System.out.println(titleNewString);
        System.out.println();
        System.out.println(introduction);
        System.out.println();
        System.out.println(description);
    }
}
