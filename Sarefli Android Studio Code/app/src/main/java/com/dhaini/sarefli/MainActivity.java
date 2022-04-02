package com.dhaini.sarefli;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public static final String buyDailyRate = "com.dhaini.sarefli.dhaini.buyDailyRate";
    public static final String sellDailyRate = "com.dhaini.sarefli.dhaini.sellDailyRate";
    private Handler handler = new Handler();
    TextView buyText;
    TextView sellText;
    apiCaller1 api1;
    TextView tv_updatedText;
    TextView tv_updatedText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // These lines remove the action and title bars
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        buyText = (TextView) findViewById(R.id.tv_buyText2);
        sellText = (TextView) findViewById(R.id.tv_sellText2);
        tv_updatedText = (TextView)findViewById(R.id.tv_updatedText);
        tv_updatedText2 = (TextView) findViewById(R.id.tv_updatedText2);

        // Timer for every minute we update the period of time we updated the rates
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public int i=1;
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        //Display last update to the UI
                        tv_updatedText.setText("Updated " + i++ + " mins ago");

                    }
                });
            }
        }, 0, 60000);

        // Get Date and time from last update
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        int minutes = rightNow.get(Calendar.MINUTE);
        int day = rightNow.get(Calendar.DAY_OF_MONTH);
        int month = rightNow.get(Calendar.MONTH)+1;
        int year = rightNow.get(Calendar.YEAR);

        // Display Date and time to the UI
        String strHour = String.valueOf(hour);;
        String strMinutes = String.valueOf(minutes);
        String strDay = String.valueOf(day);
        String strMonth = String.valueOf(month);
        if(hour<10){
            strHour = "0" + strHour;
        }
        if(minutes<10){
            strMinutes = "0" + strHour;
        }
        if(day <10){
            strDay = "0" + strDay;
        }
        if(month<10){
            strMonth = "0" + month;
        }
        tv_updatedText2.setText("at "+ strHour + ":"+ strMinutes + " " + strDay + "-" + strMonth + "-" + year);


        // Call API 1 and Execute
        String urlApi1 = "http://10.0.2.2/API's/api1.php";
        api1 = new apiCaller1();
        api1.execute(urlApi1);

    }

    public void refreshButton(View view){
        // Refresh Home Page
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public class apiCaller1 extends AsyncTask<String,Void,String> {
        protected String doInBackground(String... urls){
            // URL and HTTP initialization to connect to API 1
            URL url;
            HttpURLConnection http;

            try{
                url = new URL(urls[0]);
                http =(HttpURLConnection) url.openConnection();

                // Retrieve API 1 content
                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                // Read API 1 content line by line
                BufferedReader br = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();

                // Append API 1 content to empty string
                String line;
                while((line = br.readLine())!= null){
                    sb.append(line + "\n");
                }

                br.close();
                return sb.toString();
            }
            catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }
        protected void onPostExecute(String values){
            super.onPostExecute(values);
            try{

                DecimalFormat formatter = new DecimalFormat("#,###");

                // Splitting the returned values from the api
                String[] splitValues = values.split("]");

                // Getting the buy rate and its date
                String[] splitBuy = splitValues[0].split(",");

                // Using formatter function to put a comma after every 3 numbers
                String buyRate = String.valueOf(formatter.format(Integer.parseInt(splitBuy[1]))) ;

                // Getting the sell rate and its date
                String[] splitSell = splitValues[1].split(",");
                String sellRate = String.valueOf(formatter.format(Integer.parseInt(splitSell[1]))) ;

                // Setting the updated buy and sell rates to the interface
                buyText.setText("1 USD at " + buyRate + " LBP");
                sellText.setText("1 USD at " + sellRate + " LBP");

            }
            catch(Exception e){
                e.printStackTrace();


            }
        }
    }

    public void goToCalculator(View view){
        // Create an intent and start the the calculator activity
        Intent goToCalculator = new Intent(getApplicationContext(), Calculator.class);

        // Sending buy and sell rates to the calculator activity
        goToCalculator.putExtra(buyDailyRate,buyText.getText().toString());
        goToCalculator.putExtra(sellDailyRate,sellText.getText().toString());

        startActivity(goToCalculator);
    }
}