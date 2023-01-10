package com.example.ifoundhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Admin_Notification extends AppCompatActivity {


    //Dialog Variables
    AlertDialog.Builder builderDialog;
    AlertDialog alertDialog;

    //user information
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;

    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Items> options;

    FirebaseRecyclerAdapter<Items, NotificationViewholder> adapter;
    FirebaseRecyclerAdapter<UserClass, NotificationViewholder> adapter1;
    FirebaseRecyclerOptions<UserClass> options1;


    DatabaseReference Items_dataRef, notification;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //code for removing action bar and title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide(); //this line hides the action bar
        setContentView(R.layout.activity_admin_notification);

        //BottomNavigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.notification);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Admin_Home.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.notification:

                        return true;

                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Admin_Settings.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Admin_Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return true;
            }
        });


        recyclerView = findViewById(R.id.recyclerView);
        notification = FirebaseDatabase.getInstance().getReference().child("Notification");


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);






        loadData("");









    }

    private void loadData(String data){




      //  Query query = dataRef.orderByChild("Item_Name").startAt(data).endAt(data+"\uf8ff");


    //   options1 = new FirebaseRecyclerOptions.Builder<UserClass>().setQuery(Students_dataRef, UserClass.class).build();
        options = new FirebaseRecyclerOptions.Builder<Items>().setQuery(notification, Items.class).build();
        //options = new FirebaseRecyclerOptions.Builder<Items>().setQuery(query, Items.class).build();

        adapter = new FirebaseRecyclerAdapter<Items,NotificationViewholder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull NotificationViewholder holder, int position, @NonNull Items model) {


                String key =  getRef(position).getKey();
                notification.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()){
                            String studentName = snapshot.child("Student_Name").getValue().toString();
                            holder.single_view_notification_studentName.setText(studentName);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                Picasso.get().load(model.getImage_Url()).into(holder.image_single_view_notification);
                holder.single_view_notification_DateReported.setText(model.getItem_Name());


                //To edit or delete, open view of the item
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String key2 =  getRef(position).getKey();

                        builderDialog = new AlertDialog.Builder(Admin_Notification.this);
                        View layoutview = getLayoutInflater().inflate(R.layout.custom_itemviewnotification,null);

                        notification.child(key2).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if(snapshot.exists()){
                                    TextView itemName = layoutview.findViewById(R.id.txt_itemname);
                                    TextView itemNumber = layoutview.findViewById(R.id.txt_itemnumber);
                                    TextView itemStatus = layoutview.findViewById(R.id.txt_itemstatus);
                                    TextView itemLocation = layoutview.findViewById(R.id.editTextItemLocation);
                                    TextView itemDateReported= layoutview.findViewById(R.id.editTextDateReported);
                                    TextView itemDescription = layoutview.findViewById(R.id.editTextDescription);
                                    ImageView imageUrl1 = layoutview.findViewById(R.id.image_single_view_notification);


                                    String imageUrl = snapshot.child("Image_Url").getValue().toString();
                                    String itemName1 = snapshot.child("Item_Name").getValue().toString();
                                    String itemDescription1 = snapshot.child("Item_Description").getValue().toString();
                                    String itemDate = snapshot.child("Date_Reported").getValue().toString();
                                    String itemLocation1 = snapshot.child("Location").getValue().toString();
                                    String itemStatus1 = snapshot.child("Status").getValue().toString();


                                    Picasso.get().load(imageUrl).into(imageUrl1);
                                    itemName.setText(itemName1);
                                    itemStatus.setText(itemStatus1);
                                    itemLocation.setText(itemLocation1);
                                    itemDateReported.setText(itemDate);
                                    itemDescription.setText(itemDescription1);


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });




//                        AppCompatButton itemName = layoutview.findViewById(R.id.txt_itemname);
//                        AppCompatButton itemNumber = layoutview.findViewById(R.id.txt_itemnumber);
//                        AppCompatButton itemStatus = layoutview.findViewById(R.id.txt_itemstatus);
//                        AppCompatButton itemLocation = layoutview.findViewById(R.id.editTextItemLocation);
//                        AppCompatButton itemDateReported = layoutview.findViewById(R.id.editTextDateReported);
                        AppCompatButton buttonClaimed = layoutview.findViewById(R.id.buttonClaimed);
                        AppCompatButton buttonClose = layoutview.findViewById(R.id.buttonClose);


                        builderDialog.setView(layoutview);
                        alertDialog=builderDialog.create();
                        alertDialog.show();


                        buttonClaimed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                DatabaseReference listOfClaims;
                                listOfClaims = FirebaseDatabase.getInstance().getReference().child("ListOfClaims");

                                notification.child(key2).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        if(snapshot.exists()) {
                                            String studentID = snapshot.child("Student_ID").getValue().toString();
                                            String itemName2 = snapshot.child("Item_Name").getValue().toString();
                                            String itemDateReported2 = snapshot.child("Date_Reported").getValue().toString();
                                            String URl1 = snapshot.child("Image_Url").getValue().toString();
                                            String student_name = snapshot.child("Student_Name").getValue().toString();

                                            HashMap hashMap = new HashMap();
                                            hashMap.put("Item_Name", itemName2.toString());
                                            hashMap.put("Date_Reported", itemDateReported2.toString());
                                            hashMap.put("Image_Url", URl1.toString());
                                            hashMap.put("Student_ID", studentID);

                                            listOfClaims.child(studentID).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {



                                                    //startActivity(new Intent(getApplicationContext(), Admin_Home.class));
                                                    Toast.makeText(Admin_Notification.this, "Data Successfully Uploaded!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
//
//                                            listOfClaims.child(studentID).child("ListOfClaims").setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void unused) {
//
//
//
//                                                    //startActivity(new Intent(getApplicationContext(), Admin_Home.class));
//                                                    Toast.makeText(Admin_Notification.this, "Data Successfully Uploaded!", Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
//
                                        }



                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        });




                        buttonClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });


//                        Intent intent = new Intent(Admin_Notification.this, .class);
//                        intent.putExtra("ItemKey", getRef(position).getKey());
//                        startActivity(intent);
                    }
                });

            }
            @NonNull
            @Override
            public NotificationViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_notification,parent,false);

                return new NotificationViewholder(v);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}