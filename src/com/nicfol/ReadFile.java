package com.nicfol;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {

    private BufferedReader reader;

    public ReadFile(String file) {
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String readNextLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "NOTICE MEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!";
    }
}

