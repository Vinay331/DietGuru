package com.srikanth.royal.dietg;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Bmidetails extends AppCompatActivity {
    private String result_v,result_t;
    private TextView bmi_v,bmi_t;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_details);
        bmi_v = (TextView) findViewById(R.id.bmi_value);
        bmi_t = (TextView) findViewById(R.id.bmi_txt);
        result_v = getIntent().getExtras().get("bmiv").toString();
        result_t = getIntent().getExtras().get("bmit").toString();
        bmi_v.setText(result_v);
        bmi_t.setText(result_t);

    }
}
