package com.example.ifoundhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class ItemInformation extends AppCompatActivity {

    Button button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //code for removing action bar and title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //this line hides the action bar

        setContentView(R.layout.activity_item_information);
        button4 = (Button) findViewById(R.id.button4);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call function for next page temporary
                openStudentFoundItem();
            }
        });
    }
    public void openStudentFoundItem(){
        Intent intent = new Intent(this, StudentFoundItem.class);
        startActivity(intent);
    }
}
