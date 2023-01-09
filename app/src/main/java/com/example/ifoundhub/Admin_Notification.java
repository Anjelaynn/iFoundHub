package com.example.ifoundhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_Notification extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingBtn;
    FirebaseRecyclerOptions<Items> options;
    FirebaseRecyclerAdapter<Items, NotificationViewholder> adapter;


    DatabaseReference Items_dataRef, Students_dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //code for removing action bar and title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide(); //this line hides the action bar
        setContentView(R.layout.activity_admin_notification);

        //BottomNavigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.notification);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Admin_Home.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.notification:

                        return true;

                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Admin_Settings.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Admin_Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return true;
            }
        });


        recyclerView = findViewById(R.id.recyclerView);
        Items_dataRef = FirebaseDatabase.getInstance().getReference().child("Items");
        Students_dataRef = FirebaseDatabase.getInstance().getReference().child("UsersLoginInformation");











    }
}