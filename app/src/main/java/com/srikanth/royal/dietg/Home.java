package com.srikanth.royal.dietg;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.srikanth.royal.dietg.Prevalent.Prevalent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView userNameText =(TextView)navigationView.getHeaderView(0).findViewById(R.id.user_name);
        CircleImageView userProfileImage =(CircleImageView)navigationView.getHeaderView(0).findViewById(R.id.user_profile_image);
        userNameText.setText(Prevalent.CurrentonlineUser.getName());
        Picasso.get().load(Prevalent.CurrentonlineUser.getImage())
                .placeholder(R.drawable.ic_launcher_background)
                .into(userProfileImage);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null)
        {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Articles()).commit();
            navigationView.setCheckedItem(R.id.home);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Articles()).commit();
                break;

            case R.id.recipies:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Recipies()).commit();
                break;

            case R.id.logout:
                Paper.book().destroy();
                Intent intent= new Intent(Home.this,start.class);
                startActivity(intent);
                finish();
                break;

            case R.id.settings:

                Intent intent1= new Intent(Home.this,MyProfile.class);
                startActivity(intent1);
                break;




        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }



        else {

            super.onBackPressed();

        }



    }


}
