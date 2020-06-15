package com.weather.myweatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

class SettingsLocation extends AppCompatActivity {

    EditText editTextZip;
    TextView currentTextZip;
    String newZip;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_location);

        editTextZip = findViewById(R.id.editTextZip1);
        currentTextZip = findViewById(R.id.showCurrentZip);

        newZip = editTextZip.toString();

        Intent intent = getIntent();
        currentTextZip.setText(intent.getStringExtra("zip_code"));
    }

    public void updateZip(View v){
        Intent intent = getIntent();
        intent.putExtra("zip_code", newZip);
    }

}
