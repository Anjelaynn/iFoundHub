package com.example.ifoundhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Student_ListOfClaim extends AppCompatActivity {

    //Dialog Variables
    AlertDialog.Builder builderDialog;
    AlertDialog alertDialog;

    //user information
    FirebaseUser user;
    DatabaseReference reference;
     String userId;

    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Items> options;
    FirebaseRecyclerOptions<FirebaseUser> options1;

    FirebaseRecyclerAdapter<Items, ListOfItemViewHolder> adapter;
   // FirebaseRecyclerAdapter<UserClass,  ListOfItemViewHolder> adapter1;
    //FirebaseRecyclerOptions<UserClass> options1;

    DatabaseReference Items_dataRef, listOfClaims;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //  getSupportActionBar().hide(); //this line hides the action bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list_of_claim);


        //BottomNavigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.itemCheck);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Student_Home.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.itemCheck:

                        return true;

                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Student_Settings.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Student_Profile.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return true;
            }
        });

        recyclerView = findViewById(R.id.recyclerView);

        user = FirebaseAuth.getInstance().getCurrentUser();

        userId = user.getUid();


        reference = FirebaseDatabase.getInstance().getReference(userId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        loadData("");
    }


    private void loadData(String data){

        Query query = reference.orderByChild("Student_ID").startAt(data).endAt(data + "\uf8ff");

        String userId1 = user.getUid();

        options = new FirebaseRecyclerOptions.Builder<Items>().setQuery(reference, Items.class).build();
        adapter = new FirebaseRecyclerAdapter<Items, ListOfItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ListOfItemViewHolder holder, int position, @NonNull Items model) {





//                Picasso.get().load(model.getImage_Url()).into(holder.image_single_view_listOfClaims);
//                holder.single_view_notification_studentName.Text(model.get);
//                holder.itemDescription_single_view.setText(model.getStatus());
//                holder.itemDate_single_view.setText(model.getDate_Reported());
//                holder.itemLocation_single_view.setText(model.getLocation());
                    String itemKey =  getRef(position).getKey();

                reference.child(itemKey).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()){
                            String itemNAme2 = snapshot.child("Item_Name").getValue().toString();
                            String imageUrl2 = snapshot.child("Image_Url").getValue().toString();
                            String dateReported = snapshot.child("Date_Reported").getValue().toString();

                            holder.itemName_listOfClaims.setText(itemNAme2);
                            holder.itemDateReported_listOfClaims.setText(dateReported);
                            Picasso.get().load(imageUrl2).into(holder.image_single_view_listOfClaims);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @NonNull
            @Override
            public ListOfItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listofclaim_view,parent,false);
                return new ListOfItemViewHolder(v);

            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }





//        recyclerView = findViewById(R.id.recyclerView);
//        user = FirebaseAuth.getInstance().getCurrentUser();
//       // reference = FirebaseDatabase.getInstance().getReference("UsersLoginInformation");
//        reference = FirebaseDatabase.getInstance().getReference("ListOfCLaims");
//        userId = user.getUid();
//
//
//
//
//       listOfClaims = FirebaseDatabase.getInstance().getReference("ListOfCLaims");
//      //  listOfClaims = FirebaseDatabase.getInstance().getReference("UsersLoginInformation").child(userId).child("ListOfClaims");
//     //  .child(userId).child("ListOfClaims");
//
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recyclerView.setHasFixedSize(true);
//
//
//
//
//
//        loadData("");
//
//    }
//
//
//    private void loadData(String data){
//     //  Query query = dataRef.orderByChild("Item_Name").startAt(data).endAt(data+"\uf8ff");
//
//
//
//        //options = new FirebaseRecyclerOptions.Builder<Items>().setQuery(dataRef, Items.class).build();
//
//        options = new FirebaseRecyclerOptions.Builder<Items>().setQuery(listOfClaims.child(userId), Items.class).build();
//        adapter = new FirebaseRecyclerAdapter<Items, ListOfItemViewHolder>(options) {
//
//            @Override
//            protected void onBindViewHolder(@NonNull ListOfItemViewHolder holder, int position, @NonNull Items model) {
//
//
//                listOfClaims.child(userId).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        if(snapshot.exists()){
//                            String itemNAme2 = snapshot.child("Item_Name").getValue().toString();
//                            String iamgeUrl = snapshot.child("Image_Url").getValue().toString();
//
//                            holder.itemName_listOfClaims.setText(itemNAme2);
//
//                            Picasso.get().load(iamgeUrl).into(holder.image_single_view_listOfClaims);
//                            Toast.makeText(Student_ListOfClaim.this, userId, Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//
//                Picasso.get().load(model.getImage_Url()).into(holder.image_single_view_listOfClaims);
//                holder.itemName_listOfClaims.setText(model.getItem_Name());
//                holder.itemDateReported_listOfClaims.setText(model.getDate_Reported());
//
//
//                //To edit or delete, open view of the item
//                holder.view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
////                        Intent intent = new Intent(Admin_Home.this, Admin_EditDeleteItem.class);
////                        intent.putExtra("ItemKey", getRef(position).getKey());
////                        startActivity(intent);
//                    }
//                });
//
//            }
//            @NonNull
//            @Override
//            public ListOfItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listofclaim_view,parent,false);
//
//                return new ListOfItemViewHolder(v);
//            }
//        };
//
//        adapter.startListening();
//        recyclerView.setAdapter(adapter);
//



}