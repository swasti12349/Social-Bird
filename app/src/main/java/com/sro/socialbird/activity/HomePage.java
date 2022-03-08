package com.sro.socialbird.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sro.socialbird.Fragment.AllUsersFragment;
import com.sro.socialbird.Fragment.ChatFragment;
import com.sro.socialbird.Fragment.PostFragment;
import com.sro.socialbird.Fragment.ProfileFragment;
import com.sro.socialbird.R;
import com.sro.socialbird.databinding.ActivityHomePageBinding;

public class HomePage extends AppCompatActivity {

BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        bottomNavigationView = findViewById(R.id.btmnavbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new PostFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.allusers){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new AllUsersFragment()).commit();
                }
                if (item.getItemId()==R.id.posts){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new PostFragment()).commit();
                }

                if (item.getItemId()==R.id.profiles){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new ProfileFragment()).commit();
                }
                return true;
            }
        });

    }
}