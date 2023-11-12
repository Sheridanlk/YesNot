package com.example.yesnot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_FILE = "Clicks";
    private static TextView result;
    private static Toast toast;
    static SharedPreferences settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.textView);
        Button generate = findViewById(R.id.button);
        toast = Toast.makeText(getApplicationContext(), "No internet connecton", Toast.LENGTH_SHORT);
        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetAnswer().execute();
            }
        });
    }

    public static class NetworkMenager{
        public static boolean isNetworkAvailable(Context context){
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
    }
    public static class Pref{
        public static void saveClicks(int clicks){
            SharedPreferences.Editor prefEditor = settings.edit();
            prefEditor.putInt("YesNoClicks", clicks);
            prefEditor.apply();
        }
        public static int getClicks(){
                int clicks = settings.getInt("YesNoClicks", 0);
                return clicks;
        }
    }
    private static class AnswerMenager{
        public static String  isEven(int number){
            if(number % 2 == 0){
                result.setTextColor(Color.GREEN);
                return "Yes";
            }
            else {
                result.setTextColor(Color.RED);
                return "No";
            }
        }
        public static String  ApiAnswer(){
            int clicks = Pref.getClicks();
            String url = "https://www.random.org/integers/?num=1&min=1&max=10000&col=1&base=10&format=plain&rnd=new";
            String reply = null;
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) new URL(url).openConnection();

                connection.setRequestMethod("GET");
                connection.setUseCaches(false);
                connection.connect();
                if (HttpURLConnection.HTTP_OK == connection.getResponseCode()){
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    reply = buffer.readLine();
                }
                else {
                    return null;
                }
            }
            catch (Throwable cause){
                cause.printStackTrace();

            }
            finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            if (reply != null){
                clicks += 1;
                Pref.saveClicks(clicks);
                return isEven(Integer.parseInt(reply));
            }
            else {
                return "error";
            }

        }
        public static String RandomAnswer(){
            try {
                Thread.sleep(450);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int clicks = Pref.getClicks();
            Random random = new Random();
            int number = (random.nextInt(1000) + 1) + clicks;
            clicks = 0;
            Pref.saveClicks(clicks);
            return isEven(number);
        }

    }
    private class GetAnswer extends AsyncTask<String, String, String> {
        protected void onPreExecute(){
            super.onPreExecute();
            result.setTextColor(Color.WHITE);
            result.setText("Wait...");
        }
        @Override
        protected String doInBackground(String... strings) {
            String reply = null;
            if(NetworkMenager.isNetworkAvailable(getBaseContext())){
                reply = AnswerMenager.ApiAnswer();
                return reply;
            }
            else{
                toast.show();
                reply = AnswerMenager.RandomAnswer();
                return reply;
            }
        }
        @Override
        protected void onPostExecute(String reply){
            super.onPostExecute(reply);
            result.setText(reply);
        }
    }
}