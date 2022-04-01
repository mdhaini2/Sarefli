package com.dhaini.sarefli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Calculator extends AppCompatActivity {
    apiCaller2 api2;
    Button btnConverter;
    EditText num_amountValue;
    ImageView img_fromCurrency;
    ImageView img_toCurrency;
    String from = "USD";
    String to = "LBP";
    TextView tv_buyAtRate;
    TextView tv_sellAtRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // These lines remove the action and title bars
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_calculator);

        num_amountValue = (EditText) findViewById(R.id.num_amountValue);
        btnConverter = (Button) findViewById(R.id.btn_convert);
        img_fromCurrency = (ImageView) findViewById(R.id.image_usdbar);
        img_toCurrency = (ImageView) findViewById(R.id.image_lbpbar);
        tv_buyAtRate = (TextView) findViewById(R.id.tv_buyAtRate);
        tv_sellAtRate = (TextView) findViewById(R.id.tv_sellAtRate);


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
                Log.i("values",values.replace("\"\"",""));
                NumberFormat formatter = NumberFormat.getInstance(Locale.US);
                values.replace("\"\"", "" );
                String[] splitValues = values.split(" ");
                BigDecimal bigDecimalBuy = new BigDecimal(splitValues[0].trim());
                BigDecimal bigDecimalSell = new BigDecimal(splitValues[1].trim());

                String buyAt = String.valueOf(formatter.format(bigDecimalBuy));
                String sellAt = String.valueOf(formatter.format(bigDecimalSell));

                tv_buyAtRate.setText(buyAt + " " + to);
                tv_sellAtRate.setText(sellAt + " " + to);

            }
            catch(Exception e){
                e.printStackTrace();


            }
        }
    }

    public void goToHome(View view){
        // Create an intent and start the the main activity
        Intent i2 = new Intent(getApplicationContext(), MainActivity.class);
        num_amountValue.setText("");
        tv_buyAtRate.setText("");
        tv_sellAtRate.setText("");

        startActivity(i2);
    }
    public void convert(View view){
        // Get the daily buy and sell rate from the main and extract from the interface the data we need to use to call api 2.
        String amount = num_amountValue.getText().toString();
        String internal = from;
        String external = to;
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
    public void changeCurrency(View view){
        String temp = from;
        from = to;
        to = temp;
        if(from.equalsIgnoreCase("USD")){
            img_fromCurrency.setImageResource(R.drawable.usbar);
            img_toCurrency.setImageResource(R.drawable.lebbar);
        }else{
            img_fromCurrency.setImageResource(R.drawable.lebbar);
            img_toCurrency.setImageResource(R.drawable.usbar);
        }


    }
}