package com.example.ifoundhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class Admin_Home extends AppCompatActivity {

    EditText inputSearch;
    RecyclerView recyclerView;
    FloatingActionButton floatingBtn;
    FirebaseRecyclerOptions<Items> options;
    FirebaseRecyclerAdapter<Items, MyViewHolder>adapter;
    ImageButton sorting;
    DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
      //  getSupportActionBar().hide(); //this line hides the action bar
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_home);

        dataRef = FirebaseDatabase.getInstance().getReference().child("Items");
        sorting = findViewById(R.id.sorting);
        inputSearch = findViewById(R.id.inputSearch);
        recyclerView = findViewById(R.id.recyclerView);
        floatingBtn = findViewById(R.id.floatingBtn);

        //
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin_NewReport.class));

            }
        });


        loadData("");

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString() != null){
                    loadData(editable.toString());
                }else{
                    //default if wala
                    loadData("");
                }

            }
        });

    }

    private void loadData(String data){
        Query query = dataRef.orderByChild("Item_Name").startAt(data).endAt(data+"\uf8ff");



        //options = new FirebaseRecyclerOptions.Builder<Items>().setQuery(dataRef, Items.class).build();
        options = new FirebaseRecyclerOptions.Builder<Items>().setQuery(query, Items.class).build();
        adapter = new FirebaseRecyclerAdapter<Items, MyViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Items model) {

                Picasso.get().load(model.getImage_Url()).into(holder.image_single_view);
                holder.itemName_single_view.setText(model.getItem_Name());
                holder.itemDescription_single_view.setText(model.getStatus());
                holder.itemDate_single_view.setText(model.getDate_Reported());
                holder.itemLocation_single_view.setText(model.getLocation());

                //To edit or delete, open view of the item
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Admin_Home.this, Admin_EditDeleteItem.class);
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
        recyclerView.setAdapter(adapter);

    }
}