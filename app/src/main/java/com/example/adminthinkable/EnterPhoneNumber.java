package com.example.adminthinkable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class EnterPhoneNumber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Spinner spinner;
        EditText editText;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_phone_number);


        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        editText = findViewById(R.id.editTextPhone);

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

                String number = editText.getText().toString().trim();

                if (number.isEmpty() || number.length() < 9) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }

                String phoneNumber = "+" + code + number;

                Intent intent = new Intent(EnterPhoneNumber.this, VerifyPhoneActivity.class);
                intent.putExtra("phonenumber", phoneNumber);
                startActivity(intent);

            }
        });
    }


    public void continuetocode(View view) {
        Intent intentContinuetocode = new Intent(EnterPhoneNumber.this, VerifyPhoneActivity.class);

        startActivity(intentContinuetocode);
    }


}