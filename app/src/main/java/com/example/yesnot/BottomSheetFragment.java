package com.example.yesnot;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.yesnot.databinding.FragmentBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class BottomSheetFragment extends BottomSheetDialogFragment {
    private FragmentBottomSheetBinding binding;
    final int[] UpLimitGeneration = new int[1];

    public String[] values = new String[100];
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        binding = FragmentBottomSheetBinding.inflate(getLayoutInflater());
        binding.dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        UpLimitGeneration[0] = getArguments().getInt("bundleInt");

        values = getArguments().getStringArray("bundleArray");


        TableLayout tableLayout = binding.layout;
        EditText[] editTexts = new EditText[UpLimitGeneration[0]];
        TableRow[] tableRows = new TableRow[UpLimitGeneration[0]];
        TextView[] textViews = new TextView[UpLimitGeneration[0]];

        for (int i = 0; i < UpLimitGeneration[0]; ++i) {
            editTexts[i] = new EditText(getContext());
            tableRows[i] = new TableRow(getContext());
            textViews[i] = new TextView(getContext());

            textViews[i].setTextColor(Color.BLACK);
            textViews[i].setTextSize(18);
            textViews[i].setText((i + 1) + "    : ");
            textViews[i].setGravity(Gravity.CENTER);
            tableRows[i].addView(textViews[i], new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.1f));

            editTexts[i].setTextColor(Color.BLACK);
            editTexts[i].setTextSize(18);
            editTexts[i].setBackgroundColor(Color.TRANSPARENT);

            if (values[i] == null) {
                editTexts[i].setText("" + (i + 1));
            }
            else {
                editTexts[i].setText(values[i]);
            }

            editTexts[i].setWidth(0);
            editTexts[i].setId(i);
            tableRows[i].addView(editTexts[i], new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));


            tableLayout.addView(tableRows[i]);
        }


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BlankFragment3.editBtn.setEnabled(true);
        EditText[] editTexts = new EditText[UpLimitGeneration[0]];
        for (int i = 0; i < UpLimitGeneration[0]; i++){
            editTexts[i] = getView().findViewById(i);
            String value = editTexts[i].getText().toString();
            values[i] = value;
        }
        Bundle bundle = new Bundle();
        bundle.putStringArray("bundlekey2", values);
        getParentFragmentManager().setFragmentResult("loviarry", bundle);
    }
}