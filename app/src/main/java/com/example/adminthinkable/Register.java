package com.example.adminthinkable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private EditText username, emailAddress, userPassword, re_enterPassword;
    private DatePickerDialog datePickerDialog;
    TextView signIn;
    private EditText dateButton;
    Button signUp;
    ImageView occupation;
    private RadioButton male;
    private RadioButton female;
    private String gender = "";
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    AutoCompleteTextView act;
    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuthggl;


    ArrayAdapter<String> arrayAdapter_season;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // connecting backend variables to front end components
        username = findViewById(R.id.username);
        emailAddress = findViewById(R.id.email);
        userPassword = findViewById(R.id.password);
        re_enterPassword = findViewById(R.id.repassword);
        signUp = findViewById(R.id.signUp);
        progressBar = findViewById(R.id.progressBar);




        //onClick function of occupation dropdown


        // Accessing location to read or write data in firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Admins");
        //Getting an instance of firebase Auth class
        firebaseAuth = FirebaseAuth.getInstance();

        //onClick Function of Sign in TextView

        // onClick Function of Sign up button
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calling registerUser function
                registerUser();
            }

        });


    }





    // Creating register user method
    private void registerUser() {
        // Storing input received from user in edit text inside String variables
        String userName = username.getText().toString().trim();
        String email = emailAddress.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        String reEnter = re_enterPassword.getText().toString().trim();

        // notifying user if username field is empty
        if (userName.isEmpty()) {
            username.setError("Full Name is Required");
            username.requestFocus();
            return;
        }
        // notifying user if email field is empty
        if (email.isEmpty()) {
            emailAddress.setError("Email is Required");
            emailAddress.requestFocus();
            return;
        }
        // notifying user if email entered is not in email address format
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailAddress.setError("Email is Invalid");
            emailAddress.requestFocus();
            return;
        }
        // Notifying user if password field is empty
        if (password.isEmpty()) {
            userPassword.setError("Password is Required");
            userPassword.requestFocus();
            return;
        }
        // Notifying user if password has less than 8 characters
        if (password.length() < 8) {
            userPassword.setError("Minimum Password length should be 8 characters!");
            userPassword.requestFocus();
            return;
        }
        // Notifying user if reEnter password is empty
        if (reEnter.isEmpty()) {
            re_enterPassword.setError("Re_ Enter Password");
            re_enterPassword.requestFocus();
            return;
        }
        // Notifying user if password entered and re entered password does not match
        if (!password.equals(reEnter)) {
            re_enterPassword.setError("Password does not match");
            re_enterPassword.requestFocus();
            return;
        }
        // setting progress bar as visible for authentication time
        progressBar.setVisibility(View.VISIBLE);
        // Creating a new User account in firebase with user's email and password
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Creating user object and passing user input as parameters
                    User user = new User(
                            userName,
                            email

                    );
                    // getting an instance of firebase database using getInstance()
                    // accessign the location in database to write data
                    FirebaseDatabase.getInstance().getReference("Admins")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //If task complete navigating from Register Activity to Suggestions Activity
                                        Intent intentveri = new Intent(Register.this, Signin.class);
                                        startActivity(intentveri);
                                        // Display Toast message "Registration successful"
                                        Toast.makeText(Register.this, "Registration Complete", Toast.LENGTH_SHORT).show();

                                    } else {
                                        // Display Toast message "Registration failed" if error occurs
                                        Toast.makeText(Register.this, "Registration Unsuccessful. Try Again!", Toast.LENGTH_LONG)
                                                .show();
                                    }
                                    // Setting visibility of progress bar once the registration function is complete
                                    progressBar.setVisibility(View.GONE);

                                }
                            });
                }
            }
        });
    }

    public void gotophone(View view) {
        Intent intentVerifyNum2 = new Intent(Register.this, EnterPhoneNumber.class);
        startActivity(intentVerifyNum2);
    }


//    public void gotoOTPthruRegister(View view) {
//        Intent intentVerifyNum2 = new Intent(RegisterActivity.this, VerifyPhoneActivity.class);
//        startActivity(intentVerifyNum2);
//        Log.d("war","Clicked");
//    }
}