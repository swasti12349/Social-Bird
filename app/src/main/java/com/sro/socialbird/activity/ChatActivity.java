package com.sro.socialbird.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sro.socialbird.R;
import com.sro.socialbird.adapter.MessageAdapter;
import com.sro.socialbird.databinding.ActivityChatBinding;
import com.sro.socialbird.model.message;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    ArrayList<message> messageArrayList;
    RecyclerView messageRV;
    RecyclerView.Adapter messageAdapter;
    String sendRoom, recieverRoom;

    FirebaseDatabase database;
    CircleImageView sendbtn;
    EditText edtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messageArrayList = new ArrayList<>();
        String name = getIntent().getStringExtra("nameforchat");
        database = FirebaseDatabase.getInstance();
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        messageRV =  findViewById(R.id.messagerv);
        messageRV.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter(this, messageArrayList);
        messageRV.setAdapter(messageAdapter);


        messageRV.smoothScrollToPosition(messageAdapter.getItemCount());
        String recieverID = getIntent().getStringExtra("idforchat");

        String senderID = FirebaseAuth.getInstance().getUid();
        Log.d("inqw", recieverID + " " + senderID);

        sendbtn = findViewById(R.id.sendbtn);
        edtext = findViewById(R.id.edtext);
        sendRoom = senderID + recieverID;
        recieverRoom = recieverID + senderID;

        database.getReference().child("chats").child(sendRoom).child("message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messageArrayList.clear();

                for (DataSnapshot s : snapshot.getChildren()) {
                    message msg = s.getValue(message.class);
                    messageArrayList.add(msg);
                }
                messageAdapter.notifyDataSetChanged();
                messageRV.smoothScrollToPosition(messageAdapter.getItemCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = edtext.getText().toString();
                edtext.setText("");
                Date date = Calendar.getInstance().getTime();

                message msg = new message(senderID, message, date.toString());
                message msg1 = new message(recieverID, message, date.toString());

                database.getReference()
                        .child("chats")
                        .child(sendRoom)
                        .child("message")
                        .push()
                        .setValue(msg)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                database.getReference()
                                        .child("chats")
                                        .child(recieverRoom)
                                        .child("message")
                                        .push()
                                        .setValue(msg1)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {


                                            }
                                        });
                            }
                        });
                messageAdapter.notifyDataSetChanged();
                messageRV.smoothScrollToPosition(messageAdapter.getItemCount());
            }


        });



    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}