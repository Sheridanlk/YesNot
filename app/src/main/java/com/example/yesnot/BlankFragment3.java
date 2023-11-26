package com.example.yesnot;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yesnot.databinding.FragmentBlank3Binding;
import com.example.yesnot.sampledata.AnswerManager;
import com.example.yesnot.sampledata.NetworkManager;


public class BlankFragment3 extends Fragment {
    public static Button generateBtn, plusBtn, minusBtn, editBtn;
    public static EditText editText;
    public static TextView textView;

    public static Toast toast;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank3, container, false);
    }
    public void  onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        generateBtn = view.findViewById(R.id.GenerateBtn);
        plusBtn = view.findViewById(R.id.PlusBtn);
        minusBtn = view.findViewById(R.id.MinusBtn);
        editBtn = view.findViewById(R.id.EditBtn);
        editText = view.findViewById(R.id.editTextNumber);
        textView = view.findViewById(R.id.textView);
        editText.setCursorVisible(false);
        toast = Toast.makeText(view.getContext(), "No internet connecton", Toast.LENGTH_SHORT);
        final int[] UpLimitGeneration = new int[1];
        UpLimitGeneration[0] = 3;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("") && Integer.parseInt(s.toString()) > 0) {
                    if (Integer.parseInt(s.toString()) <= 100) {
                        UpLimitGeneration[0] = Integer.parseInt(s.toString());
                        UpLimitGeneration[0] = Integer.parseInt(s.toString());
                    }
                    else {
                        UpLimitGeneration[0] = 100;
                        editText.setText("100");
                    }
                }
                else {
                    UpLimitGeneration[0] = 3;
                    editText.setText("3");
                    editText.selectAll();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new GetAnswer().execute(String.valueOf(UpLimitGeneration[0]));
            }
        });
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UpLimitGeneration[0] < 1000) {
                    UpLimitGeneration[0] += 1;
                    editText.setText(String.valueOf(UpLimitGeneration[0]));
                }
            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UpLimitGeneration[0] > 3) {
                    UpLimitGeneration[0] -= 1;
                    editText.setText(String.valueOf(UpLimitGeneration[0]));
                }
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(requireActivity().getSupportFragmentManager(), "sus");

            }
        });
    }


    private class GetAnswer extends AsyncTask<String, String, String> {
        protected void onPreExecute(){
            super.onPreExecute();
            textView.setText("Wait...");
            generateBtn.setEnabled(false);
        }
        @Override
        protected String doInBackground(String... strings) {
            String reply = null;
            int type = 3;
            if(NetworkManager.isNetworkAvailable(getContext())){
                reply = AnswerManager.ApiAnswer(Integer.parseInt(strings[0]), type);
            }
            else{
                toast.cancel();
                toast.show();
                reply = AnswerManager.RandomAnswer(Integer.parseInt(strings[0]), type);
            }
            return reply;
        }
        @Override
        protected void onPostExecute(String reply){
            super.onPostExecute(reply);
            generateBtn.setEnabled(true);
            textView.setText(reply);
        }
    }
}
