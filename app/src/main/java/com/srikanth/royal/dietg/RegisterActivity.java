package com.srikanth.royal.dietg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    Button register;
    EditText Iname, Iphone, Ipassword;
    ProgressDialog loadingbar;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register_activity);

        Iname = (EditText) findViewById(R.id.rname);
        Iphone = (EditText) findViewById(R.id.remail);
        Ipassword = (EditText) findViewById(R.id.rpassword);
        register = (Button) findViewById(R.id.btn_register);
        loadingbar = new ProgressDialog(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {

        String name = Iname.getText().toString();
        String phone = Iphone.getText().toString();
        String password = Ipassword.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show();
            Iname.setError("Name is Required");
        }

       else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "phone is required", Toast.LENGTH_SHORT).show();
            Iphone.setError("Phoe is Required");
        }
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            Ipassword.setError("Password is Required");
        }
        else if (isValidPhone(phone)) {
            Toast.makeText(this, "phone is not valid", Toast.LENGTH_SHORT).show();
            Iphone.setError("Invalid Phone Number");
        }



        else {
            loadingbar.setTitle("create account");
            loadingbar.setMessage("Please wait");
            loadingbar.setCanceledOnTouchOutside(false);
              loadingbar.show();

            validate(name,phone,password);
        }
    }
    private boolean isValidPhone(String phone)
    {
        boolean check=true;
        if(!Pattern.matches("[a-zA-Z]+", phone))
        {
            if(phone.length()!=10)
            {
                check = true;

            }
            else
            {
                check = false;

            }
        }
        else
        {
            check=true;
        }
        return check;
    }
    private void validate(final String name, final String phone, final String password) {


        final DatabaseReference Rootref;
        Rootref= FirebaseDatabase.getInstance().getReference();
        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(phone).exists()))
                {
                    HashMap<String,Object> userdatamap=new HashMap<>();
                    userdatamap.put("name",name);
                    userdatamap.put("phone",phone);
                    userdatamap.put("password",password);
                    Rootref.child("Users").child(phone).updateChildren(userdatamap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this, "Succesfully registered", Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();
                                        Intent intent= new Intent(RegisterActivity.this,loginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(RegisterActivity.this, "please retry", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

                else {
                    Toast.makeText(RegisterActivity.this, "This"+phone+"Already exists", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try using another email", Toast.LENGTH_SHORT).show();

                    Intent intent= new Intent(RegisterActivity.this,start.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
