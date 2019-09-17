package com.example.tempconverter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity    {

    private  final String ERROR_NO_DATA = "INGA MÄTVÄRDEN HITTADES!";
    private static ArrayList<Temperature> tempSeries;
    private  static ArrayList<Temperature> tempSeriesCelsius;
    private   static ArrayList<Temperature> tempSeriesFahrenheit;
    private  DecimalFormat numberFormat = new DecimalFormat("#.00"); // Antal decimaler
    private Context context;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private   String  remote = "";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        context = this;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);



        button1.setOnClickListener(view ->{

                                        {
                                           try {
                                               tempSeries = FileReader.temperatureList(context);
                                               Context context = getApplicationContext();
                                               String something = Integer.toString(tempSeries.size());


                                               CharSequence text = "MÄTVÄRDEN HAR LÄSTS IN. ";
                                               int duration = Toast.LENGTH_SHORT;
                                               if (tempSeries.size() == 0) {
                                                   System.err.println(ERROR_NO_DATA);
                                               } else {
                                                   Toast toast = Toast.makeText(context, text, duration);
                                                   toast.show();
                                               }

                                           } catch (JSONException e) {
                                               e.printStackTrace();
                                           }

                                       }
                                   });


             button2.setOnClickListener(view -> {



                 AsyncTask test = new AsyncTask(tempSeries);


                 test.execute();

               try {



                       tempSeries = test.get();


                   } catch (ExecutionException e) {
                       e.printStackTrace();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }



                 for(int i = 0; i < tempSeries.size(); i++) {

                     remote +=  tempSeries.get(i).toString()+ "\n";
                 }

                 Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
                 String message = remote;
                 intent.putExtra(EXTRA_MESSAGE, message);
                 startActivity(intent);





         });

        button3.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);


            String message = showSeries(tempSeries);
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        });




        button4.setOnClickListener(view -> {




            AsyncTaskCelsius test = new  AsyncTaskCelsius(tempSeries);

            test.execute();


            String text = "" ;

            try {



                text = test.get();


            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }





            Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
            String message = text;
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);


        });


        button5.setOnClickListener(view -> {

            AsyncTaskFaren test = new AsyncTaskFaren(tempSeries);

            test.execute();


            String text = "" ;

            try {



                text = test.get();


            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }





            Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
            String message = text;
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);


        });



    }



    private class AsyncTask extends android.os.AsyncTask<Void , Void, ArrayList<Temperature>> {

        private ArrayList<Temperature> list;


        public AsyncTask(ArrayList<Temperature> list ) {
            this.list = list;

        }

        @Override
        protected void onPostExecute(ArrayList<Temperature> temperatures) {
            super.onPostExecute(temperatures);
            tempSeries = temperatures;
            Log.d("******HEJ", String.valueOf(tempSeries));

        }

        @Override
        protected ArrayList<Temperature> doInBackground(Void... voids) {

            RemoteSource test = new RemoteSource();


            list = test.getRemoteTemperatures();



            return list;
        }




    }


    private static String showSeries(ArrayList<Temperature> series) {
       String content = "";
        for (Temperature obj : series) {
            String temperature = obj.getTemp() + "";
          content +=  temperature + " °" + obj.getTempUnit().toUpperCase() + "\n";
        }
        return "Mätvärden är :" + content;
    }




}
