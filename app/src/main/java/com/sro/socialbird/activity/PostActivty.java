package com.sro.socialbird.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sro.socialbird.R;
import com.sro.socialbird.model.Post;
import com.sro.socialbird.model.user;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostActivty extends AppCompatActivity {

    ImageView imageView;
    Uri image;
    EditText postText;
    TextView post;
    String imgurl;
    ArrayList<Post> postArrayList;
    ProgressBar postpb;
    DatabaseReference databasereference;
    ProgressDialog progressDialog;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_activty);
        imageView = findViewById(R.id.postaddimg);
        postText = findViewById(R.id.posttexts);
        post = findViewById(R.id.postbtn);
        postpb = findViewById(R.id.postpb);
        progressDialog = new ProgressDialog(PostActivty.this);
        postArrayList = new ArrayList<>();
        databasereference = FirebaseDatabase.getInstance().getReference("Post");

        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Post Uploading");
        progressDialog.incrementProgressBy(99);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 45);

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!postText.getText().toString().isEmpty() || image != null) {
                    progressDialog.show();
                    post(image);
                } else {
                    Toast.makeText(PostActivty.this, "Please type something", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (data.getData() != null) {
                imageView.setImageURI(data.getData());
                image = data.getData();
            }
        }
    }

    //    private void post(Uri image) {
//        if (image != null) {
//            FirebaseStorage.getInstance().getReference().child("Post").child(FirebaseAuth.getInstance().getUid()).putFile(image).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                    if (task.isSuccessful()) {
//
//                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Profile").child(FirebaseAuth.getInstance().getUid());
//                        storageReference.putFile(image).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            imgurl = uri.toString();
//                                            databasereference = FirebaseDatabase.getInstance().getReference("Post");
//                                            Post post;
//                                            post = new Post(postText.getText().toString(), imgurl, FirebaseAuth.getInstance().getUid(), new Date().getTime());
//                                            postArrayList.add(post);
//                                            databasereference.push().setValue(post);
//                                            Toast.makeText(PostActivty.this, "Posted", Toast.LENGTH_SHORT).show();
//                                            startActivity(new Intent(PostActivty.this, HomePage.class));
//                                            finishAffinity();
//
//                                        }
//                                    });
//                                } else {
//                                    progressDialog.dismiss();
////                                        postpb.setVisibility(View.INVISIBLE);
////                                        post.setVisibility(View.VISIBLE);
//                                }
//                            }
//                        });
//
//
//                    }
//                }
//            });
//        } else {
//            Post post = new Post(postText.getText().toString(), FirebaseAuth.getInstance().getUid(), new Date().getTime());
//            databasereference = FirebaseDatabase.getInstance().getReference("Post");
//            postArrayList.add(post);
//            databasereference.push().setValue(post);
//            Toast.makeText(PostActivty.this, "Posted", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(PostActivty.this, HomePage.class));
//            finishAffinity();
//
//        }
//    }
    private void post(Uri image) {
        if (image != null) {
            storageReference = FirebaseStorage.getInstance().getReference().child("Post").child(FirebaseAuth.getInstance().getUid());
            storageReference.putFile(image).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imgurl = uri.toString();
                                databasereference = FirebaseDatabase.getInstance().getReference("Post");
                                Post post;
                                post = new Post(postText.getText().toString(), imgurl, FirebaseAuth.getInstance().getUid(), new Date().getTime());
                                postArrayList.add(post);
                                databasereference.push().setValue(post);
                                Toast.makeText(PostActivty.this, "Posted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(PostActivty.this, HomePage.class));
                                finishAffinity();
                            }

                        });
                    } else {
                        progressDialog.dismiss();
                    }
                }
            });

        } else {
            Post post = new Post(postText.getText().toString(), FirebaseAuth.getInstance().getUid(), new Date().getTime());
            databasereference = FirebaseDatabase.getInstance().getReference("Post");
            postArrayList.add(post);
            databasereference.push().setValue(post);
            Toast.makeText(PostActivty.this, "Posted", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostActivty.this, HomePage.class));
            finishAffinity();
        }
    }
}