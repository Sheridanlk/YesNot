package com.example.yesnot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

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

    ViewPager2 myViewPager2;
    Adapter myAdapter;
    private static final String PREFS_FILE = "Clicks";
    static SharedPreferences settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);

        myViewPager2 = findViewById(R.id.viewPager2);
        myAdapter = new Adapter(getSupportFragmentManager(), getLifecycle());

        myAdapter.addFragment(new BlankFragment2());
        myAdapter.addFragment(new BlankFragment1());
        myAdapter.addFragment(new BlankFragment3());

        myViewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        myViewPager2.setAdapter(myAdapter);
        myViewPager2.setCurrentItem(1,false);
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
    static class AnswerMenager {
        public static String isEven(int number) {
            if (number % 2 == 0) {
                return "Yes";
            } else {
                return "No";
            }
        }
        public static String  ApiAnswer(){
            int clicks = MainActivity.Pref.getClicks();
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
                MainActivity.Pref.saveClicks(clicks);
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
            int clicks = MainActivity.Pref.getClicks();
            Random random = new Random();
            int number = (random.nextInt(1000) + 1) + clicks;
            clicks = 0;
            MainActivity.Pref.saveClicks(clicks);
            return isEven(number);
        }

    }
}
