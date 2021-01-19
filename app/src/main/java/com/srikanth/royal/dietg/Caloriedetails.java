package com.srikanth.royal.dietg;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Caloriedetails extends AppCompatActivity {
    private String result, result1,result2,result3;
    private TextView result_txt,result_txt1,result_txt2,result_txt3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caloriec_details);
        result_txt = (TextView) findViewById(R.id.cresult_txt);
        result_txt1 = (TextView) findViewById(R.id.cresult_txt1);
        result_txt2 = (TextView) findViewById(R.id.cresult_txt2);
        result_txt3 = (TextView) findViewById(R.id.cresult_txt3);
        result = getIntent().getExtras().get("cresult").toString();
        result1 = getIntent().getExtras().get("cresult1").toString();
        result2 = getIntent().getExtras().get("cresult2").toString();
        result3 = getIntent().getExtras().get("cresult3").toString();
        result_txt.setText(result);
        result_txt1.setText(result1);
        result_txt2.setText(result2);
        result_txt3.setText(result3);
    }
}
