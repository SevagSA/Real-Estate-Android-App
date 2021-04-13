package com.example.realestateapplication.Fragments;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import com.example.realestateapplication.Controllers.HomeActivity;
import com.example.realestateapplication.R;

public class ContactPropertyAgentFragment extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(getContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getContext(), 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getContext(), "CHANNEL_ID")
                .setSmallIcon(R.drawable.house_notification_icon)
                .setContentTitle("Your email was sent. Thank you.")
                .setContentText("You just sent an email to a property's agent." +
                        "Expect a reply in at most 2 business days.")

                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        // notificationId is a unique int for each notification that you must define
//        notificationManager.notify(notificationId, builder.build());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_contact_property_agent, container, false);
    }
}
