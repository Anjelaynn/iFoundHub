package com.example.ifoundhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Student_Settings extends AppCompatActivity {

    private Button btnlogout;
     private Button btnUpdate;

    Button privacysettingsstudent,aboutussettingsstudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //  getSupportActionBar().hide(); //this line hides the action bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_settings);


        //BottomNavigation


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.settings);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Student_Home.class));
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.itemCheck:
                        startActivity(new Intent(getApplicationContext(), Student_ListOfClaim.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.settings:
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Student_Profile.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return true;
            }
        });


        privacysettingsstudent = findViewById(R.id.privacysettingsstudent);
        privacysettingsstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Student_Privacy.class));
            }
        });

        aboutussettingsstudent = findViewById(R.id.aboutussettingsstudent);
        aboutussettingsstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Student_aboutus.class));
            }
        });


        btnlogout = findViewById(R.id.btnLogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Student_Settings.this, LoginPage.class));
            }
        });

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

//    private void showChangePassword() {
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.changepassword, null);
//        EditText passwordEt = view.findViewById(R.id.passwordEt);
//        EditText cPasswordEt = view.findViewById(R.id.cPasswordEt);
//        Button buttonOkay = view.findViewById(R.id.buttonOkay);
//
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setView(view);
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
//
//        buttonOkay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String oldPassword = passwordEt.getText().toString().trim();
//                String newPassword = cPasswordEt.getText().toString().trim();
//                if (TextUtils.isEmpty(oldPassword)){
//                    Toast.makeText(getApplicationContext(), "Enter your current password...", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (newPassword.length()<6){
//                    Toast.makeText(getApplicationContext(), "Password length must atleast 6 characters...", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                dialog.dismiss();
//                buttonOkay(oldPassword, newPassword);
//            }
//        });
//    }
//
//    private void buttonOkay(String oldPassword, String newPassword) {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
//        user.reauthenticate(authCredential)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        user.updatePassword(newPassword)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Toast.makeText(getApplicationContext(), "Password Updated...", Toast.LENGTH_SHORT).show();
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(getApplicationContext(), ""+e.getMessage(),Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(getApplicationContext(), ""+e.getMessage(),Toast.LENGTH_SHORT).show();
//                    }
//                });
 //   }
}