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
        introduction = cleanString(introduction);

        //Get description

        String row1 = "views-row views-row-1";
        try {
            description = doc.getElementsByClass(row1).first().getElementsByClass("field-content").toString();
        } catch (NullPointerException e) {
            System.out.println("No description class");
        }

        description = cleanString(description);

        //Get title
        String title = doc.title();
        String titleNewString = title.replace(" | UddannelsesGuiden", "");

        System.out.println(titleNewString);
        System.out.println();
        System.out.println(introduction);
        System.out.println();
        System.out.println(description);
    }

    public static String cleanString(String str2Clean) {
        str2Clean = str2Clean.replace("<p>", "");
        str2Clean = str2Clean.replace("</p>", "");
        str2Clean = str2Clean.replace("<div class=\"field-content\">", "");
        str2Clean = str2Clean.replace("<div class=\"field-item even\">", "");
        str2Clean = str2Clean.replace("</div>", "");
        str2Clean = str2Clean.replace("&nbsp;", " ");
        str2Clean = str2Clean.replace("<strong>", "");
        str2Clean = str2Clean.replace("</strong>", "");
        str2Clean = str2Clean.replace("\n ", "\n");

        if(str2Clean.startsWith("\n"))
            str2Clean = str2Clean.substring(1, str2Clean.length());
        if(str2Clean.endsWith("\n"))
            str2Clean = str2Clean.substring(0, str2Clean.length()-1);

        return str2Clean;
    }
}
