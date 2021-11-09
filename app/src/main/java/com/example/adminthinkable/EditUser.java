package com.example.adminthinkable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditUser extends AppCompatActivity {

    FirebaseUser mUser;
    CircleImageView profilePicture;
    AutoCompleteTextView occupation;
    EditText username, emailAddress, dateOfBirth;
    AppCompatButton update;
    RadioButton male, female;
    String gender = "";
    AppCompatImageView camera;
    private DatePickerDialog datePickerDialog;
    private EditText dateButton;

    DatabaseReference reference;
    ImageView occupationSelected;
    FirebaseAuth auth;
    private Uri imageUri;
    private String myUri = "";
    private StorageReference storageProfilePicRif;
    private StorageTask uploadTask;
    boolean isMale;
    boolean isFemale;
    ArrayAdapter<String> arrayAdapter_season;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_user);
        profilePicture = findViewById(R.id.profilePic);
        username = findViewById(R.id.userName);
        emailAddress = findViewById(R.id.email);

        update = findViewById(R.id.update);

        camera=findViewById(R.id.iv_camera);


        //Setting date text


        mUser = FirebaseAuth.getInstance().getCurrentUser();
        readData();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
                if(imageUri!=null) {
                    uploadProfileImage(imageUri);
                }
            }
        });
        auth=FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference().child("Users");
        storageProfilePicRif= FirebaseStorage.getInstance().getReference().child(mUser.getUid());
        StorageReference profileRef=storageProfilePicRif.child("profilePic.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilePicture);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode== Activity.RESULT_OK){
                imageUri=data.getData();
                Log.d("Uri", String.valueOf(imageUri));
                profilePicture.setImageURI(imageUri);

            }
        }
    }

    private void uploadProfileImage( Uri imageUri){
        //Upload Image to Firebase Storage
        StorageReference fileRef=storageProfilePicRif.child("profilePic.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profilePicture);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditUser.this,"Failed Uploading Image...",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void readData() {
        reference = FirebaseDatabase.getInstance().getReference("Admins");
        reference.child(mUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        Toast.makeText(EditUser.this, "Successfully Read", Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        username.setHint(String.valueOf(dataSnapshot.child("userName").getValue()));
                        emailAddress.setHint(String.valueOf(dataSnapshot.child("email").getValue()));


                    } else {
                        Toast.makeText(EditUser.this, "User does not exist", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(EditUser.this, "Read Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void updateData() {
        String userName = username.getText().toString();
        String email = emailAddress.getText().toString();
        Log.d("username",userName);
        Log.d("Email",email);

        if (userName.isEmpty()) {
            userName = username.getHint().toString();

        }
        if (email.isEmpty()) {
            email = emailAddress.getHint().toString();

        }


        HashMap<String, Object> User = new HashMap();
        User.put("userName", userName);
        User.put("email", email);


        reference.child(mUser.getUid()).updateChildren(User).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditUser.this, "User Details Updated Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditUser.this, "Failed Updating User Details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}