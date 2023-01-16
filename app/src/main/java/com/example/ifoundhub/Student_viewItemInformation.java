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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Student_viewItemInformation extends AppCompatActivity {

    //FireStore Database
//    private FirebaseFirestore fireStoreDatabaseReference;


    //user information
        private FirebaseUser user;
        private DatabaseReference reference;
        private String userId;




    //Dialog Variables
    AlertDialog.Builder builderDialog;
    AlertDialog alertDialog;



    ImageView itemImageView;

    TextView user_itemName, user_itemStatus, user_itemLocation, user_itemDateReported, user_itemDescription;
    TextView imageTextUrl, itemKeyNumber;


    private TextView editTextUserName;

    ImageButton btnback;
    Button btnFound;

    DatabaseReference ref, DataRef, notificationReference1, notificationReference2;
    StorageReference storageRef, notificationReferencePic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //  getSupportActionBar().hide(); //this line hides the action bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_item_information);


        itemImageView = findViewById(R.id.itemImageView);
        user_itemName = findViewById(R.id.user_itemName);
        user_itemStatus = findViewById(R.id.user_itemStatus);
        user_itemLocation = findViewById(R.id.user_item_location);
        user_itemDateReported = findViewById(R.id.user_item_dateReported);
        user_itemDescription = findViewById(R.id.user_itemDescription);
        imageTextUrl = findViewById(R.id.imageTextUrl);
        editTextUserName = findViewById(R.id.editTextUserName);


        btnback = findViewById(R.id.userBtnBack);
        btnFound = findViewById(R.id.user_btnFound);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Student_Home.class));
            }
        });



        //user information
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("UsersLoginInformation");
        userId = user.getUid();

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserClass userProfile = snapshot.getValue(UserClass.class);

                if(userProfile != null){
                    String fullname = userProfile.fullname;
                    editTextUserName.setText(fullname);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Student_viewItemInformation.this, "Something happened!", Toast.LENGTH_LONG).show();
            }
        });




        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("UsersLoginInformation");
        userId = user.getUid();


        ref = FirebaseDatabase.getInstance().getReference().child("Items");
        String itemKey = getIntent().getStringExtra("ItemKey");

        DataRef = FirebaseDatabase.getInstance().getReference().child("Items").child(itemKey);

        storageRef = FirebaseStorage.getInstance().getReference().child("ItemImage").child(itemKey+".jpg");



        ref.child(itemKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    String imageUrlText1 = snapshot.child("Image_Url").getValue().toString();
                    String imageUrl = snapshot.child("Image_Url").getValue().toString();
                    String itemName = snapshot.child("Item_Name").getValue().toString();
                    String itemDescription = snapshot.child("Item_Description").getValue().toString();
                    String itemDateReported = snapshot.child("Date_Reported").getValue().toString();
                    String itemLocation = snapshot.child("Location").getValue().toString();
                    String status = snapshot.child("Status").getValue().toString();




                    Picasso.get().load(imageUrl).into(itemImageView);
                    imageTextUrl.setText(imageUrlText1);
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


        btnFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notificationReference1 = FirebaseDatabase.getInstance().getReference().child("Notification");
                notificationReference2 = FirebaseDatabase.getInstance().getReference().child("Notification").child(itemKey);
                notificationReferencePic = FirebaseStorage.getInstance().getReference().child("ItemImage").child(itemKey+".jpg");

//                DatabaseReference ref1 =  FirebaseDatabase.getInstance().getReference().child("UsersLoginInformation");
//
//                ref1.child(userId).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        if(snapshot.exists()) {
//                            String email = snapshot.child("email").getValue().toString();
//                            HashMap hashMap = new HashMap();
//                            hashMap.put("UserEmail", email);
//
//
//
//                            notificationReference1.child(itemKey).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//
//
//                                    showAlertDialog(R.layout.custom_founddialog);
//                                    Toast.makeText(Student_viewItemInformation.this, "Admin have been notified", Toast.LENGTH_SHORT).show();
//
//                                }
//                            });
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });



                HashMap hashMap = new HashMap();
                hashMap.put("Item_Name", user_itemName.getText().toString());
                hashMap.put("Image_Url", imageTextUrl.getText().toString());
                hashMap.put("Item_Description", user_itemDescription.getText().toString());
                hashMap.put("Date_Reported",  user_itemDateReported.getText().toString());
                hashMap.put("Location",  user_itemLocation.getText().toString());
                hashMap.put("Status", user_itemStatus.getText().toString());
                hashMap.put("Student_Name", editTextUserName.getText().toString());
                hashMap.put("Student_ID", userId);
                hashMap.put("isClaim", "Not Claim");



//                fireStoreDatabaseReference = FirebaseFirestore.getInstance();
//
//                fireStoreDatabaseReference.collection(itemKey).add(hashMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        showAlertDialog(R.layout.custom_founddialog);
//                        Toast.makeText(Student_viewItemInformation.this, "Admin have been notified", Toast.LENGTH_SHORT).show();
//
//                    }
//                });


                notificationReference1.child(itemKey).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {


                        showAlertDialog(R.layout.custom_founddialog);
                        Toast.makeText(Student_viewItemInformation.this, "Admin have been notified", Toast.LENGTH_SHORT).show();

                         }
                });


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