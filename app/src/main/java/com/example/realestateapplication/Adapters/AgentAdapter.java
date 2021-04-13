package com.example.realestateapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.realestateapplication.Controllers.AgentActivity;
import com.example.realestateapplication.Models.Agent;
import com.example.realestateapplication.R;

import java.util.ArrayList;

public class AgentAdapter extends ArrayAdapter {

    ArrayList<Agent> agents = new ArrayList<>();
    Context context;

    public AgentAdapter(Context context, int textViewResourceId, ArrayList<Agent> agents) {
        super(context, textViewResourceId, agents);
        this.agents = agents;
        this.context = context;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.agent_item, null);
        ImageView agentImg = v.findViewById(R.id.agentImg);
        TextView agentName = v.findViewById(R.id.agentName);
        TextView agentEmail = v.findViewById(R.id.agentEmail);
        TextView agentCompany = v.findViewById(R.id.agentCompany);

        agentImg.setImageResource(agents.get(position).getProfileImgId());
        agentName.setText(agents.get(position).getFullName());
        agentEmail.setText(agents.get(position).getEmail());
        agentCompany.setText(agents.get(position).getCompanyName());

        v.findViewById(R.id.viewAgentBtn).setOnClickListener(e -> {
            Intent intent = new Intent(context, AgentActivity.class);
            intent.putExtra("agent", agents.get(position));
            context.startActivity(intent);
        });

        return v;

    }

}
