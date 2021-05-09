package com.example.realestateapplication.Fragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import com.example.realestateapplication.Controllers.AgentListActivity;
import com.example.realestateapplication.Controllers.HomeActivity;
import com.example.realestateapplication.Models.Property;
import com.example.realestateapplication.R;

public class ContactPropertyAgentDialogFragment extends DialogFragment {

    private Property property;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ContactPropertyAgentDialogFragment(Property property) {
        this.property = property;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_contact_property_agent, container, false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Channel ID", "Notification Name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        root.findViewById(R.id.sendEmailBtn).setOnClickListener(e -> {
            MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.caching);
            mediaPlayer.start();

//            TODO create a "viewAllSentMessages" activity for the owner of the proeprty and
//             put the intent there, not HomeActivity
            Intent propertyDetailIntent = new Intent(getActivity(), HomeActivity.class);
            PendingIntent contentPendingIntent = PendingIntent.getActivity(getContext(),
                    0, propertyDetailIntent, 0);

            Intent agentIntent = new Intent(getActivity(), AgentListActivity.class);
            PendingIntent agentPendingIntent = PendingIntent.getActivity(getContext(),
                    0, agentIntent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "Channel ID");
            builder.setContentTitle(getString(R.string.message_has_been_sent));
            builder.setContentText(getString(R.string.your_message_for) + " " + property.getPropertyAddress() + " " +  getString(R.string.was_sent));
            builder.setSmallIcon(R.drawable.house_notification_icon);
            builder.addAction(R.drawable.house_notification_icon, getString(R.string.home_page), contentPendingIntent);
            builder.addAction(R.drawable.ic_agents, getString(R.string.view_agents), agentPendingIntent);
            builder.setColor(getResources().getColor(R.color.btnColor));
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
            managerCompat.notify(1, builder.build());
        });
        return root;
    }
}
