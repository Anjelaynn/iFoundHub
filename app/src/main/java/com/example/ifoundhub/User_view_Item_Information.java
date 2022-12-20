package com.example.ifoundhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class User_view_Item_Information extends AppCompatActivity {

    //Dialog Variables
    AlertDialog.Builder builderDialog;
    AlertDialog alertDialog;



    ImageView itemImageView;

    TextView user_itemName, user_itemStatus, user_itemLocation, user_itemDateReported, user_itemDescription;


    ImageButton btnback;
    Button btnFound;

    DatabaseReference ref, DataRef;
    StorageReference storageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //  getSupportActionBar().hide(); //this line hides the action bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_item_information);


        itemImageView = findViewById(R.id.itemImageView);
        user_itemName = findViewById(R.id.user_itemName);
        user_itemStatus = findViewById(R.id.user_itemStatus);
        user_itemLocation = findViewById(R.id.user_item_location);
        user_itemDateReported = findViewById(R.id.user_item_dateReported);
        user_itemDescription = findViewById(R.id.user_itemDescription);

        btnback = findViewById(R.id.userBtnBack);
        btnFound = findViewById(R.id.user_btnFound);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), User_Home.class));
            }
        });

        btnFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(R.layout.custom_founddialog);
            }
        });


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
                    String itemDateReported = snapshot.child("Date_Reported").getValue().toString();
                    String itemLocation = snapshot.child("Location").getValue().toString();
                    String status = snapshot.child("Status").getValue().toString();




                    Picasso.get().load(imageUrl).into(itemImageView);
                    user_itemName.setText(itemName);
                    user_itemStatus.setText(status);
                    user_itemLocation.setText(itemLocation);
                    user_itemDateReported.setText(itemDateReported);
                    user_itemDescription.setText(itemDescription);


                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {



            }
        });


    }



    //------------------Delete Dialog-------------

    private void showAlertDialog(int custom_founddialog) {

        builderDialog = new AlertDialog.Builder(this);
        View layoutview = getLayoutInflater().inflate(custom_founddialog,null);

        AppCompatButton dialogButtonOkay = layoutview.findViewById(R.id.buttonOkay);


        builderDialog.setView(layoutview);
        alertDialog=builderDialog.create();
        alertDialog.show();


        dialogButtonOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });


        //click on Yes button
//        dialogButtonYes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                DataRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Toast.makeText(Admin_EditDeleteItem.this, "Your Data is Succesfully Deleted", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(getApplicationContext(), Admin_Home.class));
//                            }
//                        });
//                    }
//                });
//
//
//            }
//        });



    }
    //------------------Delete Dialog-------------
}