package com.example.ifoundhub;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView image_single_view;
    TextView itemName_single_view, itemDescription_single_view, itemDate_single_view, itemLocation_single_view;

    //Open View event Listener
    View view;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        image_single_view = itemView.findViewById(R.id.image_single_view);
        itemName_single_view = itemView.findViewById(R.id.itemName_single_view);
        itemDescription_single_view = itemView.findViewById(R.id.itemDescription_single_view);
        itemDate_single_view = itemView.findViewById(R.id.itemDate_single_view);
        itemLocation_single_view = itemView.findViewById(R.id.itemLocation_single_view);

        view = itemView;
    }
}
