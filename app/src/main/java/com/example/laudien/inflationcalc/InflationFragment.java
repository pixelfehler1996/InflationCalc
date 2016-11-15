package com.example.laudien.inflationcalc;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class InflationFragment extends Fragment implements TextView.OnEditorActionListener, View.OnClickListener {

    public static final int TYPE_INFLATION = 0;
    public static final int TYPE_VALUE_INCREASE = 1;
    private EditText editText_numberToday, editText_inflation, editText_years;
    private TextView textView_result, textView_answer, textView_result2;
    private Button button_calc;
    private NumberFormat formatter;
    private int type;

    public InflationFragment(){
        type = TYPE_INFLATION;
    }

    @SuppressLint("ValidFragment")
    public InflationFragment(int type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inflation, container, false);

        editText_numberToday = (EditText) view.findViewById(R.id.editText_numberToday);
        editText_inflation = (EditText) view.findViewById(R.id.editText_inflation);
        editText_years = (EditText) view.findViewById(R.id.editText_years);
        textView_result = (TextView) view.findViewById(R.id.textView_result);
        textView_answer = (TextView) view.findViewById(R.id.textView_answer);
        textView_result2 = (TextView) view.findViewById(R.id.textView_result2);
        button_calc = (Button) view.findViewById(R.id.button_calc);
        formatter = NumberFormat.getCurrencyInstance();

        button_calc.setOnClickListener(this);
        editText_years.setOnEditorActionListener(this);

        return view;
    }

    public void calc(){
        if(editText_inflation.getText().toString().trim().equals("") ||
                editText_numberToday.getText().toString().trim().equals("") ||
                editText_years.getText().toString().trim().equals("")){
            Toast.makeText(getContext().getApplicationContext(), getString(R.string.not_all_filled), Toast.LENGTH_SHORT).show();
            textView_result.setText(getString(R.string.error));
            textView_answer.setText("");
            textView_result2.setText("");
            return;
        }
        double numberToday = Double.valueOf(editText_numberToday.getText().toString());
        double inflation = Double.valueOf(editText_inflation.getText().toString());
        long years = Long.valueOf(editText_years.getText().toString());
        double result = numberToday;
        String stringAnswer = "";

        switch (type){
            case TYPE_INFLATION:
                for (int i = 0; i < years; i++) {
                    result -= result * inflation / 100;
                }
                stringAnswer = formatter.format(numberToday) + " in " + years + " " + getString(R.string.years_equals_today);
                break;
            case TYPE_VALUE_INCREASE:
                for(int i = 0; i < years; i++){
                    result += result * inflation / 100;
                }
                stringAnswer = getString(R.string.property_with_value) + " " + formatter.format(numberToday) + " " + getString(R.string.has_in) + " " + years
                        + " " + getString(R.string.years_value_of);
                break;
        }

        result = Math.round(100.0 * result) / 100.0;
        textView_answer.setText(stringAnswer);
        textView_result.setText(formatter.format(result));
        textView_result2.setText(getString(R.string.wert));
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            InputMethodManager in = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(editText_years.getApplicationWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        calc();
    }
}
