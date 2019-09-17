package com.example.tempconverter;


import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class FileReader {
    /**
     * Get the temperature from file and converts it into a ArrayList with
     * temperature objects.
     *
     * @return ArrayList<Temperature>
     */

    private static Context context;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Temperature> temperatureList(Context context) throws JSONException {

        JSONObject object1 = readFrom(context);

        ArrayList<Temperature> temperatureList = new ArrayList<Temperature>();

        for (int i = 0; i < object1.getJSONArray("temperature").length(); i++) {

            JSONObject object2 = object1.getJSONArray("temperature").getJSONObject(i);

            Temperature myObj = new Temperature((String) object2.get("unit"),
                    Double.valueOf(object2.get("temperaturevalue").toString()));
            temperatureList.add(myObj);
        }

        return temperatureList;
    }

    /**
     * Reads data from file.
     *
     * @return JsonObject
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    static JSONObject readFrom(Context context) throws JSONException {




        System.out.println();

        String tmpStr;
        String svar = "";
        BufferedReader br = null;




        try {


            br =  new BufferedReader(
                    new InputStreamReader( context.getAssets().open("TemperatureData.json") ,  "UTF-8"));




            while ((tmpStr = br.readLine()) != null) {
                svar += tmpStr;
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsobj = new JSONObject(svar);
        return jsobj;
    }

}
