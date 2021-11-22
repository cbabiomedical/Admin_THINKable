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
import com.example.adminthinkable.Model.UploadGame;
import com.example.adminthinkable.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    private Context context;
    private ArrayList<UploadGame> uploadGames;
    DatabaseReference reference1;

    public GameAdapter(Context context, ArrayList<UploadGame> uploadGames) {
        this.context = context;
        this.uploadGames = uploadGames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_upload_game,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(uploadGames.get(position).getGameImage()).into(holder.gameImage);
        holder.gameTitle.setText(uploadGames.get(position).gameName);
        holder.gameId.setText(uploadGames.get(position).gameId);
        holder.deletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Games_Admin");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            UploadGame post=dataSnapshot.getValue(UploadGame.class);
                            if(post.getGameId().equals(uploadGames.get(position).getGameId())){
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

        holder.editGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myactivity = new Intent(context.getApplicationContext(), EditGamePage.class).putExtra("name",uploadGames.get(position).getGameName()).putExtra("image",uploadGames.get(position).getGameImage()).putExtra("id",uploadGames.get(position).getGameId());
                myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(myactivity);
            }
        });

    }

    @Override
    public int getItemCount() {
        return uploadGames.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        ImageView gameImage;
        TextView gameTitle;
        TextView gameId;
        ImageView deletBtn;
        ImageView editGame;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gameId=itemView.findViewById(R.id.gameId);
            gameImage=itemView.findViewById(R.id.gameImage);
            gameTitle=itemView.findViewById(R.id.gameTitle);
            deletBtn=itemView.findViewById(R.id.deleteGame);
            editGame=itemView.findViewById(R.id.editGame);
        }
    }
}
