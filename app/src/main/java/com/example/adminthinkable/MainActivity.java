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
import com.example.adminthinkable.Model.UploadSong;
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


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    TextView textViewImage;
    ProgressBar progressBar;
    Uri audioUri,imageUri;
    StorageReference mStorageref;
    StorageTask mUploadsTask;
    DatabaseReference referenceSongs;
    ImageView editSong;
    String songsCategory;
    Uri imageUrl;
    MediaMetadataRetriever metadataRetriever;
    byte []art;
    String title1,artist1,album_art1 = "",duration1;
    TextView title,artist,durations,album,dataa;
    ImageView songimage;
    Button uploadBtn,showAllBtn;
    EditText Enterid,EnterName;
//    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Songs");
//    private StorageReference reference = FirebaseStorage.getInstance().getReference().child("Songs");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewImage=findViewById(R.id.textViewSongsFilesSelected);
        progressBar=findViewById(R.id.progressbar);
        title=findViewById(R.id.title);
//        uploadBtn=findViewById(R.gameId.openAImageFiles);
        Enterid=findViewById(R.id.enterid);
        songimage = findViewById(R.id.songimage);
        EnterName=findViewById(R.id.entername);
        editSong=findViewById(R.id.editSgs);
//        artist=findViewById(R.gameId.artist);
//        durations=findViewById(R.gameId.duration);
//        album=findViewById(R.gameId.album);
//        dataa=findViewById(R.gameId.dataa);
//        album_art=findViewById(R.gameId.imageview);

        metadataRetriever = new MediaMetadataRetriever();
        referenceSongs = FirebaseDatabase.getInstance().getReference().child("Songs_Admin");
        mStorageref= FirebaseStorage.getInstance().getReference().child("Songs_Admin");

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


        songimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");

                ImageResultLauncher.launch(galleryIntent);
            }
        });
        editSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DisplayUploadedSongs.class));
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
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(muri));
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
//
                        songimage.setImageURI(imageUri);


                    }
                }
            });


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        songsCategory = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this,"Selected"+songsCategory, Toast.LENGTH_SHORT).show();



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

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
                        textViewImage.setText(fileNames);


//                        titlem.setText(metadataRetrieverm.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
//                        title1=metadataRetrieverm.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

                    }
                }
            });

    public void openAudioFiles(View v) {
        Intent i = new Intent (Intent.ACTION_GET_CONTENT);
        i.setType("audio/*");
        someActivityResultLauncher.launch(i);
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
    public void uploadFileTofirebase(View v){
        if(textViewImage.equals("No files selected")){
            Toast.makeText(this,"please selected an image",Toast.LENGTH_SHORT).show();

        }
        else{
            if (mUploadsTask != null && mUploadsTask.isInProgress()){
                Toast.makeText(this,"songs uploads in already progress !",Toast.LENGTH_SHORT);
            }else{
                uploadFiles();
            }
        }
    }
    private void uploadFiles(){
        StorageReference storageReference=FirebaseStorage.getInstance().getReference(Enterid.getText().toString());
        StorageReference fileRef = storageReference.child("musicImage.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri imageUriUpload) {
                        Picasso.get().load(imageUriUpload).into(songimage);
                        imageUrl=imageUriUpload;
                        Log.d("Url", String.valueOf(imageUriUpload));
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed Uploading Image...", Toast.LENGTH_SHORT).show();
            }
        });


        if(audioUri != null){
            Toast.makeText(this,"Upload please wait !",Toast.LENGTH_SHORT);
            progressBar.setVisibility(View.VISIBLE);
            final StorageReference storageReference1 = mStorageref.child(System.currentTimeMillis()+"."+getfileextension(audioUri));
            mUploadsTask=storageReference.putFile(audioUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            UploadSong uploadSong = new UploadSong(uri.toString(),imageUrl.toString(),Enterid.getText().toString(),EnterName.getText().toString());
                            Log.d("ImagewUrl",imageUrl.toString());
                            String uploadId= referenceSongs.push().getKey();
                            Log.d("UploadId",referenceSongs.push().getKey());
                            referenceSongs.child(uploadId).setValue(uploadSong);



                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0* snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    progressBar.setProgress((int) progress);
                }
            });


        }else{
            Toast.makeText(this,"No file Selected to uploads",Toast.LENGTH_SHORT).show();
        }




    }

    private String getfileextension(Uri audioUri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(audioUri));

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void openImageFiles(View view) {
    }
}