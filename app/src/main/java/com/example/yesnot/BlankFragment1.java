package com.example.yesnot;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class BlankFragment1 extends Fragment {
    private static TextView result;
    private static Toast toast;
    private static Button generate;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank1, container, false);
    }

    @Override
    public void  onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        result = view.findViewById(R.id.textView);
        generate = view.findViewById(R.id.button);
        toast = Toast.makeText(view.getContext(), "No internet connecton", Toast.LENGTH_SHORT);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetAnswer().execute();
            }
        });
    }
    private class GetAnswer extends AsyncTask<String, String, String> {
        protected void onPreExecute(){
            super.onPreExecute();
            result.setText("Wait...");
            generate.setEnabled(false);

        }
        @Override
        protected String doInBackground(String... strings) {
            String reply = null;
            if(MainActivity.NetworkMenager.isNetworkAvailable(getContext())){
                reply = MainActivity.AnswerMenager.ApiAnswer();
            }
            else{
                toast.cancel();
                toast.show();
                reply = MainActivity.AnswerMenager.RandomAnswer();
            }
            return reply;
        }
        @Override
        protected void onPostExecute(String reply){
            super.onPostExecute(reply);
            generate.setEnabled(true);
            result.setText(reply);
        }
    }
}