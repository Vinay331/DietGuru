package com.srikanth.royal.dietg;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.jar.Attributes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class calorieC extends AppCompatActivity {
    private EditText age;
    private EditText height;
    private EditText weight;

    private Button btn_calculate;
    RadioGroup rg, rg1;
    RadioButton rb, rb1, m, fm, no, li, mo, va;
    private String fresult;
    private String fresult1;
    private String fresult2;
    private String fresult3;
    int flag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie);


        age = (EditText) findViewById(R.id.age);
        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);

        btn_calculate = (Button) findViewById(R.id.btn_result);

        rg = findViewById(R.id.gender);
        rg1 = findViewById(R.id.activity);

        m = findViewById(R.id.male);
        fm = findViewById(R.id.female);
        no = findViewById(R.id.no_exercise);
        li = findViewById(R.id.light);
        mo = findViewById(R.id.moderate);
        va = findViewById(R.id.very_active);


        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculate();
                if (flag==1) {
                    Intent intent = new Intent(calorieC.this, Caloriedetails.class);
                    intent.putExtra("cresult", fresult);
                    intent.putExtra("cresult1", fresult1);
                    intent.putExtra("cresult2", fresult2);
                    intent.putExtra("cresult3", fresult3);

                    startActivity(intent);
                }

            }
        });


    }

    private void Calculate() {


        String heightStr = height.getText().toString();
        String weightStr = weight.getText().toString();
        String ageStr = age.getText().toString();


        if (TextUtils.isEmpty(weightStr)) {
            weight.setError("Weight  is required");
            return;
        } else if (TextUtils.isEmpty(heightStr)) {
            height.setError("Height  is required");
            return;
        } else if (TextUtils.isEmpty(ageStr)) {
            age.setError("Age  is required");
            return;
        } else {

            float weightValue = Float.parseFloat(weightStr);
            float heightValue = Float.parseFloat(heightStr);
            int ageValue = Integer.parseInt(ageStr);


            if (m.isChecked()) {

                double bmr = ((((weightValue * 6.23) + (heightValue * 12.7)) - (ageValue * 6.8)) + 66);

                if (no.isChecked()) {
                    bmr = bmr * 1.2;
                    Displaycalorie(bmr);

                    return;
                } else if (li.isChecked()) {
                    bmr = bmr * 1.375;
                    Displaycalorie(bmr);

                    return;

                } else if (mo.isChecked()) {
                    bmr = bmr * 1.55;
                    Displaycalorie(bmr);

                    return;

                } else if (va.isChecked()) {
                    bmr = bmr * 1.725;
                    Displaycalorie(bmr);

                    return;
                }


                return;
            } else if (fm.isChecked()) {

                double bmr = ((((weightValue * 4.35) + (heightValue * 4.7)) - (ageValue * 4.7)) + 655);
                if (no.isChecked()) {
                    bmr = bmr * 1.2;
                    Displaycalorie(bmr);

                    return;
                } else if (li.isChecked()) {
                    bmr = bmr * 1.375;
                    Displaycalorie(bmr);

                    return;

                } else if (mo.isChecked()) {
                    bmr = bmr * 1.55;
                    Displaycalorie(bmr);

                    return;

                } else if (va.isChecked()) {
                    bmr = bmr * 1.725;
                    Displaycalorie(bmr);

                    return;
                }


                return;

            }

        }

    }


    private void Displaycalorie(double bmr) {
        String doulebmr = Double.toString(bmr);
        float dublebmrva = Float.parseFloat(doulebmr);
        int dim = (int) dublebmrva;
        int dimw = (int) ((dublebmrva) * 90 / 100);
        int diw = (int) ((dublebmrva) * 79 / 100);
        int diewl = (int) ((dublebmrva) * 59 / 100);


        fresult = dim + "";
        fresult1 = dimw + "";
        fresult2 = diw + "";
        fresult3 = diewl + "";

        flag = 1;

    }
}
