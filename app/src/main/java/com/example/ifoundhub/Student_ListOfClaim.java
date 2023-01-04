package com.example.ifoundhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Student_ListOfClaim extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //  getSupportActionBar().hide(); //this line hides the action bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list_of_claim);


        //BottomNavigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.itemCheck);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Student_Home.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.itemCheck:

                        return true;

                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Student_Settings.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Student_Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return true;
            }
        });





    }
}