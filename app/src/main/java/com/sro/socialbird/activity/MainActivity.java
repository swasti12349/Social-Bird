package com.sro.socialbird.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sro.socialbird.R;
import com.sro.socialbird.databinding.ActivityMainBinding;
import com.sro.socialbird.model.user;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    String email, name, phno, password;
    DatabaseReference databasereference;
    Button signup;
    FirebaseStorage firebaseStorage;
    Uri selectedIMG;
    String imgurl;

    public static ArrayList<user> alluserArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseStorage = FirebaseStorage.getInstance();
        databasereference = FirebaseDatabase.getInstance().getReference("users");
        alluserArrayList = new ArrayList<>();
        binding.profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 45);
            }
        });
        binding.loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        signup = findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = binding.Name.getText().toString();
                email = binding.Email.getText().toString();
                phno = binding.Phone.getText().toString();
                password = binding.Password.getText().toString();

                if (name.isEmpty() || email.isEmpty() || phno.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Some fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (selectedIMG != null) {
                        binding.registerpb.setVisibility(View.VISIBLE);
                        binding.signup.setVisibility(View.INVISIBLE);
                        signup(name, email, phno, password);
                    } else {
                        Toast.makeText(MainActivity.this, "Please select an Image", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (data.getData() != null) {
                binding.profileimg.setImageURI(data.getData());
                selectedIMG = data.getData();
            }
        }
    }

    private void signup(String name, String email, String phno, String password) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (selectedIMG != null) {
                                StorageReference storageReference = firebaseStorage.getReference().child("Profile").child(firebaseAuth.getUid());
                                storageReference.putFile(selectedIMG).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    imgurl = uri.toString();
                                                    databasereference = FirebaseDatabase.getInstance().getReference("users");
                                                    String uid = FirebaseAuth.getInstance().getUid();
                                                    Log.d("imgurlo", imgurl.toString());
                                                    user users = new user(uid, name, email, phno, password, imgurl);
                                                    alluserArrayList.add(users);
                                                    databasereference.child(FirebaseAuth.getInstance().getUid()).setValue(users);
                                                    Toast.makeText(MainActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                                    finishAffinity();

                                                }
                                            });
                                        }
                                    }
                                });
                            } else {
                                binding.registerpb.setVisibility(View.INVISIBLE);
                                binding.signup.setVisibility(View.VISIBLE);
                                Toast.makeText(MainActivity.this, "Please select an Image", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            binding.registerpb.setVisibility(View.INVISIBLE);
                            binding.signup.setVisibility(View.VISIBLE);
                            Log.d("eroor", task.getException().toString());
                            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            if (task.getException().toString() == "com.google.firebase.FirebaseNetworkException") {
                                Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                }
        );
    }
}