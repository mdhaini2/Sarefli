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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // These lines remove the action and title bars
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        String url = "http://10.0.2.2/TheBroallers/TheBroallers/api1.php";
        DownloadTask Task = new DownloadTask();
        Task.execute(url);
    }

    public class DownloadTask extends AsyncTask<String,Void,String> {
        protected String doInBackground(String... urls){
            String result = "";
            URL url;
            HttpURLConnection http;
            try{
                url = new URL(urls[0]);
                http =(HttpURLConnection) url.openConnection();
                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader br = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();

                String line;
                while((line = br.readLine())!= null){
                    sb.append(line + "\n");
                }
                br.close();
                if(sb.toString().equalsIgnoreCase("Request Error")){

                }
                Log.i("API",sb.toString());
                Log.i("Lenght", String.valueOf(sb.toString().length()));
                return sb.toString();

            }
            catch(Exception e){
                e.printStackTrace();
                return null;
            }

        }

    }

    public void goToCalculator(View view){
        // Create an intent and start the the calculator activity
        Intent i1 = new Intent(getApplicationContext(), Calculator.class);
        startActivity(i1);
    }


}