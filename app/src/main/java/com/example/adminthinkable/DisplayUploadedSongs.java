package com.example.adminthinkable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.adminthinkable.Model.UploadMeditate;
import com.example.adminthinkable.Model.UploadSong;
import com.example.adminthinkable.adapter.MusicAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class DisplayUploadedSongs extends AppCompatActivity implements MusicAdapter.OnNoteListner{
    private RecyclerView recyclerView;
    private ArrayList<UploadSong> uploadedSongs;
    private MusicAdapter musicAdapter;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_display_uploaded_songs);
        reference = FirebaseDatabase.getInstance().getReference().child("Songs_Admin");
        recyclerView = findViewById(R.id.recyclerView);

        initData();

    }

    private void initData() {
        uploadedSongs = new ArrayList<>();

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Songs_Admin");
        Log.d("Display Path",reference.getKey());
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UploadSong post = dataSnapshot.getValue(UploadSong.class);
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
        musicAdapter = new MusicAdapter(getApplicationContext(),uploadedSongs,this::onNoteClick);
        recyclerView.setAdapter(musicAdapter);
    }


    @Override
    public void onNoteClick(int position) {
        uploadedSongs.get(position);
        String songName = uploadedSongs.get(position).getName();
        String url = uploadedSongs.get(position).getSongTitle1();
        Log.d("url",url);
        String image = uploadedSongs.get(position).getImageUrl();
        String id=uploadedSongs.get(position).getId();
        Log.d("Url", url);
        startActivity(new Intent(getApplicationContext(), PlayMusicActivity.class).putExtra("uri", url).putExtra("name", songName).putExtra("image", image).putExtra("id",id));
    }
}