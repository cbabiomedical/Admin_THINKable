package com.example.adminthinkable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.adminthinkable.Model.UploadGame;
import com.example.adminthinkable.Model.UploadVideo;
import com.example.adminthinkable.adapter.GameAdapter;
import com.example.adminthinkable.adapter.VideoAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayUploadedVideo extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<UploadVideo> uploadVideos;
    private VideoAdapter videoAdapter;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_uploaded_video);
        reference = FirebaseDatabase.getInstance().getReference().child("Video_Admin");
        recyclerView = findViewById(R.id.recyclerView);
        initData();
    }

    private void initData() {
        uploadVideos = new ArrayList<>();

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Video_Admin");
        Log.d("Display Path",reference.getKey());
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UploadVideo post = dataSnapshot.getValue(UploadVideo.class);
                    Log.d("Video Post", String.valueOf(post));

                    uploadVideos.add(post);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error", String.valueOf(error));

            }
        });
        videoAdapter = new VideoAdapter(getApplicationContext(),uploadVideos);
        recyclerView.setAdapter(videoAdapter);
    }
}