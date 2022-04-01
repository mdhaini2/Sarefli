package com.dhaini.sarefli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class Calculator extends AppCompatActivity {
    apiCaller2 api2;
    Button btnConverter;
    EditText num_amountValue;
    TextView tv_fromCurrency;
    TextView tv_toCurrency;
    TextView num_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // These lines remove the action and title bars
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_calculator);

        num_amountValue = (EditText) findViewById(R.id.num_amountValue);
        btnConverter = (Button) findViewById(R.id.btn_convert);
        tv_fromCurrency = (TextView) findViewById(R.id.tv_fromCurrency);
        tv_toCurrency = (TextView) findViewById(R.id.tv_toCurrency);
        num_result = (TextView) findViewById(R.id.num_result);

    }
    public class apiCaller2 extends AsyncTask<String,Void,String> {
        protected String doInBackground(String... urls){
            // URL and HTTP initialization to connect to API 2
            URL url;
            HttpURLConnection http;

            try{
                url = new URL(urls[0]);
                http =(HttpURLConnection) url.openConnection();

                // Retrieve API 2 content
                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                // Read API 2 content line by line
                BufferedReader br = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();

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
                Log.i("Error",values);
                num_result.setText(values);
            }
            catch(Exception e){
                e.printStackTrace();


            }
        }
    }

    public void goToHome(View view){
        // Create an intent and start the the main activity
        Intent i2 = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i2);
    }
    public void convert(View view){
        // Get the daily buy and sell rate from the main and extract from the interface the data we need to use to call api 2.
        String amount = num_amountValue.getText().toString();
        String internal = tv_fromCurrency.getText().toString();
        String external = tv_toCurrency.getText().toString();
        Intent intent =getIntent();
        String buyRate = intent.getStringExtra(MainActivity.buyDailyRate).split(" ")[3].replace(",","");
        String sellRate = intent.getStringExtra(MainActivity.sellDailyRate).split(" ")[3].replace(",","");

        //constructing api2 url and adding the data retrieved to it
        String urlApi2 = "http://10.0.2.2/API's/api2.php?Amount="+amount+"&Internal="+internal+"&External="+external
                +"&Daily_Rate_Buy="+buyRate+"&Daily_Rate_Sell="+sellRate;

        //call api2 and execute
        api2 = new apiCaller2();
        api2.execute(urlApi2);

    }
}