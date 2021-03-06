package com.example.adminthinkable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminthinkable.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signin extends AppCompatActivity implements View.OnClickListener {



    TextView forgotPassword;
    EditText emailAddress, passwordTxt;
    Button signIn;
    Button signUp;

    private FirebaseAuth mAuth;
    private ProgressBar progressBarSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_signin);

        //set onclick listener for signin button
        signIn = (Button) findViewById(R.id.signin);
        signIn.setOnClickListener(this);

        //set onclick listener for signup button
        signUp = (Button) findViewById(R.id.signinsup);
        signUp.setOnClickListener(this);

        emailAddress = (EditText) findViewById(R.id.Signinemail);
        passwordTxt = (EditText) findViewById(R.id.signinpw);

        progressBarSignin = (ProgressBar) findViewById(R.id.progressBarSignin);

        //firebase authentication instance
        mAuth = FirebaseAuth.getInstance();

        //set onclick listener for forgotpassword button
        forgotPassword = findViewById(R.id.forgetpw);
        forgotPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signinsup:
                //go to sign up page
                startActivity(new Intent(this, Register.class));
                break;

            case R.id.signin:
//                Log.d("USERLOGIN", "----------------------H----------------------------");
//                Log.d("USERLOGIN", "----------------------I----------------------------");
//                Log.d("USERLOGIN", "----------------------J----------------------------");
                userLogin();
                break;

            case R.id.forgetpw:
                //go to forget password page
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
        }
    }

    //get & display current user's profile
    @Override
    protected void onStart() {
        super.onStart();

        //signin authentication and redirect to landing page
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, Landing.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    }

    //get user credentials & convert it back to string

    private void userLogin() {
        //get user email and password
        String email = emailAddress.getText().toString().trim();
        String password = passwordTxt.getText().toString().trim();

        //check email is entered
        if (email.isEmpty()) {
            emailAddress.setError("Email is required");
            emailAddress.requestFocus();
            return;
        }
        //check whether email is valid
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailAddress.setError("Enter a valid email!");
            emailAddress.requestFocus();
            return;
        }
        //check whether password is entered
        if (password.isEmpty()) {
            passwordTxt.setError("Password is Required");
            passwordTxt.requestFocus();
            return;
        }
        //check passoword length is more than 8 characters
        if (password.length() < 8) {
            passwordTxt.setError("Minimum Password length should be 8 characters!");
            passwordTxt.requestFocus();
            return;
        }

        Log.d("USERLOGIN", "----------------------A----------------------------");
        Log.d("USERLOGIN", "----------------------B----------------------------");
        Log.d("USERLOGIN", "----------------------C----------------------------");
        progressBarSignin.setVisibility(View.GONE);
        //signin with email and password
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

//                    Log.d("USERLOGIN", "----------------------E----------------------------");
//                    Log.d("USERLOGIN", "----------------------F----------------------------");
//                    Log.d("USERLOGIN", "----------------------G----------------------------");
                    //check whether user details are correct and authenticate with firebase userdata and redirect user to landing page
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    startActivity(new Intent(Signin.this, Landing.class));

                     /*if (user.isEmailVerified()) {
                        // redirect to user profile
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    }
                    else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this,"Check your email to verify your account",Toast.LENGTH_LONG)
                                .show();
                    }*/

                } else {
                    Toast.makeText(Signin.this, "LogIn Failed! Please check your username or password again", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });


    }
}
