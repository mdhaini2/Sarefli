package com.dhaini.sarefli;

import androidx.appcompat.app.AppCompatActivity;


import android.os.AsyncTask;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    // API calls as strings
    public static final String buyDailyRate = "com.dhaini.sarefli.dhaini.buyDailyRate";
    public static final String sellDailyRate = "com.dhaini.sarefli.dhaini.sellDailyRate";

    // Declaration of UI components and internal timer variables
    private Handler handler = new Handler();
    TextView buyText;
    TextView sellText;
    apiCaller1 api1;
    TextView updatedText;
    TextView updatedText2;
    ImageView refreshButton;
    Animation rotateAnimation;
    public int updateTimer = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // These lines remove the action and title bars
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Initialization of above-declared variables
        buyText = (TextView) findViewById(R.id.tv_buyText2);
        sellText = (TextView) findViewById(R.id.tv_sellText2);
        updatedText = (TextView) findViewById(R.id.tv_updatedText);
        updatedText2 = (TextView) findViewById(R.id.tv_updatedText2);
        refreshButton = (ImageView) findViewById(R.id.image_refresh);
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        refreshButton.setAnimation(rotateAnimation);

        // Timer for every minute that passes since last update
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        //Display last update to the UI
                        updatedText.setText("Updated " + updateTimer++ + " mins ago");
                    }
                });
            }
        }, 0, 60000);


        // Display Date and time to the UI
        displayTime();

        // Call API 1 and Execute
        String urlApi1 = "http://10.0.2.2/API's/api1.php";
        api1 = new apiCaller1();
        api1.execute(urlApi1);
    }

    public void refreshButton(View view) {
        // Updating date and time and resetting the timer
        updateTimer = 1;
        updatedText.setText("Updated " + updateTimer++ + " mins ago");
        displayTime();

        // Recalling API1 to get the latest rates
        String urlApi1 = "http://10.0.2.2/API's/api1.php";
        api1 = new apiCaller1();
        api1.execute(urlApi1);

        // Rotate animation
        refreshButton.startAnimation(rotateAnimation);
    }

    public class apiCaller1 extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            // URL and HTTP initialization to connect to API 1
            URL url;
            HttpURLConnection http;

            try {
                url = new URL(urls[0]);
                http = (HttpURLConnection) url.openConnection();

                // Retrieve API 1 content
                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                // Read API 1 content line by line
                BufferedReader br = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();

                // Append API 1 content to empty string
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }

                br.close();
                // Return content from API 1
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String values) {
            super.onPostExecute(values);
            try {

                DecimalFormat formatter = new DecimalFormat("#,###");

                // Splitting the returned values from the API
                String[] splitValues = values.split("]");

                // Getting the buy rate and its date
                String[] splitBuy = splitValues[0].split(",");

                // Using formatter function to place a comma every 3 digits
                String buyRate = String.valueOf(formatter.format(Integer.parseInt(splitBuy[1])));

                // Getting the sell rate and its date
                String[] splitSell = splitValues[1].split(",");
                String sellRate = String.valueOf(formatter.format(Integer.parseInt(splitSell[1])));

                // Setting the updated buy and sell rates to the interface
                buyText.setText("1 USD at " + buyRate + " LBP");
                sellText.setText("1 USD at " + sellRate + " LBP");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void goToCalculator(View view) {
        // Create an intent and start the the calculator activity
        Intent goToCalculator = new Intent(getApplicationContext(), Calculator.class);

        // Sending buy and sell rates to the calculator activity
        goToCalculator.putExtra(buyDailyRate, buyText.getText().toString());
        goToCalculator.putExtra(sellDailyRate, sellText.getText().toString());

        startActivity(goToCalculator);
    }

    public void displayTime() {
        // Get time and date from last update
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        int minutes = rightNow.get(Calendar.MINUTE);
        int day = rightNow.get(Calendar.DAY_OF_MONTH);
        int month = rightNow.get(Calendar.MONTH) + 1;
        int year = rightNow.get(Calendar.YEAR);

        // Display time and date on the UI
        String strHour = String.valueOf(hour);
        String strMinutes = String.valueOf(minutes);
        String strDay = String.valueOf(day);
        String strMonth = String.valueOf(month);
        if (hour < 10) {
            strHour = "0" + strHour;
        }
        if (minutes < 10) {
            strMinutes = "0" + strHour;
        }
        if (day < 10) {
            strDay = "0" + strDay;
        }
        if (month < 10) {
            strMonth = "0" + month;
        }
        updatedText2.setText("at " + strHour + ":" + strMinutes + " " + strDay + "-" + strMonth + "-" + year);
    }
}