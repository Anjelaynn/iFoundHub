package com.example.ifoundhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class AboutUsStudents extends AppCompatActivity {
    ImageButton btnBackSetting2Stud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //code for removing action bar and title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide(); //this line hides the action bar
        setContentView(R.layout.activity_student_aboutus);

        btnBackSetting2Stud = findViewById(R.id.btnBackSetting2Stud);
        btnBackSetting2Stud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Student_Settings.class));
            }
        });
    }
}