package com.example.adminthinkable.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminthinkable.EditGamePage;
import com.example.adminthinkable.EditVideo;
import com.example.adminthinkable.Model.UploadGame;
import com.example.adminthinkable.Model.UploadVideo;
import com.example.adminthinkable.R;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private Context context;
    private ArrayList<UploadVideo> uploadVideos;
    DatabaseReference reference1;

    public VideoAdapter(Context context, ArrayList<UploadVideo> uploadVideos) {
        this.context = context;
        this.uploadVideos = uploadVideos;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_upload_video, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(uploadVideos.get(position).getVideoImage()).into(holder.videoImage);
        holder.videoTitle.setText(uploadVideos.get(position).getVideoName());
        holder.videoId.setText(uploadVideos.get(position).getVideoId());
        holder.editVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myactivity = new Intent(context.getApplicationContext(), EditVideo.class).putExtra("name",uploadVideos.get(position).getVideoName()).putExtra("image",uploadVideos.get(position).getVideoImage()).putExtra("id",uploadVideos.get(position).getVideoId()).putExtra("uri",uploadVideos.get(position).videoUrl);
                myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(myactivity);
            }
        });

    }

    @Override
    public int getItemCount() {
        return uploadVideos.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView videoImage;
        TextView videoTitle;
        TextView videoId;
        ImageView editVideo,deleteVideo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videoImage=itemView.findViewById(R.id.videoImage);
            videoTitle=itemView.findViewById(R.id.videoTitle);
            videoId=itemView.findViewById(R.id.videoId);
            editVideo=itemView.findViewById(R.id.editVideo);
            deleteVideo=itemView.findViewById(R.id.deleteVideo);
        }
    }


}
