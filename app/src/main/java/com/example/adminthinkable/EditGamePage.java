package com.example.adminthinkable;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.adminthinkable.Model.UploadGame;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class EditGamePage extends AppCompatActivity {
    EditText gameId,gameName;
    ImageView gameImage;
    Uri imageUri;
    Uri imageUrl;
    AppCompatButton updateImage, updateGame;
    String name;
    String image;
    String id;
    String gameNameUpdated,gameIdUpdated,gameImageUpdated;
    DatabaseReference reference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_edit_game_page);
        gameId=findViewById(R.id.gameId);
        gameName=findViewById(R.id.gameTitle);
        gameImage=findViewById(R.id.songImage);
        updateImage=findViewById(R.id.uploadImage);
        updateGame=findViewById(R.id.updateGame);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            id = extras.getString("id");
            image = extras.getString("image");

            gameId.setHint(id);
            gameName.setHint(name);
            Picasso.get().load(image).into(gameImage);

        }

        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");

                ImageResultLauncher.launch(galleryIntent);
            }
        });


        updateGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameNameUpdated = gameName.getText().toString();
                gameIdUpdated = gameId.getText().toString();
                gameImageUpdated = String.valueOf(imageUrl);

                if (gameNameUpdated.isEmpty()) {
                    gameNameUpdated = gameName.getHint().toString();
                }
                if (gameIdUpdated.isEmpty()) {
                    gameIdUpdated = gameId.getHint().toString();
                }
                if(gameImageUpdated==null){
                    gameImageUpdated=image;
                    return;
                }
//
                HashMap<String, Object> ID = new HashMap<>();

                if(imageUrl==null){
                    gameImageUpdated=image;
                }
                ID.put("gameImage", gameImageUpdated);
                Log.d("UpdateImageFinal",gameImageUpdated);
                ID.put("gameId", gameIdUpdated);
                ID.put("gameName", gameNameUpdated);

                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Games_Admin");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            UploadGame post = dataSnapshot.getValue(UploadGame.class);
                            if (post.getGameId().equals(id)) {
                                reference1 = reference.child(dataSnapshot.getRef().getKey());
                                Log.d("ReferencePath", String.valueOf(reference1));
                                reference1.updateChildren(ID).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(EditGamePage.this,"Successful",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
    ActivityResultLauncher<Intent> ImageResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();

                        imageUri = data.getData();
                        if (imageUri != null) {
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference(id);
                            StorageReference fileRef = storageReference.child("musicImage.jpg");
                            fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri imageUriUpload) {
                                            Picasso.get().load(imageUriUpload).into(gameImage);
                                            imageUrl = imageUriUpload;
                                            Log.d("Url", imageUrl.toString());
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditGamePage.this, "Failed Uploading Image...", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(EditGamePage.this,"No file Selected to uploads",Toast.LENGTH_SHORT).show();
                            imageUrl = Uri.parse(image);
                            Log.d("Image", image);
                        }

//
                        gameImage.setImageURI(imageUri);


                    }
                }
            });
}