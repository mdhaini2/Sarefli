package com.dhaini.sarefli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    TextView buyText;
    TextView sellText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        // These lines remove the action and title bars
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        buyText = (TextView) findViewById(R.id.tv_buyText2);
        sellText = (TextView) findViewById(R.id.tv_sellText2);

        // API 1 URL
        String urlApi1 = "http://10.0.2.2/API's/api1.php";
        apiCaller1 api1 = new apiCaller1();
        api1.execute(urlApi1);


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
                if(sb.toString().contains("Request Error")){
                    doInBackground();
                }
                br.close();
                Log.i("Error",sb.toString());
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
                  String buyDate = convertTimeStamp(splitBuy[0].substring(1)).toString();

                  // Getting the sell rate and its date
                  String[] splitSell = splitValues[1].split(",");
                  String sellRate = String.valueOf(formatter.format(Integer.parseInt(splitSell[1]))) ;
                  String sellDate = convertTimeStamp(splitBuy[0].substring(1)).toString();

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
        Intent i1 = new Intent(getApplicationContext(), Calculator.class);
        startActivity(i1);
    }


    public Date convertTimeStamp(String timeStamp){
        long time = Long.parseLong(timeStamp);
        Date date = new Date(time);
        return date;
    }
}