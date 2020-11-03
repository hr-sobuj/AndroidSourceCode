package com.trianglelab.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView mTemparature,mWeatherStatus,mLocation,mHumity,mWind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTemparature=(TextView) findViewById(R.id.temparature);
        mWeatherStatus=(TextView) findViewById(R.id.weatherstatus);
        mLocation=(TextView) findViewById(R.id.location);
        mHumity=(TextView) findViewById(R.id.humidity);
        mWind=(TextView) findViewById(R.id.wind);

        makeJsonRequest();

    }

    public void makeJsonRequest(){
        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        String url = "https://api.openweathermap.org/data/2.5/weather?q=Pabna&appid=1c81badd61cac2d74f125a3121a86de6";

       /* ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();*/

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());

                        try {
                            String cityName=response.getString("name");

                            JSONObject MainData=response.getJSONObject("main");
                            double temperature = MainData.getDouble("temp");
                            String humidity = MainData.getString("humidity");

                            JSONObject WindData=response.getJSONObject("wind");
                            double wind=WindData.getDouble("speed");

                            JSONArray WeatherArray=response.getJSONArray("weather");
                            JSONObject weatherStatus=WeatherArray.getJSONObject(0);

                            String weather=weatherStatus.getString("main");

                            wind=(wind*3.6);
                            wind=roundTwoDecimals(wind);

                            temperature=(temperature-273.15);
                            int xtemperature=(int)(temperature);
                            mLocation.setText(cityName);
                            mTemparature.setText(""+xtemperature+"°C");
                            mHumity.setText("Humidity "+humidity+"%");
                            mWind.setText("Wind "+wind+" km/hr");
                            mWeatherStatus.setText(weather);
                            /*Log.e("CityName",cityName);
                            Toast.makeText(MainActivity.this,""+cityName,Toast.LENGTH_LONG).show();*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                            /*Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();*/
                        }
                        /*pDialog.hide();*/
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG error", "Error: " + error.getMessage());
                // hide the progress dialog
                /*pDialog.hide();*/
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    double roundTwoDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#.##");

        return Double.valueOf(twoDForm.format(d));
    }
    double roundOneDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#");

        return Double.valueOf(twoDForm.format(d));
    }
}