package com.srikanth.royal.dietg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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

public class loginActivity extends AppCompatActivity {
    Button login;
    EditText Lphone, Lpassword;
    ProgressDialog loadingbar;
    String parentDbname = "Users";
    CheckBox checkRememberMe;
    TextView adminlink, nadminlink;
    TextView login_txt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Lphone = (EditText) findViewById(R.id.Lphone);
        Lpassword = (EditText) findViewById(R.id.Lpassword);
        login = (Button) findViewById(R.id.btn_login);
        checkRememberMe = (CheckBox) findViewById(R.id.remember_me);
login_txt=(TextView)findViewById(R.id.login_txt);
        adminlink = (TextView) findViewById(R.id.admin_link);

        nadminlink = (TextView) findViewById(R.id.notadmin);


        Paper.init(this);
        loadingbar = new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

        adminlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setText("Admin Login");
                login_txt.setText("Admin Login");
                adminlink.setVisibility(View.INVISIBLE);
                nadminlink.setVisibility(View.VISIBLE);
                parentDbname = "Admins";
            }
        });


        nadminlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setText("Login");
login_txt.setText("User Login");
                adminlink.setVisibility(View.VISIBLE);
                nadminlink.setVisibility(View.INVISIBLE);
                parentDbname = "Users";
            }
        });
    }

    private void LoginUser() {

        String phone = Lphone.getText().toString();
        String password = Lpassword.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "phone is required", Toast.LENGTH_SHORT).show();
            Lphone.setError("Phone is Required");
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            Lpassword.setError("Password is Required");
        } else {
            loadingbar.setTitle("Login Account");
            loadingbar.setMessage("Please wait");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            Acess(phone, password);
        }
    }

    private void Acess(final String phone, final String password) {


        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(parentDbname).child(phone).exists()) {
                    Users userdata = dataSnapshot.child(parentDbname).child(phone).getValue(Users.class);

                    if (userdata.getPhone().equals(phone)) {
                        if (userdata.getPassword().equals(password)) {
                            if (parentDbname.equals("Admins")) {
                                if (checkRememberMe.isChecked()) {
                                    Paper.book().write(Prevalent.UserPhoneKey, phone);
                                    Paper.book().write(Prevalent.UserPasswordKey, password);
                                }

                                Toast.makeText(loginActivity.this, " AdminArticles Logged in succesfull", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                                Intent intent = new Intent(loginActivity.this, category.class);
                                startActivity(intent);

                            } else if (parentDbname.equals("Users")) {
                                if (checkRememberMe.isChecked()) {
                                    Paper.book().write(Prevalent.UserPhoneKey, phone);
                                    Paper.book().write(Prevalent.UserPasswordKey, password);
                                }

                                Toast.makeText(loginActivity.this, "Logged in succesfull", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                                Intent intent = new Intent(loginActivity.this, Home.class);
                                Prevalent.CurrentonlineUser = userdata;
                                startActivity(intent);
                                finish();


                            }
                        } else {
                            loadingbar.dismiss();
                            Toast.makeText(loginActivity.this, "password incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(loginActivity.this, phone + "not exist", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Intent intent = new Intent(loginActivity.this, start.class);
                    startActivity(intent);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
