package com.example.laudien.inflationcalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import static android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener, View.OnClickListener {

    EditText editText_numberToday, editText_inflation, editText_years;
    TextView textView_result, textView_answer, textView_result2;
    Button button_calc;
    NumberFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_numberToday = (EditText) findViewById(R.id.editText_numberToday);
        editText_inflation = (EditText) findViewById(R.id.editText_inflation);
        editText_years = (EditText) findViewById(R.id.editText_years);
        textView_result = (TextView) findViewById(R.id.textView_result);
        textView_answer = (TextView) findViewById(R.id.textView_answer);
        textView_result2 = (TextView) findViewById(R.id.textView_result2);
        button_calc = (Button) findViewById(R.id.button_calc);
        formatter = NumberFormat.getCurrencyInstance();

        button_calc.setOnClickListener(this);
        editText_years.setOnEditorActionListener(this);
    }

    public void calc(){
        if(editText_inflation.getText().toString().trim().equals("") ||
                editText_numberToday.getText().toString().trim().equals("") ||
                editText_years.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), getString(R.string.not_all_filled), Toast.LENGTH_SHORT).show();
            textView_result.setText(getString(R.string.error));
            textView_answer.setText("");
            textView_result2.setText("");
            return;
        }
        double numberToday = Double.valueOf(editText_numberToday.getText().toString());
        double inflation = Double.valueOf(editText_inflation.getText().toString());
        long years = Long.valueOf(editText_years.getText().toString());
        double result = numberToday;

        for(int i = 0; i < years; i++){
            result -= result * inflation/100;
        }
        result = Math.round(100.0 * result) / 100.0;
        textView_answer.setText(formatter.format(numberToday) + " in " + years + " " + getString(R.string.years_equals_today));
        textView_result.setText(formatter.format(result));
        textView_result2.setText(getString(R.string.wert));
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            InputMethodManager in = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
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
