package com.nicfol;

import org.jsoup.Jsoup;
import org.jsoup.Jsoup;
import org.json.simple.JSONObject;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        //JSONObject.obj = new JSONObject();

        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.javatpoint.com").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String title = doc.title();
        System.out.println("title is: " + title);

    }
}
