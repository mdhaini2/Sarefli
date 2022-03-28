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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}