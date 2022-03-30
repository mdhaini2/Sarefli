package com.dhaini.sarefli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {
    TextView buy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // These lines remove the action and title bars
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        String url = "http://10.0.2.2/TheBroallers/TheBroallers/api1.php";


        apiCaller1 api1 = new apiCaller1();
        api1.execute(url);



        buy =  (TextView) findViewById(R.id.tv_buyText2);

    }

    public class apiCaller1 extends AsyncTask<String,Void,String> {
        protected String doInBackground(String... urls){

            URL url;
            HttpURLConnection http;

            try{
                url = new URL(urls[0]);
                http =(HttpURLConnection) url.openConnection();

                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                BufferedReader breader = new BufferedReader(reader);
                StringBuilder sbuilder = new StringBuilder();

                String line = "";

                while((line = breader.readLine())!= null){
                    sbuilder.append(line + "\n");
                }

                breader.close();
                return sbuilder.toString();

            }
            catch(Exception e){
                e.printStackTrace();
                return null;
            }

        }
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            try{


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




}