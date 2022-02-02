package com.example.adminthinkable;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminthinkable.Model.UploadGame;
import com.example.adminthinkable.adapter.GameAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayUploadedGames extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<UploadGame> uploadGames;
    private GameAdapter musicAdapter;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_display_uploaded_games);
        reference = FirebaseDatabase.getInstance().getReference().child("Games_Admin");
        recyclerView = findViewById(R.id.recyclerView);
        initData();

    }

    private void initData() {
        uploadGames = new ArrayList<>();

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Games_Admin");
        Log.d("Display Path",reference.getKey());
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UploadGame post = dataSnapshot.getValue(UploadGame.class);
                    Log.d("Post", String.valueOf(post));

                    uploadGames.add(post);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error", String.valueOf(error));

            }
        });
        musicAdapter = new GameAdapter(getApplicationContext(),uploadGames);
        recyclerView.setAdapter(musicAdapter);

    }


}