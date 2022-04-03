package com.dhaini.sarefli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

public class Calculator extends AppCompatActivity {
    apiCaller2 api2;
    Button convertButton;
    EditText amountValue;
    ImageView fromCurrency;
    ImageView toCurrency;
    String from = "USD";
    String to = "LBP";
    TextView buyAtRate;
    TextView sellAtRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // These lines remove the action and title bars
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_calculator);

        amountValue = (EditText) findViewById(R.id.num_amountValue);
        convertButton = (Button) findViewById(R.id.btn_convert);
        fromCurrency = (ImageView) findViewById(R.id.image_usdbar);
        toCurrency = (ImageView) findViewById(R.id.image_lbpbar);
        buyAtRate = (TextView) findViewById(R.id.tv_buyAtRate);
        sellAtRate = (TextView) findViewById(R.id.tv_sellAtRate);

        // Initial string values
        amountValue.setText("");
        buyAtRate.setText("");
        sellAtRate.setText("");
    }

    public class apiCaller2 extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            // URL and HTTP initialization to connect to API 2
            URL url;
            HttpURLConnection http;

            try {
                // Connect to API 2
                url = new URL(urls[0]);
                http = (HttpURLConnection) url.openConnection();

                // Retrieve API 2 content
                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                // Read API 2 content line by line
                BufferedReader br = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }

                br.close();
                // Return content from API 2
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String values) {
            super.onPostExecute(values);
            try {

                NumberFormat formatter = NumberFormat.getInstance(Locale.US);

                Log.i("Values",values);

                // Getting the values from API 2 as JSON Object
                JSONObject json = new JSONObject(values);

                // Getting the buy and sell results rates returned from API 2. Using BigDecimal class in case were dealing with huge numbers.
                BigDecimal bigDecimalBuy = new BigDecimal(json.getString("Buy at"));
                BigDecimal bigDecimalSell = new BigDecimal(json.getString("Sell at"));

                String buyAt = "Buy " + String.valueOf(formatter.format(bigDecimalBuy));
                String sellAt = "Sell " + String.valueOf(formatter.format(bigDecimalSell));

                // Displaying on the UI
                buyAtRate.setText(buyAt + " " + to);
                sellAtRate.setText(sellAt + " " + to);

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    public void goToHome(View view) {
        // Create an intent and start the the main activity
        Intent i2 = new Intent(getApplicationContext(), MainActivity.class);

        // Resetting input and result fields
        amountValue.setText("");
        buyAtRate.setText("");
        sellAtRate.setText("");

        startActivity(i2);
    }

    public void convert(View view) {
        // Get the daily buy and sell rate from main and extract from the interface the data we need to use to call API 2
        String amount = amountValue.getText().toString();
        String internal = from;
        String external = to;
        Intent intent = getIntent();
        String buyRate = intent.getStringExtra(MainActivity.buyDailyRate).split(" ")[3].replace(",", "");
        String sellRate = intent.getStringExtra(MainActivity.sellDailyRate).split(" ")[3].replace(",", "");

        // Constructing API 2 url and adding the data retrieved to it
        String urlApi2 = "http://10.0.2.2/API's/api2.php?Amount=" + amount + "&Internal=" + internal + "&External=" + external
                + "&Daily_Rate_Buy=" + buyRate + "&Daily_Rate_Sell=" + sellRate;

        // Calling API 2 to get the results of this conversion and adding it to our database
        api2 = new apiCaller2();
        api2.execute(urlApi2);
    }

    public void changeCurrency(View view) {

        // Switching the "from" currency and the "to" currency
        String temp = from;
        from = to;
        to = temp;

        // Swapping images
        if (from.equalsIgnoreCase("USD")) {
            fromCurrency.setImageResource(R.drawable.usbar);
            toCurrency.setImageResource(R.drawable.lebbar);
        } else {
            fromCurrency.setImageResource(R.drawable.lebbar);
            toCurrency.setImageResource(R.drawable.usbar);
        }
    }
} //