package com.sro.socialbird.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sro.socialbird.R;

public class ChatFragment extends Fragment {
TextView times;
int t=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat, container, false);
        times = view.findViewById(R.id.time);

      new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
              Toast.makeText(getActivity(), "Hello", Toast.LENGTH_SHORT).show();
          }
      }, 1000);
        return view;


    }
}
