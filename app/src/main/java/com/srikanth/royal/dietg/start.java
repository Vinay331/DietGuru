package com.srikanth.royal.dietg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.srikanth.royal.dietg.Model.Users;
import com.srikanth.royal.dietg.Prevalent.Prevalent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

public class start extends AppCompatActivity {
    ProgressDialog loadingbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button join = (Button) findViewById(R.id.join);
        Button Alogin = (Button) findViewById(R.id.Alogin);
        loadingbar=new ProgressDialog(this);
        Paper.init(this);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(start.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        Alogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(start.this, loginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);
        if (UserPhoneKey != "" && UserPasswordKey != "") {
            if (!TextUtils.isEmpty(UserPhoneKey)  && !TextUtils.isEmpty(UserPasswordKey))
            {
                Access(UserPhoneKey,UserPasswordKey);
                loadingbar.setTitle("Already Logged in");
                loadingbar.setMessage("Please wait");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();
            }

        }
    }

    private void Access(final String phone, final String password) {

        final DatabaseReference Rootref;
        Rootref= FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("Users").child(phone).exists())
                {
                    Users userdata=dataSnapshot.child("Users").child(phone).getValue(Users.class);

                    if (userdata.getPhone().equals(phone))
                    {
                        if (userdata.getPassword().equals(password))
                        {
                            Toast.makeText(start.this, "Logged in succesfull", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                            Intent intent= new Intent(start.this,Home.class);
                            Prevalent.CurrentonlineUser=userdata;
                            startActivity(intent);
                         finish();

                        }
                        else {
                            loadingbar.dismiss();
                            Toast.makeText(start.this, "password incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(start.this, phone+"not exist", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Intent intent= new Intent(start.this,start.class);
                    startActivity(intent);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
