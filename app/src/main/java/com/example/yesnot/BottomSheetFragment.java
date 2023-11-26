package com.example.yesnot;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.yesnot.databinding.FragmentBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    private FragmentBottomSheetBinding binding;
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,@NonNull Bundle savedInstanceState) {
        binding = FragmentBottomSheetBinding.inflate(getLayoutInflater());

        binding.dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        int n = 5;
        TableLayout tableLayout = binding.layout ;
        EditText[] editTexts = new EditText[n];
        TableRow[] tableRows = new  TableRow[n];
        TextView[] textViews = new TextView[n];
        for (int i = 0; i < n; ++i) {
            editTexts[i] = new EditText(getContext());
            tableRows[i] = new TableRow(getContext());
            textViews[i] = new TextView(getContext());


            textViews[i].setTextColor(Color.BLACK);
            textViews[i].setTextSize(18);
            textViews[i].setText((i+1) + ":");
            textViews[i].setGravity(Gravity.CENTER);
            tableRows[i].addView(textViews[i], new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.2f));

            editTexts[i].setTextColor(Color.BLACK);
            editTexts[i].setTextSize(18);
            editTexts[i].setBackgroundColor(Color.TRANSPARENT);
            editTexts[i].setText(" " + (i+1));
            editTexts[i].setId(i);
            tableRows[i].addView(editTexts[i], new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));

            tableLayout.addView(tableRows[i]);
        }

        return binding.getRoot();

    }
}