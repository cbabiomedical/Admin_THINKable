package com.example.adminthinkable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.adminthinkable.Model.UploadMeditate;
import com.example.adminthinkable.Model.UploadSong;
import com.example.adminthinkable.adapter.MeditationAdapter;
import com.example.adminthinkable.adapter.MusicAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayMeditateSongs extends AppCompatActivity implements MeditationAdapter.OnNoteListner{
    private RecyclerView recyclerView;
    private ArrayList<UploadMeditate> uploadedSongs;
    private MeditationAdapter musicAdapter;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_meditate_songs);
        reference = FirebaseDatabase.getInstance().getReference().child("Meditation");
        recyclerView = findViewById(R.id.recyclerView);

        initData();

    }

    private void initData() {
        uploadedSongs = new ArrayList<>();

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Meditation_Admin");
        Log.d("Display Path",reference.getKey());
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UploadMeditate post = dataSnapshot.getValue(UploadMeditate.class);
                    Log.d("Post", String.valueOf(post));

                    uploadedSongs.add(post);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error", String.valueOf(error));

            }
        });
        musicAdapter = new MeditationAdapter(getApplicationContext(),uploadedSongs,this::onNoteClick);
        recyclerView.setAdapter(musicAdapter);

    }

    @Override
    public void onNoteClick(int position) {
        String uri=uploadedSongs.get(position).getUrl();
        Log.d("Sending URL",uri);
        String image= uploadedSongs.get(position).getMeditateImage();
        String name=uploadedSongs.get(position).getMeditateName();
        String id=uploadedSongs.get(position).getMediateId();

        startActivity(new Intent(getApplicationContext(),PlayMeditation.class).putExtra("name",name).putExtra("image",image).putExtra("uri",uri).putExtra("id",id));

    }
}