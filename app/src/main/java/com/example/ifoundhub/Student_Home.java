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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class Student_Home extends AppCompatActivity {

    EditText inputSearch;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Items> options;
    FirebaseRecyclerAdapter<Items, MyViewHolder>adapter;
    ImageButton sorting;
    Button lostfilter;
    Button foundfilter;
    Button allfilter;
    private LinearLayout sortView;
    boolean sortHidden = true;
    DatabaseReference dataRef;



    private ImageButton btnprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //  getSupportActionBar().hide(); //this line hides the action bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);


        //BottomNavigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:

                        return true;

                    case R.id.itemCheck:
                        startActivity(new Intent(getApplicationContext(), Student_ListOfClaim.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Student_Settings.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Student_Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return true;
            }
        });



        initWidgets();
        hideSort();



        dataRef = FirebaseDatabase.getInstance().getReference().child("Items");
        lostfilter = findViewById(R.id.lostfilter);
        foundfilter = findViewById(R.id.foundfilter);
        allfilter = findViewById(R.id.allfilter);
        inputSearch = findViewById(R.id.inputSearch);
        recyclerView = findViewById(R.id.recyclerView);



        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        lostdata ("Lost");

        lostfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lostdata("Lost");
            }

        });

        founddata ("Found");

        foundfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                founddata("Found");

            }

        });


        allfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadData("");
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
    private void initWidgets()
    {
        sorting = findViewById(R.id.sorting);
        sortView = (LinearLayout) findViewById(R.id.sortTabsLayout2);

    }
    //
    public void showSortTapped(View view)
    {
        if(sortHidden == true)
        {
            sortHidden = false;
            showSort();
        }
        else
        {
            sortHidden = true;
            hideSort();
        }
    }

    private void hideSort()
    {
        sortView.setVisibility(View.GONE);
    }

    private void showSort()
    {
        sortView.setVisibility(View.VISIBLE);
    }




    private void founddata(String data) {
        Query query = dataRef.orderByChild("Status").startAt(data).endAt(data + "\uf8ff");

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
                        Intent intent = new Intent(Student_Home.this, Student_viewItemInformation.class);
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

    private void lostdata(String data) {
        Query query = dataRef.orderByChild("Status").startAt(data).endAt(data + "\uf8ff");

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
                        Intent intent = new Intent(Student_Home.this, Student_viewItemInformation.class);
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
                        Intent intent = new Intent(Student_Home.this, Student_viewItemInformation.class);
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
