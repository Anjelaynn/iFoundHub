package com.example.ifoundhub;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListOfItemViewHolder extends RecyclerView.ViewHolder {

    ImageView image_single_view_listOfClaims;

    TextView itemName_listOfClaims,  itemDateReported_listOfClaims;

    //Open View event Listener
    View view;
    public ListOfItemViewHolder(@NonNull View itemView) {
        super(itemView);


        image_single_view_listOfClaims = itemView.findViewById(R.id.image_single_view_listOfClaims);
        itemName_listOfClaims  = itemView.findViewById(R.id.itemName_listOfClaims);
        itemDateReported_listOfClaims = itemView.findViewById(R.id.itemDateReported_listOfClaims);

        view = itemView;
    }
}
