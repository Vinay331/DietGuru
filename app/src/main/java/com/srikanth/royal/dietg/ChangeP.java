package com.srikanth.royal.dietg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.srikanth.royal.dietg.Prevalent.Prevalent;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChangeP extends AppCompatActivity {
    private Button  update;
    private EditText settingPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp);
        update = (Button) findViewById(R.id.u_btn);
        settingPassword = (EditText) findViewById(R.id.Npassword);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    updateOnlyuserInfo();

            }
        });

    }
    private void updateOnlyuserInfo() {


        if (TextUtils.isEmpty(settingPassword.getText().toString())) {
            Toast.makeText(this, "Password is Required", Toast.LENGTH_SHORT).show();
        }
        else {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
            HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("password", settingPassword.getText().toString());
            ref.child(Prevalent.CurrentonlineUser.getPhone()).updateChildren(userMap);

            startActivity(new Intent(ChangeP.this, start.class));
            Toast.makeText(ChangeP.this, "Update Successful", Toast.LENGTH_SHORT).show();
            finish();

        }
    }

}



