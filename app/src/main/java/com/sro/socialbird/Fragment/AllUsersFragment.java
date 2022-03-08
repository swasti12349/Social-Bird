package com.sro.socialbird.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sro.socialbird.R;
import com.sro.socialbird.adapter.userAdapter;
import com.sro.socialbird.model.user;

import java.util.ArrayList;

public class AllUsersFragment extends Fragment {
    RecyclerView alluserrv;
    RecyclerView.Adapter alluseradapter;
    DatabaseReference reference;
    ArrayList<user> dbuserArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alluserfragment, container, false);
        alluserrv = view.findViewById(R.id.alluserrv);
        alluserrv.setLayoutManager(new LinearLayoutManager(getContext()));
        dbuserArrayList = new ArrayList<>();

        getUsers();
        return view;
    }

    private void getUsers() {
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dbuserArrayList.clear();
                for (DataSnapshot s : snapshot.getChildren()) {
                    user users = s.getValue(user.class);
                    if (!users.getId().equals(FirebaseAuth.getInstance().getUid()))
                        dbuserArrayList.add(users);
                }
                alluseradapter = new userAdapter(getContext(), dbuserArrayList);
                alluserrv.setAdapter(alluseradapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
