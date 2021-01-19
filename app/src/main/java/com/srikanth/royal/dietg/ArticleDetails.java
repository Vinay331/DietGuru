package com.srikanth.royal.dietg;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.srikanth.royal.dietg.Model.Items;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ArticleDetails extends AppCompatActivity {

    ImageView dImage;
    TextView dTitle,dShort,dDescription;
    String productId="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        dImage=(ImageView)findViewById(R.id.detail_image);
        dTitle=(TextView)findViewById(R.id.detail_title);
        dShort=(TextView)findViewById(R.id.detail_sd);
        dDescription=(TextView)findViewById(R.id.detail_d);

        productId=getIntent().getStringExtra("pid");
        getProductDetails(productId);

    }

    private void getProductDetails(String productId) {

        DatabaseReference pRef= FirebaseDatabase.getInstance().getReference().child("Items");
        pRef.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    Items items=dataSnapshot.getValue(Items.class);
                    dTitle.setText(items.getTitle());
                  dShort.setText(items.getShortdescription());
                    dDescription.setText(items.getDescription());
                    Picasso.get().load(items.getImage()).into(dImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
