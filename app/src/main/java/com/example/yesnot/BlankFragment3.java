package com.example.yesnot;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yesnot.sampledata.AnswerManager;
import com.example.yesnot.sampledata.NetworkManager;

import java.util.ArrayList;


public class BlankFragment3 extends Fragment {
    public static Button generateBtn;
    public static ImageButton plusBtn, editBtn, minusBtn;
    public static EditText editText;
    public static TextView textView;
    public String[] values = new String[100];
    final int[] UpLimitGeneration = new int[1];



    public static Toast toast, toasterror1, toasterror2;
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
        toasterror1 = Toast.makeText(view.getContext(), "Число должно быть не меньше 2", Toast.LENGTH_SHORT);
        toasterror2 = Toast.makeText(view.getContext(), "Число должно быть не больше 100", Toast.LENGTH_SHORT);
        UpLimitGeneration[0] = 3;

        getParentFragmentManager().setFragmentResultListener("loviarry", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                values = bundle.getStringArray("bundlekey2");
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("") && Integer.parseInt(s.toString()) > 0) {
                    UpLimitGeneration[0] = Integer.parseInt(s.toString());;
                }
                else {
                    UpLimitGeneration[0] = 2;
                    editText.setText("2");
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
                if (UpLimitGeneration[0] < 2){
                    editText.setText("2");
                    toasterror1.cancel();
                    toasterror1.show();
                }
                else {
                    if(UpLimitGeneration[0] > 100){
                        editText.setText("100");
                        toasterror2.cancel();
                        toasterror2.show();
                    }
                    else{
                        new GetAnswer().execute(String.valueOf(UpLimitGeneration[0]));
                    }
                }
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
                editBtn.setEnabled(false);
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(requireActivity().getSupportFragmentManager(), "sus");
                Bundle bundle = new Bundle();
                bundle.putInt("bundleInt", UpLimitGeneration[0]);
                bundle.putStringArray("bundleArray", values);
                bottomSheetFragment.setArguments(bundle);
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

            boolean flag = true;
            for (int i = 0; i < UpLimitGeneration[0]; i++){
                if (values[i] == null){
                    flag = false;
                    break;
                }
            }
            if (flag){
                textView.setText(values[Integer.parseInt(reply) - 1]);
            }
            else {
                textView.setText(reply);
            }

        }
    }



}
