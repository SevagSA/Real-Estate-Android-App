package com.example.realestateapplication.Fragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.realestateapplication.Models.Agent;
import com.example.realestateapplication.Models.Property;
import com.example.realestateapplication.R;

public class ContactPropertyAgentDialogFragment extends DialogFragment {

    private final Agent agent;

    private EditText emailSubject;
    private EditText emailBody;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ContactPropertyAgentDialogFragment(Agent agent) {
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

    public void emailAgent() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, emailBody.getText().toString());
        intent.setData(Uri.parse("mailto:" + agent.getEmail()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        getActivity().startActivityForResult(intent, 0);
        dismiss();
    }
}
