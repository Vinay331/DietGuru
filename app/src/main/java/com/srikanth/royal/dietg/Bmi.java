package com.srikanth.royal.dietg;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Bmi extends AppCompatActivity {

    private EditText height;
    private EditText weight;

    private Button calculate;
    String bmiLabel = "";
    String bmiV = "";
    int flag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);

        calculate = (Button) findViewById(R.id.calc);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
                if (flag==1) {
                    Intent intent = new Intent(Bmi.this, Bmidetails.class);
                    intent.putExtra("bmiv", bmiV);
                    intent.putExtra("bmit", bmiLabel);
                    startActivity(intent);
                }

            }
        });

    }

    public void calculateBMI() {
        String heightStr = height.getText().toString();
        String weightStr = weight.getText().toString();

        if (TextUtils.isEmpty(weightStr)) {
            weight.setError("Weight  is required");
            return;
        } else if (TextUtils.isEmpty(heightStr)) {
            height.setError("Height  is required");
            return;
        } else {
            float heightValue = Float.parseFloat(heightStr) / 100;
            float weightValue = Float.parseFloat(weightStr);


            float bmi = weightValue / (heightValue * heightValue);
            bmiV = bmi + "";
            displayBMI(bmi);
        }


    }

    private void displayBMI(float bmi) {


        if (Float.compare(bmi, 15f) <= 0) {
            bmiLabel = getString(R.string.very_severely_underweight);
        } else if (Float.compare(bmi, 15f) > 0 && Float.compare(bmi, 16f) <= 0) {
            bmiLabel = getString(R.string.severely_underweight);
        } else if (Float.compare(bmi, 16f) > 0 && Float.compare(bmi, 18.5f) <= 0) {
            bmiLabel = getString(R.string.underweight);
        } else if (Float.compare(bmi, 18.5f) > 0 && Float.compare(bmi, 25f) <= 0) {
            bmiLabel = getString(R.string.normal);
        } else if (Float.compare(bmi, 25f) > 0 && Float.compare(bmi, 30f) <= 0) {
            bmiLabel = getString(R.string.overweight);
        } else if (Float.compare(bmi, 30f) > 0 && Float.compare(bmi, 35f) <= 0) {
            bmiLabel = getString(R.string.obese_class_i);
        } else if (Float.compare(bmi, 35f) > 0 && Float.compare(bmi, 40f) <= 0) {
            bmiLabel = getString(R.string.obese_class_ii);
        } else {
            bmiLabel = getString(R.string.obese_class_iii);
        }

        flag = 1;
    }
}
