package com.example.ifoundhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Admin_EditDeleteItem extends AppCompatActivity implements AdapterView.OnItemSelectedListener{




    DatabaseReference ref, DataRef;
    StorageReference storageRef;

    Spinner spinnerstatus1;
    ImageView image_single_view_activity;
    TextView itemName_single_view_activity, itemDescription_single_view_activity,itemDate_single_view_activity,itemLocation_single_view_activity;
    TextView lname, fname, mname, studentnum, college, year, course, block, contactnum;

    TextView status;


    ImageButton btnBack;
    //    TextView itemName_single_view_activity, itemDescription_single_view_activity,itemDate_single_view_activity,itemLocation_single_view_activity;
    Button btnDelete, btnUpdate, btneditChanges;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_delete);


        spinnerstatus1 = findViewById(R.id.spinnerstatus1_view);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerstatus1.setAdapter(adapter);
        spinnerstatus1.setOnItemSelectedListener(this);



        image_single_view_activity = findViewById(R.id.image_single_view_activity);

        itemName_single_view_activity = findViewById(R.id.itemName_single_view_activity);
        itemDescription_single_view_activity = findViewById(R.id.itemDescription_single_view_activity);
        itemDate_single_view_activity = findViewById(R.id.itemDate_single_view_activity);
        itemLocation_single_view_activity = findViewById(R.id.itemLocation_single_view_activity);

        fname = findViewById(R.id.firstName_view);
        lname = findViewById(R.id.lastName_view);
        mname = findViewById(R.id.middleName_view);
        studentnum = findViewById(R.id.studentNumber_view);
        college = findViewById(R.id.college_view);
        year = findViewById(R.id.year_view);
        course = findViewById(R.id.course_view);
        block = findViewById(R.id.block_view);
        contactnum = findViewById(R.id.contactNumber_view);


        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin_Home.class));
            }
        });




        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
//        btneditChanges = findViewById(R.id.btnEdit);


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

                    String Fname = snapshot.child("First_Name").getValue().toString();
                    String Mname = snapshot.child("Middle_Name").getValue().toString();
                    String Lname = snapshot.child("Last_Name").getValue().toString();
                    String Studentnum = snapshot.child("Student_Number").getValue().toString();
                    String College = snapshot.child("College").getValue().toString();
                    String Year = snapshot.child("Year").getValue().toString();
                    String Course = snapshot.child("Course").getValue().toString();
                    String Block = snapshot.child("Block").getValue().toString();
                    String Contactnum = snapshot.child("Contact_Number").getValue().toString();
                    String status =  snapshot.child("Status").toString();




                    Picasso.get().load(imageUrl).into(image_single_view_activity);

                    itemName_single_view_activity.setText(itemName);
                    itemDescription_single_view_activity .setText(itemDescription);
                    itemDate_single_view_activity.setText(itemDate);
                    itemLocation_single_view_activity.setText(itemLocation);

                    fname.setText(Fname);
                    lname.setText(Lname);
                    mname.setText(Mname);

                    college.setText(College);
                    year.setText(Year);
                    course.setText(Course);
                    block.setText(Block);
                    contactnum.setText(Contactnum);





                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                HashMap hashMap = new HashMap();
                hashMap.put("Item_Name", itemName_single_view_activity.getText().toString());
                hashMap.put("Item_Desciprion", itemDescription_single_view_activity.getText().toString());
                hashMap.put("Location", itemLocation_single_view_activity.getText().toString());
                hashMap.put("Date_Reported", itemDate_single_view_activity.getText().toString());

                hashMap.put("First_Name", fname.getText().toString());
                hashMap.put("Last_Name", lname.getText().toString());
                hashMap.put("Middle_Name", mname.getText().toString());
                hashMap.put("Student_Number", studentnum.getText().toString());
                hashMap.put("Year", year.getText().toString());
                hashMap.put("Course", course.getText().toString());
                hashMap.put("College", college.getText().toString());
                hashMap.put("Block", block.getText().toString());
                hashMap.put("Contact_Number", contactnum.getText().toString());
                hashMap.put("Status", spinnerstatus1.getSelectedItem().toString());


                DataRef.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(Admin_EditDeleteItem.this, "Your Data is Succesfully Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Admin_Home.class));

                    }
                });
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Admin_EditDeleteItem.this, "Your Data is Succesfully Deleted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Admin_Home.class));
                            }
                        });
                    }
                });

            }
        });



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