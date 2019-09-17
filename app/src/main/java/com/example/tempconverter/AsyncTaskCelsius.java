package com.example.tempconverter;


import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.tempconverter.Converter.*;

public class AsyncTaskCelsius extends  android.os.AsyncTask<ArrayList<Temperature>,Void,String> {

private  ArrayList<Temperature> temps;


    AsyncTaskCelsius(ArrayList<Temperature> temps) {
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
        String faren = "";
        for(Temperature temp : temps) {

            if(temp.getTempUnit().trim().equals("C")){
                faren += temp.getTemp() + temp.getTempUnit() + " ";
            }
        }


        Converter convert = new Converter(temps);
        AverageTemp test = new AverageTemp(convert.convertFahrenheitToCelsius(temps));

            MedianTemp median = new MedianTemp(convert.convertFahrenheitToCelsius(temps));



        try {


            return "Värden: " + faren+ "\nMedelVärden:" +  test.Calculate()
                    + "C" + "\nMedianVärden:" + median.getMedian().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
