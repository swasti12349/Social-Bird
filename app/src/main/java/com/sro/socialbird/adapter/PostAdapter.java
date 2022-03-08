package com.sro.socialbird.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.sro.socialbird.R;
import com.sro.socialbird.databinding.PostitemBinding;
import com.sro.socialbird.model.Post;
import com.sro.socialbird.model.user;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    Context context;
    ArrayList<Post> postArrayList;

    public PostAdapter(Context context, ArrayList<Post> postArrayList) {
        this.context = context;
        this.postArrayList = postArrayList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.postitem, parent, false);
        PostViewHolder viewHolder = new PostViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postArrayList.get(position);
        holder.binding.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Post")
                        .child(post.getPostID())
                        .child("likes")
                        .child(FirebaseAuth.getInstance().getUid())
                        .setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        FirebaseDatabase.getInstance().getReference("Post")
                                .child(post.getPostID())
                                .child("like")
                                .setValue(post.getLike() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });

            }
        });

        FirebaseDatabase.getInstance().getReference("Post")
                .child(post.getPostID())
                .child("like")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        holder.binding.postlikes.setText(snapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        holder.binding.postlikes.setText(post.getLike() + "");
        holder.binding.posttexti.setText(post.getPostText());
        Picasso.get().load(post.getPostImgURL()).into(holder.binding.postimgi);
        FirebaseDatabase.getInstance().getReference("users").child(post.getSenderID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user users = snapshot.getValue(user.class);
                holder.binding.senderName.setText(users.getName());
                Picasso.get().load(users.getImageURL()).into(holder.binding.senderImg);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        PostitemBinding binding;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = PostitemBinding.bind(itemView);
        }
    }
}
