package com.example.adminthinkable.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminthinkable.EditGamePage;
import com.example.adminthinkable.EditMeditation;
import com.example.adminthinkable.Model.UploadMeditate;
import com.example.adminthinkable.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MeditationAdapter extends RecyclerView.Adapter<MeditationAdapter.ViewHolder> {
    private Context context;
    private ArrayList<UploadMeditate> uploadMeditates;
    private MusicAdapter.OnNoteListner onNoteListner;
    DatabaseReference reference1;

    public MeditationAdapter(Context context, ArrayList<UploadMeditate> uploadMeditates, MusicAdapter.OnNoteListner onNoteListner) {
        this.context = context;
        this.uploadMeditates = uploadMeditates;
        this.onNoteListner=onNoteListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_upload_meditate,parent,false);
        return new ViewHolder(view,onNoteListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.songTitle.setText(uploadMeditates.get(position).getMeditateName());
        holder.songId.setText(uploadMeditates.get(position).getMediateId());
        Picasso.get().load(uploadMeditates.get(position).getMeditateImage()).into(holder.imageView);
        holder.songUrl.setText(uploadMeditates.get(position).getUrl());
        holder.deleteMeditate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Meditation_Admin");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            UploadMeditate post=dataSnapshot.getValue(UploadMeditate.class);
                            if(post.getMediateId().equals(uploadMeditates.get(position).getMediateId())){
                                reference1 = databaseReference.child(dataSnapshot.getRef().getKey());
                                Log.d("ReferencePath", String.valueOf(reference1));
                                reference1.removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        holder.editMeditation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myactivity = new Intent(context.getApplicationContext(), EditMeditation.class).putExtra("name",uploadMeditates.get(position).getMeditateName()).putExtra("image",uploadMeditates.get(position).getMeditateImage()).putExtra("id",uploadMeditates.get(position).getMediateId()).putExtra("uri",uploadMeditates.get(position).getUrl());
                myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(myactivity);
            }
        });

    }


    @Override
    public int getItemCount() {
        return uploadMeditates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView songTitle;
        TextView songId;
        TextView songUrl;
        ImageView deleteMeditate,editMeditation;
        MusicAdapter.OnNoteListner onNoteListner;


        public ViewHolder(@NonNull View itemView, MusicAdapter.OnNoteListner onNoteListner) {
            super(itemView);
            imageView = itemView.findViewById(R.id.musicImage);
            songId = itemView.findViewById(R.id.musicId);
            songTitle = itemView.findViewById(R.id.musicTitle);
            songUrl=itemView.findViewById(R.id.url);
            editMeditation=itemView.findViewById(R.id.editMeditation);
            deleteMeditate=itemView.findViewById(R.id.deleteMeditate);
            this.onNoteListner=onNoteListner;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onNoteListner.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListner {
        void onNoteClick(int position);
    }

}


