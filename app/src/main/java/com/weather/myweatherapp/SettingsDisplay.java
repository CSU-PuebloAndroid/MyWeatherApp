package com.weather.myweatherapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

class SettingsDisplay extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Switch darkMode;
    Spinner iconPack;
    String currentIconPack;
    ArrayAdapter<CharSequence> adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_display);

        darkMode = findViewById(R.id.switch2);


        darkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String darkModeStatus;
                if(darkMode.isChecked()){
                    //settings for Dark Mode On
                    darkModeStatus=darkMode.getTextOn().toString();
                    setDarkMode(true);

                }
                else{
                    //settings for Dark Mode Off
                    setDarkMode(false);
                    darkModeStatus=darkMode.getTextOff().toString();
                }
                Toast.makeText(getApplicationContext(), "Dark Mode: "+darkModeStatus, Toast.LENGTH_LONG).show();
            }
        });

        iconPack = findViewById(R.id.spinner1);
        iconPack.setOnItemSelectedListener(this);


    }

    private void setDarkMode(boolean b){

        if(b){
            //Icon Pack Options
            adapter = ArrayAdapter.createFromResource(this, R.array.dark_icon_pack, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            iconPack.setAdapter(adapter);
        }
        if(!b){
            //Icon Pack Options
            adapter = ArrayAdapter.createFromResource(this, R.array.light_icon_pack, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            iconPack.setAdapter(adapter);
        }
    }

    @Override
    public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
        currentIconPack = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: "+currentIconPack, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(android.widget.AdapterView<?> parent) {

    }
}



