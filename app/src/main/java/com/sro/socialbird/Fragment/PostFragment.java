package com.sro.socialbird.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sro.socialbird.R;
import com.sro.socialbird.activity.PostActivty;
import com.sro.socialbird.adapter.PostAdapter;
import com.sro.socialbird.model.Post;
import com.sro.socialbird.model.message;

import java.util.ArrayList;

public class PostFragment extends Fragment {

    FloatingActionButton createPost;
    RecyclerView postRV;
    RecyclerView.Adapter postAdapter;
    ArrayList<Post> postArrayList;
    ProgressBar pb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post, container, false);
        createPost = view.findViewById(R.id.createpost);

        postRV = view.findViewById(R.id.verticalrv);
        pb = view.findViewById(R.id.pbi);
        postArrayList = new ArrayList<>();
        getPost();
        postAdapter = new PostAdapter(getContext(), postArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        postRV.setLayoutManager(layoutManager);
        postRV.setAdapter(postAdapter);

        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PostActivty.class));
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pb.setVisibility(View.INVISIBLE);
            }
        }, 4000);


        return view;
    }

    private void getPost() {

        FirebaseDatabase.getInstance().getReference("Post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postArrayList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Post post = snapshot1.getValue(Post.class);
                    post.setPostID(snapshot1.getKey());
                    postArrayList.add(post);
                }

                postAdapter.notifyDataSetChanged();

            }
            // com.google.firebase.FirebaseNetworkException

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
