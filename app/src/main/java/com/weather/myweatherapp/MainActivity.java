package com.weather.myweatherapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText editTextZip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextZip = findViewById(R.id.editTextZip);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWeatherView(view);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.action_display:
                showDisplay();
                return true;
            case R.id.action_location:
                showLocation();
                return true;
            case R.id.action_about:
                showAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void showDisplay(){
        Intent intent = new Intent(this, SettingsDisplay.class);
        startActivity(intent);
    }

    private void showLocation(){
        Intent intent = new Intent(this, SettingsLocation.class);
        startActivity(intent);
    }

    private void showAbout(){
        Intent intent = new Intent(this, SettingsAbout.class);
        startActivity(intent);
    }

    public void openWeatherView(View v) {
        String zCode = editTextZip.getText().toString();
        Intent intent = new Intent(this, WeatherView.class);
        intent.putExtra("zip_code", zCode);
        startActivity(intent);
    }
}
