package com.example.laudien.inflationcalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener{

    EditText editText_numberToday, editText_inflation, editText_years;
    TextView textView_result, textView_answer, textView_result2;
    Button button_calc;

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

        editText_years.setOnEditorActionListener(this);
    }

    public void calc(View view){
        if(editText_inflation.getText().toString().trim().equals("") ||
                editText_numberToday.getText().toString().trim().equals("") ||
                editText_years.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), "Es wurde nicht alles ausgefüllt!", Toast.LENGTH_SHORT).show();
            textView_result.setText("Fehler!");
            textView_answer.setText("");
            textView_result2.setText("");
            return;
        }
        double numberToday = Double.valueOf(editText_numberToday.getText().toString());
        long inflation = Long.valueOf(editText_inflation.getText().toString());
        long years = Long.valueOf(editText_years.getText().toString());
        double result = numberToday;

        for(int i = 0; i < years; i++){
            result -= result * inflation/100;
        }
        result = Math.round(100.0 * result) / 100.0;
        textView_answer.setText(numberToday + "€ in " + years + " Jahren sind heute");
        textView_result.setText(String.valueOf(result) + "€");
        textView_result2.setText("wert!");
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
}
