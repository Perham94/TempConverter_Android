package com.example.tempconverter;


import java.io.IOException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.*;

/**
 * @author Perham
 *
 */
public class AverageTemp {

    private ArrayList<Temperature> TempList;
    private String path = "";
    private URL link;
    private String result = "";
    private String loop = "";


    public AverageTemp(ArrayList<Temperature> tempList) {
        TempList = new ArrayList<>(tempList);
    }

    /**
     * Creates the String that is going to be sent to the Math API to be calculated.
     */
    private void getValues() {
        String expr = "";
        for (Temperature temperature : TempList) {
            expr += temperature.getTemp() + "%2B";
        }
        path = "(" + expr + "0)" + "%2F" + "(" + TempList.size() + ")";
    }

    /**
     * Calculates the average temperature of the arraylist by sending it to the math
     * rest api.
     *
     * @throws IOException
     */
    public String Calculate() throws IOException {

        getValues();
        link = new URL("https://api.mathjs.org/v4/?expr=" + path);

        try {
            Scanner scanner = new Scanner(link.openStream());

            scanner.useDelimiter("\\A");
            loop = scanner.hasNext() ? scanner.next() : "";
            scanner.close();

            result = round(Float.parseFloat(loop)) + "" ;
            return result;

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            System.out.println(e.toString());
        }

        return result;

    }

}


