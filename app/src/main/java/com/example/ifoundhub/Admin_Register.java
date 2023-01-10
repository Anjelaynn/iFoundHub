package com.example.ifoundhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class Admin_Register extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText editTextfullName, editTextcontactNumber,
          editTextcourse, editTextyear,
            editTextstudentNumber, editTextemail, editTextpassword;

    //REference to firebase
    ProgressBar progressBar;
    DatabaseReference dataRef;
    StorageReference storageRef;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    private Button btnRegUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //code for removing action bar and title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide(); //this line hides the action bar
        setContentView(R.layout.activity_admin_register);


        editTextfullName = findViewById(R.id.editTextfullName);
        editTextcontactNumber = findViewById(R.id.editTextcontactNumber);
        editTextcourse = findViewById(R.id.editTextcourse);
        editTextyear = findViewById(R.id.editTextyear);
        editTextstudentNumber = findViewById(R.id.editTextstudentNumber);
        editTextemail = findViewById(R.id.editTextemail);
        editTextpassword = findViewById(R.id.editTextpassWord);

        progressBar = findViewById(R.id.progressBar);
        btnRegUser = findViewById(R.id.btnRegUser);



        mAuth = FirebaseAuth.getInstance();


     //   dataRef = FirebaseDatabase.getInstance().getReference().child("Users");

        btnRegUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


    }


    private void registerUser() {


        String fullname = editTextfullName.getText().toString().trim();
        String course = editTextcourse.getText().toString().trim();
        String year = editTextyear.getText().toString().trim();
        String studentnumber = editTextstudentNumber.getText().toString().trim();
        String email = editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        String contactnumber = editTextcontactNumber.getText().toString().trim();;


        if(fullname.isEmpty()){
            editTextfullName.setError("This field is required!");
            editTextfullName.requestFocus();
            return;
        }


        if(course.isEmpty()){
            editTextcourse.setError("This field is required!");
            editTextcourse.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextpassword.setError("This field is required!");
            editTextpassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextemail.setError("Please provide valid email");
            editTextemail.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextpassword.setError("Min password length should be 6 characters!");
            editTextpassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    UserClass user = new UserClass(fullname, contactnumber, email, studentnumber, year, course, password);

                    FirebaseDatabase.getInstance().getReference("UsersLoginInformation")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()) {
                                                    Toast.makeText(Admin_Register.this, "Please verify your email address.", Toast.LENGTH_SHORT).show();
                                                    progressBar.setVisibility(View.GONE);
                                                    startActivity(new Intent(getApplicationContext(), LoginPage.class));
                                                }else{
                                                    Toast.makeText(Admin_Register.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });



                                    }else{
                                        Toast.makeText(Admin_Register.this, "Failed To register!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(Admin_Register.this, "Failed To register!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }









//    private void registerUser() {
//        final String key = dataRef.push().getKey();
//
//        String userRole = editTextUserRole.getText().toString().trim();
//        String fullname = editTextfullName.getText().toString().trim();
//        String age = editTextage.getText().toString().trim();
//        String course = editTextcourse.getText().toString().trim();
//        String year = editTextyear.getText().toString().trim();
//        String studentnumber = editTextstudentNumber.getText().toString().trim();
//        String email = editTextemail.getText().toString().trim();
//        String password = editTextpassword.getText().toString().trim();
//        String contactnumber = editTextcontactNumber.getText().toString().trim();
//
//
//        UserRoleConstructor user = new UserRoleConstructor(fullname, contactnumber, age, email, studentnumber, year, course, password, userRole);
//
//
//        dataRef.child(key).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//
//                startActivity(new Intent(getApplicationContext(), LoginPage.class));
//                Toast.makeText(Admin_Register.this, "Data Successfully Uploaded!", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });



    }

