package com.example.ifoundhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ifoundhub.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Item_Information extends AppCompatActivity {

    //    TextView spinnerstatus1;
    ImageView imageViewAdd_view;
    TextView inputItemName_view, inputItemLocation_view, inputItemDateReported_view, inputItemDescription_view;

    TextView lastName_view, firstName_view, middleName_view, studentNumber_view, college_view, year_view, course_view, block_view, contactNumber_view;


    Button btnEdit, btnDeletes;
    ImageButton imageButton2;

    DatabaseReference ref, DataRef;
    StorageReference storageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_information);

        imageViewAdd_view = findViewById(R.id.image_single_view_activity);

//        spinnerstatus1 = findViewById(R.id.spinnerstatus1);


        inputItemName_view = findViewById(R.id.itemName_single_view_activity);
        inputItemLocation_view = findViewById(R.id.itemLocation_single_view_activity);
        inputItemDateReported_view = findViewById(R.id.itemDate_single_view_activity);
        inputItemDescription_view = findViewById(R.id.itemDescription_single_view_activity);

        firstName_view = findViewById(R.id.firstName_view);
        lastName_view = findViewById(R.id.lastName_view);
        middleName_view = findViewById(R.id.middleName_view);
        studentNumber_view = findViewById(R.id.studentNumber_view);
        college_view = findViewById(R.id.college_view);
        year_view = findViewById(R.id.year_view);
        course_view = findViewById(R.id.course_view);
        block_view = findViewById(R.id.block_view);
        contactNumber_view= findViewById(R.id.contactNumber_view);


        btnEdit = findViewById(R.id.btnEdit);
        btnDeletes = findViewById(R.id.btnDeletes);


        ref = FirebaseDatabase.getInstance().getReference().child("Items");

        String itemKey = getIntent().getStringExtra("ItemKey");
        DataRef = FirebaseDatabase.getInstance().getReference().child("Items").child(itemKey);
        storageRef = FirebaseStorage.getInstance().getReference().child("ItemImage").child(itemKey+".jpg");


        ref.child(itemKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    String imageUrl = snapshot.child("Image_Url").getValue().toString();
                    String itemName = snapshot.child("Item_Name").getValue().toString();
                    String itemDescription = snapshot.child("Item_Description").getValue().toString();
                    String itemDate = snapshot.child("Date_Reported").getValue().toString();
                    String itemLocation = snapshot.child("Location").getValue().toString();

                    String fname = snapshot.child("First_Name").getValue().toString();
                    String mname = snapshot.child("Middle_Name").getValue().toString();
                    String lname = snapshot.child("Last_Name").getValue().toString();
                    String studentnum = snapshot.child("Student_Number").getValue().toString();
                    String colleges = snapshot.child("College").getValue().toString();
                    String year1 = snapshot.child("Year").getValue().toString();
                    String course1 = snapshot.child("Course").getValue().toString();
                    String block1 = snapshot.child("Block").getValue().toString();
                    String contactnum = snapshot.child("Contact_Number").getValue().toString();



                    Picasso.get().load(imageUrl).into(imageViewAdd_view);
                    inputItemName_view.setText(itemName);
                    inputItemDescription_view.setText(itemDescription);
                    inputItemDateReported_view.setText(itemDate);
                    inputItemLocation_view.setText(itemLocation);

//                    firstName_view.setText(fname);
//                    lastName_view.setText(lname);
//                    middleName_view.setText(mname);
//                    studentNumber_view.setText(studentnum);
//                    college_view.setText(colleges);
//
//                    year_view.setText(year1);
//                    course_view.setText(course1);
//                    block_view.setText(block1);
//                    contactNumber_view.setText(contactnum);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}