package com.example.realestateapplication.Fragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import com.example.realestateapplication.Controllers.AgentListActivity;
import com.example.realestateapplication.Controllers.HomeActivity;
import com.example.realestateapplication.Models.Agent;
import com.example.realestateapplication.Models.Property;
import com.example.realestateapplication.R;

public class ContactPropertyAgentDialogFragment extends DialogFragment {

    private Property property;
    private Agent agent;

    private EditText emailSubject;
    private EditText emailBody;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ContactPropertyAgentDialogFragment(Property property, Agent agent) {
        this.property = property;
        this.agent = agent;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_contact_property_agent, container, false);

        initializeNotificationChannel();
        emailSubject = root.findViewById(R.id.contactAgentFormSubjectInput);
        emailBody = root.findViewById(R.id.contactAgentFormMessageInput);

        root.findViewById(R.id.sendEmailBtn).setOnClickListener(e -> {
            emailAgent();
        });
        return root;
    }

    private void initializeNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Channel ID", "Notification Name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    public void playSuccessSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.caching);
        mediaPlayer.start();
    }

    public void sendNotification() {
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
        builder.setContentText(getString(R.string.your_message_for) + " " + property.getPropertyAddress() + " " + getString(R.string.was_sent));
        builder.setSmallIcon(R.drawable.house_notification_icon);
        builder.addAction(R.drawable.house_notification_icon, getString(R.string.home_page), contentPendingIntent);
        builder.addAction(R.drawable.ic_agents, getString(R.string.view_agents), agentPendingIntent);
        builder.setColor(getResources().getColor(R.color.btnColor));
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
        managerCompat.notify(1, builder.build());
    }

    public void emailAgent() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, emailBody.getText().toString());
        intent.setData(Uri.parse("mailto:" + agent.getEmail()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        getActivity().startActivityForResult(intent, 0);
        dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode  == 0) {
            sendNotification();
            playSuccessSound();
            ContactEmailSuccessFragment dialogFragment = new ContactEmailSuccessFragment();
            dialogFragment.show(getActivity().getSupportFragmentManager(), "ContactEmailSuccessFragment");
        }

    }
}
