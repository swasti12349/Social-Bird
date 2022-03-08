package com.sro.socialbird.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.sro.socialbird.R;
import com.sro.socialbird.activity.ChatActivity;
import com.sro.socialbird.model.user;

import java.util.ArrayList;

public class userAdapter extends RecyclerView.Adapter<userAdapter.userViewHolder> {
    Context context;
    ArrayList<user> dbuserArrayList;

    public userAdapter(Context context, ArrayList<user> dbuserArrayList) {
        this.context = context;
        this.dbuserArrayList = dbuserArrayList;
    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.useritem, parent , false);
        userViewHolder holder = new userViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
        user dbusers = dbuserArrayList.get(position);
        holder.Name.setText(dbusers.getName());
        holder.Mobile.setText(dbusers.getPhone());
        Picasso.get().load(dbusers.getImageURL()).into(holder.userImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("nameforchat", dbusers.getName());
                intent.putExtra("idforchat", dbusers.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dbuserArrayList.size();
    }

    static class userViewHolder extends RecyclerView.ViewHolder {
            ImageView userImg;
            TextView Name, Mobile;
        public userViewHolder(@NonNull View itemView) {
            super(itemView);
            userImg = itemView.findViewById(R.id.userprofile);
            Name = itemView.findViewById(R.id.username);
            Mobile = itemView.findViewById(R.id.userphno);
        }
    }


}
