package com.example.adminthinkable;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminthinkable.Model.UploadGame;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class Game extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener{

    TextView textViewImageg;
    ProgressBar progressBarg;
    Uri audioUrig, imageUrig;
    Spinner spinner;
    StorageReference mStoragerefg;
    StorageTask mUploadsTaskg;
    DatabaseReference referenceSongsg;
    ImageView editGame;
    String songsCategory;
    MediaMetadataRetriever metadataRetrieverg;
    byte[] art;
    String title1, artist1, album_art1 = "", duration1;
    TextView titleg, artist, durations, album, dataa;
    ImageView songimageg;
    Button uploadBtn, showAllBtn;
    EditText Enteridg, EnterNameg;
//    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Songs");
//    private StorageReference reference = FirebaseStorage.getInstance().getReference().child("Songs");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);


        progressBarg = findViewById(R.id.progressbarg);

//       uploadBtn=findViewById(R.gameId.openAImageFiles);
        Enteridg = findViewById(R.id.enteridg);
        songimageg = findViewById(R.id.songimageg);
        EnterNameg = findViewById(R.id.enternameg);
        editGame = findViewById(R.id.editGame);

        spinner = findViewById(R.id.spinnerCategories);

//        artist=findViewById(R.gameId.artist);
//        durations=findViewById(R.gameId.duration);
//        album=findViewById(R.gameId.album);
//        dataa=findViewById(R.gameId.dataa);
//        album_art=findViewById(R.gameId.imageview);

        metadataRetrieverg = new MediaMetadataRetriever();

        mStoragerefg = FirebaseStorage.getInstance().getReference().child("Games_Admin");


        spinner.setOnItemSelectedListener(this);
//
        List<String> catrgories = new ArrayList<>();
//
        catrgories.add("Concentration");
        catrgories.add("Relaxation");
        catrgories.add("Memory");

//
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,catrgories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        editGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DisplayUploadedGames.class));
            }
        });

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


        songimageg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");

                ImageResultLauncherg.launch(galleryIntent);
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


    private String getFileExtention(Uri muri) {
        ContentResolver crg = getContentResolver();
        MimeTypeMap mimeg = MimeTypeMap.getSingleton();
        return mimeg.getExtensionFromMimeType(crg.getType(muri));
    }


    ActivityResultLauncher<Intent> ImageResultLauncherg = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();

                        imageUrig = data.getData();
//
                        songimageg.setImageURI(imageUrig);


                    }
                }
            });


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


    public void uploadFileTofirebaseg(View v) {


        if (mUploadsTaskg != null && mUploadsTaskg.isInProgress()) {
            Toast.makeText(this, "games uploads in already progress !", Toast.LENGTH_SHORT);
        } else {
            uploadFilesg();
        }

    }

    private void uploadFilesg() {


        if (songsCategory.equals("Concentration")) {

            referenceSongsg = FirebaseDatabase.getInstance().getReference("Games_Admin").child("Games_Concentration");}
        else if (songsCategory.equals("Relaxation")){

            referenceSongsg = FirebaseDatabase.getInstance().getReference("Games_Admin").child("Games_Relaxation");


        }
        else if(songsCategory.equals("Memory")){
            referenceSongsg = FirebaseDatabase.getInstance().getReference("Games_Admin").child("Games_Memory");


        }





        if (imageUrig != null) {
            Toast.makeText(this, "Upload please wait !", Toast.LENGTH_SHORT);
            progressBarg.setVisibility(View.VISIBLE);
            final StorageReference storageReferenceg = mStoragerefg.child(System.currentTimeMillis() + "." + getfileextension(imageUrig));
            mUploadsTaskg = storageReferenceg.putFile(imageUrig).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReferenceg.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            UploadGame uploadGame = new UploadGame(uri.toString(), EnterNameg.getText().toString(), Enteridg.getText().toString(),songsCategory);
                            String uploadIdg = referenceSongsg.push().getKey();
                            referenceSongsg.child(uploadIdg).setValue(uploadGame);


                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressBarg.setProgress((int) progress);
                }
            });


        } else {
            Toast.makeText(this, "No file Selected to uploads", Toast.LENGTH_SHORT).show();
        }


    }

    private String getfileextension(Uri imageUrig) {
        ContentResolver contentResolverm = getContentResolver();
        MimeTypeMap mimeTypeMapm = MimeTypeMap.getSingleton();
        return mimeTypeMapm.getExtensionFromMimeType(contentResolverm.getType(imageUrig));

    }


//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//    }

    public void openImageFiles(View view) {
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        songsCategory = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this,"Selected"+songsCategory, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}