package com.example.ifoundhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ViewActivity extends AppCompatActivity {


    FirebaseRecyclerOptions<Items> options;
    FirebaseRecyclerAdapter<Items, MyViewHolder> adapter;
    RecyclerView recyclerView;

    DatabaseReference ref, DataRef;
    StorageReference storageRef;


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
        setContentView(R.layout.activity_viewinfoadmin);

        image_single_view_activity = findViewById(R.id.image_single_view_activity);

        itemName_single_view_activity = findViewById(R.id.itemName_single_view_activity);
        itemDescription_single_view_activity = findViewById(R.id.itemDescription_single_view_activity);
        itemDate_single_view_activity = findViewById(R.id.itemDate_single_view_activity);
        itemLocation_single_view_activity = findViewById(R.id.itemLocation_single_view_activity);

        fname = findViewById(R.id.firstName_view_information);
        lname = findViewById(R.id.lastName_view_information);
        mname = findViewById(R.id.middleName_view_information);
        studentnum = findViewById(R.id.studentNumber_view_information);
        college = findViewById(R.id.college_view_information);
        year = findViewById(R.id.year_view_information);
        course = findViewById(R.id.course_view_information);
        block = findViewById(R.id.block_view_information);
        contactnum = findViewById(R.id.contactNumber_view_information);


        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin_Home.class));
            }
        });




        btnDelete = findViewById(R.id.btnDelete);
        btneditChanges = findViewById(R.id.btnEditChanges);
//        btnUpdate = findViewById(R.id.btnUpdate);

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




                    Picasso.get().load(imageUrl).into(image_single_view_activity);

                    itemName_single_view_activity.setText(itemName);
                    itemDescription_single_view_activity .setText(itemDescription);
                    itemDate_single_view_activity.setText(itemDate);
                    itemLocation_single_view_activity.setText(itemLocation);

                    fname.setText(Fname);
                    lname.setText(Lname);
                    mname.setText(Mname);
                    studentnum.setText(Studentnum);
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


//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                HashMap hashMap = new HashMap();
//                hashMap.put("Item_Name", itemName_single_view_activity.getText().toString());
//                hashMap.put("Item_Desciprion", itemDescription_single_view_activity.getText().toString());
//                hashMap.put("Location", itemLocation_single_view_activity.getText().toString());
//                hashMap.put("Date_Reported", itemDate_single_view_activity.getText().toString());
//
//                DataRef.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
//                    @Override
//                    public void onSuccess(Object o) {
//                        Toast.makeText(ViewActivity.this, "Your Data is Succesfully Updated", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//
//                    }
//                });
//            }
//        });
//
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ViewActivity.this, "Your Data is Succesfully Deleted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Admin_Home.class));
                            }
                        });
                    }
                });

            }
        });





    }

    private void loadData(){


        options = new FirebaseRecyclerOptions.Builder<Items>().setQuery(ref, Items.class).build();
        adapter = new FirebaseRecyclerAdapter<Items, MyViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Items model) {


                //To edit or delete, open view of the item
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ViewActivity.this, Admin_EditChanges.class);
                        intent.putExtra("ItemKey", getRef(position).getKey());
                        startActivity(intent);
                    }
                });

            }
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);

                return new MyViewHolder(v);
            }
        };

        adapter.startListening();
//        recyclerView.setAdapter(adapter);

    }


}