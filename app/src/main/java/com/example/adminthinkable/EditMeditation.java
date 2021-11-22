package com.example.adminthinkable;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminthinkable.Model.UploadMeditate;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class EditMeditation extends AppCompatActivity {
    EditText meditationId, meditationName;
    Uri audioUri, imageUri, selectedAudioUri;
    StorageTask mUploadsTask;
    TextView songUrl;
    AppCompatButton selectSongUrl, selectImageUrl, update;
    ImageView songImage;
    String uri;
    String name;
    ProgressBar progressBar;
    String image;
    Handler handler;
    String id;

    Uri imageUrl;
    DatabaseReference reference1;
    String songNameUpdated, songIdUpdated, songImageUpdated, songUrlUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meditation);


        meditationName = findViewById(R.id.meditationTitle);
        meditationId = findViewById(R.id.meditationId);
        songUrl = findViewById(R.id.textViewSongsFilesSelected);
        selectImageUrl = findViewById(R.id.uploadImage);
        progressBar = findViewById(R.id.progressbar);
        songImage = findViewById(R.id.songImage);
        selectSongUrl = findViewById(R.id.openAudioFiles);
        update = findViewById(R.id.updateMeditation);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            uri = extras.getString("uri");
            name = extras.getString("name");
            id = extras.getString("id");
            image = extras.getString("image");

            meditationId.setHint(id);
            songUrl.setText(uri);
            Log.d("Uri Text", String.valueOf(uri));
            meditationName.setHint(name);
            Picasso.get().load(image).into(songImage);

        }

        selectImageUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");

                ImageResultLauncher.launch(galleryIntent);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                songNameUpdated = meditationName.getText().toString();
                songIdUpdated = meditationId.getText().toString();
                songImageUpdated = String.valueOf(imageUrl);
                Log.d("UpdateImage",songImageUpdated);
                songUrlUpdated = String.valueOf(selectedAudioUri);
                Log.d("UpdateSongUrl",songUrlUpdated);

                // needs to be updated
                if (songNameUpdated.isEmpty()) {
                    songNameUpdated = meditationName.getHint().toString();
                }
                if (songIdUpdated.isEmpty()) {
                    songIdUpdated = meditationId.getHint().toString();
                }
                if(songImageUpdated==null){
                    update.setEnabled(false);
                    update.setError("No Image Selected");
                    return;
                }
                if(songUrlUpdated==null){
                    update.setEnabled(false);
                    update.setError("No Song Slected");
                    return;
                }
//                if (songImageUpdated.isEmpty()) {
//                    songImageUpdated = image;
//                    Log.d("UpdateImageNull",songImageUpdated);
//                }
//                if (songUrlUpdated.isEmpty()) {
//                   songUrlUpdated=uri;
//                    Log.d("UpdateSongUrlNull",songImageUpdated);
////
//                }
                HashMap<String, Object> ID = new HashMap<>();
                if(selectedAudioUri==null){
                    songUrlUpdated=uri;
                }
                ID.put("url", songUrlUpdated);
                Log.d("UpdateSongUrlFinal",songImageUpdated);
                if(imageUrl==null){
                    songImageUpdated=image;
                }
                ID.put("meditateImage", songImageUpdated);
                Log.d("UpdateImageFinal",songImageUpdated);
                ID.put("mediateId", songIdUpdated);
                ID.put("meditateName", songNameUpdated);

                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Meditation_Admin");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            UploadMeditate post = dataSnapshot.getValue(UploadMeditate.class);
                            if (post.getMediateId().equals(id)) {
                                reference1 = reference.child(dataSnapshot.getRef().getKey());
                                Log.d("ReferencePath", String.valueOf(reference1));
                                reference1.updateChildren(ID).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(EditMeditation.this,"Successful",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//
//                        Log.d("Reference", String.valueOf(reference));
//
////                reference.setValue(ID).addOnCompleteListener(new OnCompleteListener<Void>() {
////                    @Override
////                    public void onComplete(@NonNull Task<Void> task) {
////                        if(task.isSuccessful()){
////                            Toast.makeText(EditMusicPage.this,"Successful",Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                });
//                    }
//                }, 7000);
            }
        });

    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                }
            } finally {
                cursor.close();
            }
        }

        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;


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
                                            Picasso.get().load(imageUriUpload).into(songImage);
                                            imageUrl = imageUriUpload;
                                            Log.d("Url", imageUrl.toString());
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditMeditation.this, "Failed Uploading Image...", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(EditMeditation.this,"No file Selected to uploads",Toast.LENGTH_SHORT).show();
                            imageUrl = Uri.parse(image);
                            Log.d("Image", image);
                        }

//
                        songImage.setImageURI(imageUri);


                    }
                }
            });

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        audioUri = data.getData();
                        // metadataRetrieverm.setDataSource(this,audioUrim);
                        String fileNames = getFileName(audioUri);
                        songUrl.setText(fileNames);
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference(id);
                        StorageReference mStorageref = FirebaseStorage.getInstance().getReference().child("Songs_Admin");
                        if (audioUri != null) {
                            Toast.makeText(EditMeditation.this, "Upload please wait !", Toast.LENGTH_SHORT);
                            progressBar.setVisibility(View.VISIBLE);
                            final StorageReference storageReference1 = mStorageref.child(System.currentTimeMillis() + "." + getfileextension(audioUri));
                            mUploadsTask = storageReference.putFile(audioUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            selectedAudioUri = uri;
                                            Log.d("SelectedAudio", selectedAudioUri.toString());

                                        }
                                    });
                                }
                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                    progressBar.setProgress((int) progress);
                                }
                            });



                        }else{
                            Toast.makeText(EditMeditation.this,"No file Selected to uploads",Toast.LENGTH_SHORT).show();
                            selectedAudioUri=Uri.parse(uri);
                            Log.d("Song Url", songUrlUpdated);
                        }






//                        titlem.setText(metadataRetrieverm.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
//                        title1=metadataRetrieverm.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

                    }
                }
            });

    private String getfileextension(Uri audioUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(audioUri));

    }


    public void openAudioFiles(View view) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("audio/*");
        someActivityResultLauncher.launch(i);
    }
}