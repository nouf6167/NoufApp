package com.nouf.noufapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class WeatherReportActivity extends AppCompatActivity {

    private Context context;
    private Toolbar toolbar;
    private TextInputEditText searchWeatherBox;
    private MaterialButton searchBtn;
    private MaterialTextView dummyTextView;
    DecimalFormat df = new DecimalFormat("#.##");
    AppSettings appSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_report);

        initViews();
        setUpToolbar();

    }

    private void initViews(){
        context = this;
        appSettings = new AppSettings(context);
        toolbar = findViewById(R.id.toolbar);
        searchWeatherBox = findViewById(R.id.search_city_box);
        searchBtn = findViewById(R.id.search_weather_btn);
        dummyTextView = findViewById(R.id.dummy_textview);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!searchWeatherBox.getText().toString().trim().isEmpty()){
                    getWeatherReport(searchWeatherBox.getText().toString().trim());
                }
            }
        });
        String weatherData = appSettings.getString("WEATHER_DATA");
        if (weatherData.isEmpty()){
            getWeatherReport("Barlin");
        }
        else{
            displayWeatherData(weatherData);
        }

    }

    private void displayWeatherData(String weatherData) {
        StringBuilder output = new StringBuilder();
        try {
            JSONObject jsonResponse = new JSONObject(weatherData);
            JSONArray jsonArray = jsonResponse.getJSONArray("weather");
            JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
            String description = jsonObjectWeather.getString("description");
            JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
            double temp = jsonObjectMain.getDouble("temp") - 273.15;
            double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
            float pressure = jsonObjectMain.getInt("pressure");
            int humidity = jsonObjectMain.getInt("humidity");
            JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
            String wind = jsonObjectWind.getString("speed");
            JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
            String clouds = jsonObjectClouds.getString("all");
            JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
            String countryName = jsonObjectSys.getString("country");
            String cityName = jsonResponse.getString("name");
            dummyTextView.setTextColor(Color.rgb(68,134,199));
            output.append("Current weather of " + cityName + " (" + countryName + ")");
            output.append("\n Temp: " + df.format(temp) + " °C");
            output.append("\n Humidity: " + humidity + "%");
            output.append("\n Cloudiness: " + clouds + "%");
            dummyTextView.setText(output);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);
            getSupportActionBar().setTitle("Weather Report");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    private void getWeatherReport(String query){

        String url = "http://api.openweathermap.org/data/2.5/weather?q="+query+"&APPID=f361743f41882b96ecac0d7289112117";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                appSettings.putString("WEATHER_DATA",response);
                displayWeatherData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TEST199",error.getLocalizedMessage());
            }
        });
      VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}