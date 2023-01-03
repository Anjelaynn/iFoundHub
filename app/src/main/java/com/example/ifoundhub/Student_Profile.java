package com.example.ifoundhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Student_Profile extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;

    private TextView student_fullname, student_number, student_contactNumber;
    private ImageButton btnback;

   // private FirebaseDatabase database;
    //private DatabaseReference databaseReference;
   // private static final String LOGIN = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //code for removing action bar and title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide(); //this line hides the action bar
        setContentView(R.layout.activity_student_profile);




        //BottomNavigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Student_Home.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.itemCheck:
                        startActivity(new Intent(getApplicationContext(), Notification.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Student_Settings.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:

                        return true;
                }

                return true;
            }
        });


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("UsersLoginInformation");
        userId = user.getUid();

        final TextView studentfullname = findViewById(R.id.student_fullname);
        final TextView studentnumber = findViewById(R.id.student_number);
        final TextView studentcontactnumber = findViewById(R.id.student_contactNumber);



        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserClass userProfile = snapshot.getValue(UserClass.class);

                if(userProfile != null){
                    String fullname = userProfile.fullname;
                    String studentNumber = userProfile.student_number;
                    String studentcontactNumber = userProfile.contactNum;

                    studentfullname.setText(fullname);
                    studentnumber.setText(studentNumber);
                    studentcontactnumber.setText(studentcontactNumber);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Student_Profile.this, "Something happened!", Toast.LENGTH_LONG).show();
            }
        });





//        student_fullname = findViewById(R.id.student_fullname);
//        student_number = findViewById(R.id.student_number);
//        student_contactNumber = findViewById(R.id.student_contactNumber);
//        btnback = findViewById(R.id.backbuttonprofile);

       // showAllUserData();




//        databaseReference.child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                UserRoleConstructor userProfile = snapshot.getValue(UserRoleConstructor.class);
//                if (userProfile != null) {
//
//
//                    String studentfullname = userProfile.fullname;
//                    String studentnumber = userProfile.student_number;
//                    String studentcontactnumber = userProfile.contactNum;
//
//                    student_fullname.setText(studentfullname);
//                    student_number.setText(studentnumber);
//                    student_contactNumber.setText(studentcontactnumber);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(Student_Profile.this, "Something happened!", Toast.LENGTH_LONG).show();
//
//            }
//        });



//        databaseReference.child(currentUser).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                UserRoleConstructor userProfile = snapshot.getValue(UserRoleConstructor.class);
//                if(userProfile != null){
//
//
//                    String studentfullname = userProfile.fullname;
//                    String studentnumber = userProfile.student_number;
//                    String studentcontactnumber =  userProfile.contactNum;
//
//                    student_fullname.setText(studentfullname);
//                    student_number.setText(studentnumber);
//                    student_contactNumber.setText(studentcontactnumber);
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(Student_Profile.this, "Something happened!", Toast.LENGTH_LONG).show();
//
//            }
//        });



    }



    private void showAllUserData(){

        Intent intent = getIntent();
        String user_studentnum = intent.getStringExtra("student_number");
        String user_fullname = intent.getStringExtra("name");
        String user_contactNum = intent.getStringExtra("contactNum");


        student_fullname.setText(user_fullname);
        student_number.setText(user_studentnum);
        student_contactNumber.setText(user_contactNum);


    }

}