package com.example.ifoundhub;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationViewholder extends RecyclerView.ViewHolder{

    ImageView image_single_view_notification;
    TextView single_view_notification_studentName,  single_view_notification_DateReported;

    //Open View event Listener
    View view;
    public NotificationViewholder(@NonNull View itemView) {
        super(itemView);

        image_single_view_notification = itemView.findViewById(R.id.image_single_view_notification);
        single_view_notification_studentName  = itemView.findViewById(R.id.single_view_notification_studentName);
        single_view_notification_DateReported = itemView.findViewById(R.id.single_view_notification_DateReported);

        view = itemView;
    }
}
