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
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminthinkable.Model.UploadMeditate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Meditate extends AppCompatActivity {

    TextView textViewImagem;
    ProgressBar progressBarm;
    Uri audioUrim, imageUrim;
    StorageReference mStoragerefm;
    Uri imageUrl;
    StorageTask mUploadsTaskm;
    DatabaseReference referenceSongsm;
    String songsCategorym;
    MediaMetadataRetriever metadataRetrieverm;
    byte []art;
    String title1,artist1,album_art1 = "",duration1;
    TextView titlem,artist,durations,album,dataa;
    ImageView songimagem;
    Button uploadBtn,showAllBtn;
    EditText Enteridm, EnterNamem;
    ImageView editmeditation;
//    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Songs");
//    private StorageReference reference = FirebaseStorage.getInstance().getReference().child("Songs");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditate);

        textViewImagem =findViewById(R.id.textViewSongsFilesSelectedm);
        progressBarm =findViewById(R.id.progressbarm);
//        titlem =findViewById(R.gameId.titlem);
//        uploadBtn=findViewById(R.gameId.openAImageFiles);
        Enteridm =findViewById(R.id.enteridm);
        songimagem = findViewById(R.id.songimagem);
        EnterNamem =findViewById(R.id.enternamem);
        editmeditation=findViewById(R.id.editMeditation);
//        artist=findViewById(R.gameId.artist);
//        durations=findViewById(R.gameId.duration);
//        album=findViewById(R.gameId.album);
//        dataa=findViewById(R.gameId.dataa);
//        album_art=findViewById(R.gameId.imageview);

        metadataRetrieverm = new MediaMetadataRetriever();
        referenceSongsm = FirebaseDatabase.getInstance().getReference().child("Meditation_Admin");
        mStoragerefm = FirebaseStorage.getInstance().getReference().child("Meditation_Admin");

//        Spinner spinner = findViewById(R.gameId.spinner);
//
//        spinner.setOnItemSelectedListener(this);
//
//        List<String> catrgories = new ArrayList<>();
//
//        catrgories.add("Love songs");
//        catrgories.add("Sad songs");
//        catrgories.add("Party songs");
//        catrgories.add("Birthday songs");
//        catrgories.add("God songs");
//
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,catrgories);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(dataAdapter);

        editmeditation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DisplayMeditateSongs.class));
            }
        });
        songimagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");

                ImageResultLauncherm.launch(galleryIntent);
            }
        });

//        uploadBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (imageUrim != null){
//                    uploadToFirebase(imageUrim);
//
//
//                }else{
//                    Toast.makeText(MainActivity.this,"Please Select Image",Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });

    }



    private String getFileExtention(Uri muri){
        ContentResolver crm = getContentResolver();
        MimeTypeMap mimem = MimeTypeMap.getSingleton();
        return mimem.getExtensionFromMimeType(crm.getType(muri));
    }




    ActivityResultLauncher<Intent> ImageResultLauncherm = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();

                        imageUrim = data.getData();
//
                        songimagem.setImageURI(imageUrim);


                    }
                }
            });




    ActivityResultLauncher<Intent> someActivityResultLauncherm = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        audioUrim = data.getData();
                        // metadataRetrieverm.setDataSource(this,audioUrim);
                        String fileNames = getFileName(audioUrim);
                        textViewImagem.setText(fileNames);


//                        titlem.setText(metadataRetrieverm.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
//                        title1=metadataRetrieverm.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

                    }
                }
            });

    public void openAudioFilesm(View v) {
        Intent i = new Intent (Intent.ACTION_GET_CONTENT);
        i.setType("audio/*");
        someActivityResultLauncherm.launch(i);
    }

//    public void openAudioFilesm(View v){
//        Intent i = new Intent (Intent.ACTION_GET_CONTENT);
//        i.setType("audio/*");
//        startActivityForResult(i, 101);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode ==101 && requestCode ==RESULT_OK && data.getData() != null){
//
//            audioUrim = data.getData();
//            String fileNames = getFileName(audioUrim);
//            textViewImagem.setText(fileNames);
//            metadataRetrieverm.setDataSource(this,audioUrim);
//
//
//
//            titlem.setText(metadataRetrieverm.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
//            title1=metadataRetrieverm.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
//
//        }
//
//    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")){
            Cursor cursor= getContentResolver().query(uri,null,null,null,null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                }
            }
            finally{
                cursor.close();
            }
        }

        if(result ==null ){
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if(cut != -1){
                result = result.substring(cut +1);
            }
        }
        return result;




    }
    public void uploadFileTofirebasem(View v){
        if(textViewImagem.equals("No files selected")){
            Toast.makeText(this,"please selected an image",Toast.LENGTH_SHORT).show();

        }
        else{
            if (mUploadsTaskm != null && mUploadsTaskm.isInProgress()){
                Toast.makeText(this,"Mp3 uploads in already progress !",Toast.LENGTH_SHORT);
            }else{
                uploadFilesm();
            }
        }
    }
    private void uploadFilesm(){
        StorageReference storageReference=FirebaseStorage.getInstance().getReference(Enteridm.getText().toString());
        StorageReference fileRef = storageReference.child("musicImage.jpg");
        fileRef.putFile(imageUrim).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri imageUriUpload) {
                        Picasso.get().load(imageUriUpload).into(songimagem);
                        imageUrl=imageUriUpload;
                        Log.d("Url", String.valueOf(imageUriUpload));
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Meditate.this, "Failed Uploading Image...", Toast.LENGTH_SHORT).show();
            }
        });



        if(audioUrim != null){
            Toast.makeText(this,"Upload please wait !",Toast.LENGTH_SHORT);
            progressBarm.setVisibility(View.VISIBLE);
            final StorageReference storageReferencem = mStoragerefm.child(System.currentTimeMillis()+"."+getfileextension(audioUrim));
            mUploadsTaskm = storageReferencem.putFile(audioUrim).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReferencem.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            UploadMeditate uploadMeditate = new UploadMeditate(uri.toString(),imageUrl.toString(), Enteridm.getText().toString(), EnterNamem.getText().toString());
                            String uploadIdm = referenceSongsm.push().getKey();
                            referenceSongsm.child(uploadIdm).setValue(uploadMeditate);



                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0* snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    progressBarm.setProgress((int) progress);
                }
            });


        }else{
            Toast.makeText(this,"No file Selected to uploads",Toast.LENGTH_SHORT).show();
        }




    }

    private String getfileextension(Uri audioUri){
        ContentResolver contentResolverm =getContentResolver();
        MimeTypeMap mimeTypeMapm =MimeTypeMap.getSingleton();
        return mimeTypeMapm.getExtensionFromMimeType(contentResolverm.getType(audioUri));

    }

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//    }

    public void openImageFiles(View view) {
    }
}