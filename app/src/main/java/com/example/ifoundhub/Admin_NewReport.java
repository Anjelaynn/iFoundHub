package com.example.ifoundhub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class Admin_NewReport extends AppCompatActivity implements AdapterView.OnItemSelectedListener{



    private static final int REQUEST_CODE_IMAGE = 101;

    Spinner spinnerstatus1;
    ImageView imageViewAdd;
    EditText inputItemName, inputItemLocation, inputItemDateReported, inputItemDescription;

    EditText lastName, firstName, middleName, studentNumber, college, year, course, block, contactNumber;

    TextView textViewProgress;
    ProgressBar progressBar;
    Button btnUpload;
    ImageButton imageButton2;


    //REference to firebase
    DatabaseReference dataRef;
    StorageReference storageRef;


    Uri imageUri;
    boolean isImageAddded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide(); //this line hides the action bar
        setContentView(R.layout.activity_new_report);


        spinnerstatus1 = findViewById(R.id.spinnerstatus1_view);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerstatus1.setAdapter(adapter);
        spinnerstatus1.setOnItemSelectedListener(this);




        imageViewAdd = findViewById(R.id.image_single_view_activity);

        inputItemName = findViewById(R.id.itemName_single_view_activity);
        inputItemLocation = findViewById(R.id.itemLocation_single_view_activity);
        inputItemDateReported = findViewById(R.id.itemDate_single_view_activity);
        inputItemDescription = findViewById(R.id.itemDescription_single_view_activity);

        firstName = findViewById(R.id.firstName_view);
        lastName = findViewById(R.id.lastName_view);
        middleName = findViewById(R.id.middleName_view);
        studentNumber = findViewById(R.id.studentNumber_view);
        college = findViewById(R.id.college_view);
        year = findViewById(R.id.year_view);
        course = findViewById(R.id.course_view);
        block = findViewById(R.id.block_view);
        contactNumber = findViewById(R.id.contactNumber_view);


        textViewProgress = findViewById(R.id.textViewProgress);
        progressBar = findViewById(R.id.progressBar);
        btnUpload = findViewById(R.id.btnUpload);
        imageButton2 = findViewById(R.id.imageButton2);

        textViewProgress.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);


        dataRef = FirebaseDatabase.getInstance().getReference().child("Items");
        storageRef = FirebaseStorage.getInstance().getReference().child("ItemImage");

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin_Home.class));
            }
        });


        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(intent, REQUEST_CODE_IMAGE);

            }
        });


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imageItem  = inputItemName.getText().toString();
                if(isImageAddded!=false && imageItem!=null){
                    uploadImage(imageItem);
                }

            }
        });


    }


    //Upload image
    private void uploadImage(String imageItem) {
        textViewProgress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        final String key = dataRef.push().getKey();
        //image upload to storage reference
        storageRef.child(key+ ".jpg").putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                storageRef.child(key+ ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        //Addded image on storage
                        HashMap hashMap = new HashMap();
                        hashMap.put("Item_Name", imageItem);
                        hashMap.put("Image_Url", uri.toString());
                        hashMap.put("Item_Description", inputItemDescription.getText().toString());
                        hashMap.put("Date_Reported", inputItemDateReported.getText().toString());
                        hashMap.put("Location", inputItemLocation.getText().toString());
                        hashMap.put("First_Name", firstName.getText().toString());
                        hashMap.put("Last_Name", lastName.getText().toString());
                        hashMap.put("Middle_Name", middleName.getText().toString());
                        hashMap.put("Student_Number", studentNumber.getText().toString());
                        hashMap.put("College", college.getText().toString());
                        hashMap.put("Year", year.getText().toString());
                        hashMap.put("Course", course.getText().toString());
                        hashMap.put("Block", block.getText().toString());
                        hashMap.put("Contact_Number", contactNumber.getText().toString());
                        hashMap.put("Status", spinnerstatus1.getSelectedItem().toString());



                        dataRef.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(getApplicationContext(), Admin_Home.class));
                                Toast.makeText(Admin_NewReport.this, "Data Successfully Uploaded!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });


            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                //Progress of uploading image
                double progress = (snapshot.getBytesTransferred()*100)/snapshot.getTotalByteCount();
                progressBar.setProgress((int) progress);
                textViewProgress.setText(progress+" %");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_IMAGE && data != null){
            imageUri = data.getData();
            isImageAddded = true;
            imageViewAdd.setImageURI(imageUri);
        }
    }


    //Spinner status
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String text = adapterView.getItemAtPosition(i).toString();


    }



    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}