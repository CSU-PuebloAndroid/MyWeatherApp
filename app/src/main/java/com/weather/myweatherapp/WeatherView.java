package com.weather.myweatherapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherView extends AppCompatActivity {
    TextView textViewTemp, textViewDesc, textViewWind, textViewPressure, textViewHumidity, textViewSunrise, textViewSunset, textViewRec;
    String ZIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewTemp = findViewById(R.id.textViewTemp);
        textViewDesc = findViewById(R.id.textViewDesc);
        textViewWind = findViewById(R.id.textViewWind);
        textViewPressure = findViewById(R.id.textViewPressure);
        textViewHumidity = findViewById(R.id.textViewHumidity);
        textViewSunrise = findViewById(R.id.textViewSunrise);
        textViewSunset = findViewById(R.id.textViewSunset);

        Intent intent = getIntent();
        ZIP = intent.getStringExtra("zip_code");

        search();

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class Weather extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... address) {

            try {
                URL url = new URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int data = isr.read();
                String content = "";
                char ch;

                while (data != -1) {
                    ch = (char) data;
                    content = content + ch;
                    data = isr.read();
                }
                return content;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public void search() {

        String content;
        Weather weather = new Weather();

        try {
            content = weather.execute("https://api.openweathermap.org/data/2.5/weather?zip=" + ZIP + "&units=imperial&appid=243181514c9a174184b04802648430a1").get();
            Log.i("content",content);

            JSONObject jsonObject = new JSONObject(content);
            String weatherInfo = jsonObject.getString("weather");
            String wMain = jsonObject.getString("main");
            String wWind = jsonObject.getString("wind");
            String wSys = jsonObject.getString("sys");

            String main = "";
            String description = "";
            String temperature;
            String wind;
            String pressure;
            String humidity;
            Long sunrise;
            Long sunset;

            JSONArray array = new JSONArray(weatherInfo);

            for(int i=0; i<array.length(); i++) {
                JSONObject weatherPart = array.getJSONObject(i);
                main = weatherPart.getString("main");
                description = weatherPart.getString("description");
            }

            JSONObject mainPart = new JSONObject(wMain);
            temperature = mainPart.getString("temp");
            pressure = mainPart.getString("pressure");
            humidity = mainPart.getString("humidity");


            JSONObject windPart = new JSONObject(wWind);
            wind = windPart.getString("speed");

            JSONObject sysPart = new JSONObject(wSys);
            sunrise = sysPart.getLong("sunrise");
            sunset = sysPart.getLong("sunset");

            textViewTemp.setText(temperature + " °F");
            textViewDesc.setText(description);
            textViewWind.setText("Wind: " + wind + " mph");
            textViewPressure.setText("Pressure: " + pressure + " MB");
            textViewHumidity.setText("Humidity: " + humidity + "%");
            textViewSunrise.setText("Sunrise: " + new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
            textViewSunset.setText("Sunset: " + new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
