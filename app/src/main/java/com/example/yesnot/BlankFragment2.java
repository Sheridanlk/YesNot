package com.example.yesnot;

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

import com.example.yesnot.sampledata.AnswerManager;
import com.example.yesnot.sampledata.NetworkManager;


public class BlankFragment2 extends Fragment {
    private static TextView result, header;
    private static Toast toast;
    private static Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank2, container, false);
    }

    public void  onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        result = view.findViewById(R.id.textView2);
        header = view.findViewById(R.id.textView22);
        button = view.findViewById(R.id.button2);
        toast = Toast.makeText(view.getContext(), "No internet connecton", Toast.LENGTH_SHORT);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                header.setText(" ");
                new GetAnswer().execute();
            }
        });
    }

    private class GetAnswer extends AsyncTask<String, String, String> {
        protected void onPreExecute(){
            super.onPreExecute();
            result.setText("Wait...");
            button.setEnabled(false);

        }
        @Override
        protected String doInBackground(String... strings) {
            String reply = null;
            int type = 2;
            if(NetworkManager.isNetworkAvailable(getContext())){
                reply = AnswerManager.ApiAnswer(1000, type);
            }
            else{
                toast.cancel();
                toast.show();
                reply = AnswerManager.RandomAnswer(1000, type);
            }
            return reply;
        }
        @Override
        protected void onPostExecute(String reply){
            super.onPostExecute(reply);
            button.setEnabled(true);
            result.setText(reply);
        }
    }
}