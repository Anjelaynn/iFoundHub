package com.example.ifoundhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginPage extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText editTextusername, editTextpassword;
//    boolean passwordVisible;

    Switch active;

  //  FirebaseDatabase database;

    private Button btnSignUp, btnSignIn;

    private ProgressBar progressBar;





    //Remember Me
    public static final String SHARED_PRES = "sharedPrefs";
    String username, password1;
    private CheckBox CheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //code for removing action bar and title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide(); //this line hides the action bar
        setContentView(R.layout.activity_login_page);

        editTextusername = findViewById(R.id.editTextusername);
        editTextpassword = findViewById(R.id.editTextpassword);
        CheckBox = findViewById(R.id.checkBox_remember_me);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin) {
            editTextusername.setText(loginPreferences.getString("username", ""));
            editTextpassword.setText(loginPreferences.getString("password", ""));
            CheckBox.setChecked(true);
        }

//        editTextpassword.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                final int Right = 2;
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (event.getRawX() >= editTextpassword.getRight() - editTextpassword.getCompoundDrawables()[Right].getBounds().width()) {
//                        int selection = editTextpassword.getSelectionEnd();
//                        if (passwordVisible) {
//                            editTextpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off_24);
//                            editTextpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                            passwordVisible = false;
//                        } else {
//                            editTextpassword.setCompoundDrawablesRelativeWithIntrinsicBounsd(0, 0, R.drawable.ic_visibility_24);
//                            editTextpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                            passwordVisible = true;
//                        }
//                        editTextpassword.setSelection(selection);
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        active = findViewById(R.id.active);

        progressBar = findViewById(R.id.progressBar);


        mAuth = FirebaseAuth.getInstance();

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginPage.this, Admin_Register.class);
                startActivity(intent);
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });



        CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (CheckBox.isChecked()) {

                    Toast.makeText(LoginPage.this, "Checked", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(LoginPage.this, "UnChecked", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Remember ME
        //checkBox();



        //Try 2 failed!
//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                       final String input1 = username.getText().toString().trim();
//                        String input2 = password.getText().toString().trim();
//
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
//                Query checkUser  = databaseReference.orderByChild("student_number").equalTo(input1);
//
//                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        if(snapshot.exists()){
//
//                            username.setError(null);
//                            //username.setErrorEnabled(false);
//
//
//
//                            String passwordFromDb = snapshot.child(input1).child("password").getValue(String.class);
//
//                            if(passwordFromDb.equals(input2)){
//
//                                String studentNumberFromdb = snapshot.child(input1).child("stundent_number").getValue(String.class);
//                                String fullnameFromdb = snapshot.child(input1).child("fullname").getValue(String.class);
//                                String contactNumFromdb = snapshot.child(input1).child("contactNum").getValue(String.class);
//
//                                Intent intent = new Intent(getApplicationContext(), Student_Profile.class);
//
//                                intent.putExtra("name", fullnameFromdb);
//                                intent.putExtra("student_number", studentNumberFromdb);
//                                intent.putExtra("contactNum", contactNumFromdb);
//
//                                startActivity(intent);
//
//                            }else{
//                                password.setError("Wrong Password");
//                                password.requestFocus();
//                            }
//
//                        }else {
//                            username.setError("No such UserClass Exist");
//                            username.requestFocus();
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//            }
//        });
//


//1st Try failed!
//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
//
//                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                        String input1 = username.getText().toString();
//                        String input2 = password.getText().toString();
//
//                        if (dataSnapshot.child(input1).exists()) {
//                            if (dataSnapshot.child(input1).child("password").getValue(String.class).equals(input2)) {
//                                if (active.isChecked()) {
//                                    if (dataSnapshot.child(input1).child("userRole").getValue(String.class).equals("admin")) {
//                                        Preferences_login.setDataLogin(LoginPage.this, true);
//                                        Preferences_login.setDataAs(LoginPage.this, "admin");
//                                        startActivity(new Intent(LoginPage.this, Admin_Home.class));
//
//                                    } else if (dataSnapshot.child(input1).child("userRole").getValue(String.class).equals("user")){
//                                        Preferences_login.setDataLogin(LoginPage.this, true);
//                                        Preferences_login.setDataAs(LoginPage.this, "user");
//
//                                        Intent intent = new Intent(LoginPage.this, Student_Profile.class);
//                                        intent.putExtra("Currentuser", input1);
//                                        startActivity(intent);
////                                        startActivity(new Intent(LoginPage.this, Student_Home.class));
//                                    }
//                                } else {
//                                    if (dataSnapshot.child(input1).child("userRole").getValue(String.class).equals("admin")) {
//                                        Preferences_login.setDataLogin(LoginPage.this, false);
//                                        startActivity(new Intent(LoginPage.this, Admin_Home.class));
//
//                                    } else if (dataSnapshot.child(input1).child("userRole").getValue(String.class).equals("user")){
//                                        Preferences_login.setDataLogin(LoginPage.this, false);
//
//                                        Intent intent = new Intent(LoginPage.this,  Student_Profile.class);
//                                        intent.putExtra("Currentuser", input1);
//                                        startActivity(intent);
////                                        startActivity(new Intent(LoginPage.this, Student_Home.class));
//                                    }
//                                }
//
//                            } else {
//                                Toast.makeText(LoginPage.this, "incorrect password", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Toast.makeText(LoginPage.this, "no registered", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        });
    }



//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (Preferences_login.getDataLogin(this)) {
//            if (Preferences_login.getDataAs(this).equals("admin")) {
//                startActivity(new Intent(this, Admin_Home.class));
//                finish();
//            } else {
//                startActivity(new Intent(this, Student_Profile.class));
//                finish();
//            }
//        }
//    }



    private void checkBox() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PRES, MODE_PRIVATE);
        String check = sharedPreferences.getString("name", "");

        if(check.equals("true")){
            Toast.makeText(LoginPage.this, "This is admin site", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginPage.this, Admin_Home.class));
            finish();
        }

    }

    public void openHome(){
        Intent intent=new Intent(this, Admin_Home.class);
        startActivity(intent);
    }





    private void userLogin(){
        String email = editTextusername.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextusername.setError("This field is required!");
            editTextusername.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextpassword.setError("This field is required!");
            editTextpassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextpassword.setError("Min password length should be 6 characters!");
            editTextpassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(editTextusername.getWindowToken(), 0);

                    username = editTextusername.getText().toString();
                    password1 = editTextpassword.getText().toString();

                    if (CheckBox.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", username);
                        loginPrefsEditor.putString("password", password);
                        loginPrefsEditor.commit();

                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }


                    //Remember Me
//                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PRES, MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                    editor.putString("name", "true");
//                    editor.apply();





//                    if(mAuth.getCurrentUser().isEmailVerified()){
//                        Toast.makeText(LoginPage.this, "This is user side", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(LoginPage.this, Student_Home.class));
//                    }else{
//                        Toast.makeText(LoginPage.this, "Please verify your email address.", Toast.LENGTH_SHORT).show();
//                    }
                        //redirect to user profile
                    if(email.equals("admin@plm.edu.ph") && password.equals("useradmin123")) {
                        Toast.makeText(LoginPage.this, "This is admin site", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(LoginPage.this, Admin_Home.class));
                    }
                    else{
                        Toast.makeText(LoginPage.this, "This is user side", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginPage.this, Student_Home.class));
                    }

                }else{
                    Toast.makeText(LoginPage.this, "Invalid Information!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });



    }




}