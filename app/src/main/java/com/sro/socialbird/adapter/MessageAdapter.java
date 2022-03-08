package com.sro.socialbird.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.sro.socialbird.R;
import com.sro.socialbird.databinding.ItemrecBinding;
import com.sro.socialbird.databinding.ItemsentBinding;
import com.sro.socialbird.model.message;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<message> messageArrayList;
    int ItemSent = 1;
    int ItemRec = 2;

    public MessageAdapter(Context context, ArrayList<message> messageArrayList) {
        this.context = context;
        this.messageArrayList = messageArrayList;
    }

    @Override
    public int getItemViewType(int position) {

        if (messageArrayList.get(position).getSenderid() == FirebaseAuth.getInstance().getUid()) {
            return ItemSent;
        } else return ItemRec;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == ItemSent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsent, parent, false);
            return new SentViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemrec, parent, false);
            return new RecieveViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        message msg = messageArrayList.get(position);
        if (holder.getClass() == SentViewHolder.class) {
            SentViewHolder viewHolder = (SentViewHolder) holder;
            viewHolder.binding.mssgsent.setText(msg.getMessage());
        } else {
            RecieveViewHolder viewHolder = (RecieveViewHolder) holder;
            viewHolder.binding.mssgrec.setText(msg.getMessage());
        }
    }

    @Override
    public int getItemCount() {

        return messageArrayList.size();
    }

    public static class SentViewHolder extends RecyclerView.ViewHolder {

        ItemsentBinding binding;

        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemsentBinding.bind(itemView);
        }
    }

    public static class RecieveViewHolder extends RecyclerView.ViewHolder {

        ItemrecBinding binding;

        public RecieveViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemrecBinding.bind(itemView);
        }
    }

}