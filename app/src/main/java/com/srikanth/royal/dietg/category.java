package com.srikanth.royal.dietg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class category extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Button articles=(Button)findViewById(R.id.articles);
        Button recipies=(Button)findViewById(R.id.recipies);

        articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(category.this, AdminArticles.class);
                intent.putExtra("activity_category","Articles");
                startActivity(intent);
            }
        });

        recipies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(category.this, AdminRecipies.class);
                intent.putExtra("activity_category","Recipies");
                startActivity(intent);
            }
        });
    }
}
