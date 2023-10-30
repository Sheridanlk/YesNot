package com.example.yesnot;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

public class MainActivity extends AppCompatActivity {

    private Button generate;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.textView);
        generate = findViewById(R.id.button);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.random.org/integers/?num=1&min=1&max=10000&col=1&base=10&format=plain&rnd=new";
                if (NetworkMenager.isNetworkAvailable(getBaseContext())){
                    new GetUrlData().execute(url);
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "No internet connecton", Toast.LENGTH_SHORT);
                    toast.show();
                    result.setText("d");
                }
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
    private class GetUrlData extends AsyncTask<String, String, String> {

        protected void onPreExecute(){
            super.onPreExecute();
            result.setText("Wait...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String reply = null;
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) new URL(strings[0]).openConnection();

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

            return reply;
        }

        @Override
        protected void onPostExecute(String reply){
            super.onPostExecute(reply);
            result.setText(reply);
        }
    }
}