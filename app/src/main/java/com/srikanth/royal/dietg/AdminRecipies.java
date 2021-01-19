package com.srikanth.royal.dietg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AdminRecipies extends AppCompatActivity {
    private String categoryname,t,sd,d,savecurrentdate,savecurrenttime,randomkey,downloadimageurl;
    ProgressDialog loadingbar;
    Button Add;
    EditText title, shortD, description;
    ImageView image;
    private static  final int gallerypick=1;
    Uri ImageUri;


    StorageReference Sref;
    DatabaseReference Iref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_recipies);
Sref= FirebaseStorage.getInstance().getReference().child("RImage");
        Iref= FirebaseDatabase.getInstance().getReference().child("Recipies");
        categoryname = getIntent().getExtras().get("activity_category").toString();



        Add = (Button) findViewById(R.id.btn_add);
        title = (EditText) findViewById(R.id.title);
        shortD = (EditText) findViewById(R.id.short_description);
        description = (EditText) findViewById(R.id.description);
        image = (ImageView) findViewById(R.id.selected_image);
        loadingbar=new ProgressDialog(this);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Opengallery();
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate();

            }
        });

    }



    private void Opengallery() {

        Intent gallery=new Intent();
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery,gallerypick);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==gallerypick && resultCode==RESULT_OK && data!=null )
        {
            ImageUri=data.getData();
            image.setImageURI(ImageUri);
        }
    }


    private void validate() {

        t=title.getText().toString();
        sd=shortD.getText().toString();
        d=description.getText().toString();

        if (ImageUri==null)
        {
            Toast.makeText(this, "Image is required", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(t)){
            Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show();
        }


        else if(TextUtils.isEmpty(sd)){
            Toast.makeText(this, "Short description is required", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(d)){
            Toast.makeText(this, "description is required", Toast.LENGTH_SHORT).show();
        }
        else {
            Storage();
        }


    }

    private void Storage() {

        loadingbar.setTitle("Adding New Item");
        loadingbar.setMessage("Please wait");
        loadingbar.setCanceledOnTouchOutside(false);
        loadingbar.show();

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
        savecurrentdate=currentDate.format(calendar.getTime());


        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime=currentTime.format(calendar.getTime());

        randomkey=savecurrentdate+savecurrenttime;
        final StorageReference filepath=Sref.child(ImageUri.getLastPathSegment()+randomkey+".jpg");
        final UploadTask uploadTask=filepath.putFile(ImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminRecipies.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                loadingbar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminRecipies.this, "image upload succesfull", Toast.LENGTH_SHORT).show();


                Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                       if (!task.isSuccessful()){
                           throw task.getException();

                       }
                       downloadimageurl=filepath.getDownloadUrl().toString();
                       return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful())
                        {
                            downloadimageurl=task.getResult().toString();
                            Toast.makeText(AdminRecipies.this, "image url is succesfully got", Toast.LENGTH_SHORT).show();

                            storeInfo();
                        }

                    }
                });
            }
        });


    }

    private void storeInfo() {

        HashMap<String ,Object> itemmap=new HashMap<>();
        itemmap.put("iid",randomkey);
        itemmap.put("date",savecurrentdate);
        itemmap.put("time",savecurrenttime);
        itemmap.put("title",t);
        itemmap.put("shortdescription",sd);
        itemmap.put("description",d);
        itemmap.put("image",downloadimageurl);
        itemmap.put("activity_category",categoryname);

        Iref.child(randomkey).updateChildren(itemmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {loadingbar.dismiss();
                            Toast.makeText(AdminRecipies.this, "item is added succesfull", Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(AdminRecipies.this,category.class);
                            startActivity(intent);
                        }
                        else {
                            loadingbar.dismiss();
                            Toast.makeText(AdminRecipies.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
