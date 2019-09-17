package com.example.tempconverter;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.ArrayList;

public class AsyncTaskFaren extends  android.os.AsyncTask<ArrayList<Temperature>,Void,String> {

private  ArrayList<Temperature> temps;


    AsyncTaskFaren(ArrayList<Temperature> temps) {
        this.temps = temps;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }

    @SafeVarargs
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected final String doInBackground(ArrayList<Temperature>... arrayLists) {

        Converter convert = new Converter(temps);

        AverageTemp test = new AverageTemp(convert.convertCelsiusToFahrenheit(temps));
        String faren = "";
        MedianTemp median = new MedianTemp(convert.convertFahrenheitToCelsius(temps));

        try {
            for(Temperature temp : temps) {

                if(temp.getTempUnit().trim().equals("F")){
                    faren += temp.getTemp() + temp.getTempUnit();
                }
            }

            return "Värden" + faren+ "\nMedelVärden:" +  test.Calculate().trim()  + "F"
                      + "\nMedianVärden:" + median.getMedian().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
