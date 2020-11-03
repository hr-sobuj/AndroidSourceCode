package com.trianglelab.weather;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView mTemparature, mWeatherStatus, mLocation, mHumity, mWind;
    private ImageView mIcon, mSearch;
    private EditText edt_groupName;
    String str_getTextFrom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTemparature = (TextView) findViewById(R.id.temparature);
        mWeatherStatus = (TextView) findViewById(R.id.weatherstatus);
        mLocation = (TextView) findViewById(R.id.location);
        mHumity = (TextView) findViewById(R.id.humidity);
        mWind = (TextView) findViewById(R.id.wind);

        mIcon = (ImageView) findViewById(R.id.weathericon);

        mSearch = (ImageView) findViewById(R.id.search_location);

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
      /*          AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                final EditText edittext = new EditText(MainActivity.this);
                 alert.setMessage("Enter Your Message");
                alert.setTitle("Enter Your City Name");

                alert.setView(edittext);

                alert.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        Editable YouEditTextValue = edittext.getText();
                        //OR
                        String citynameByuser = edittext.getText().toString();

                        makeJsonRequest(citynameByuser);

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();*/

                inflateDialogue();

            }
        });


                makeJsonRequestOne();

            }




            //json request
            public void makeJsonRequest(String cityname) {
                // Tag used to cancel the request
                String tag_json_obj = "json_obj_req";

                String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityname + "&appid=1c81badd61cac2d74f125a3121a86de6";


                ProgressDialog pDialog = new ProgressDialog(this);
                pDialog.setMessage("Loading...");
                pDialog.show();

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("TAG", response.toString());

                                try {
                                    pDialog.hide();

                                    String cityName = response.getString("name");

                                    JSONObject MainData = response.getJSONObject("main");
                                    double temperature = MainData.getDouble("temp");
                                    String humidity = MainData.getString("humidity");

                                    JSONObject WindData = response.getJSONObject("wind");
                                    double wind = WindData.getDouble("speed");

                                    JSONArray WeatherArray = response.getJSONArray("weather");
                                    JSONObject weatherStatus = WeatherArray.getJSONObject(0);

                                    String weather = weatherStatus.getString("main");
                                    String icon = weatherStatus.getString("icon");

                                    wind = (wind * 3.6);
                                    wind = roundTwoDecimals(wind);

                                    temperature = (temperature - 273.15);
                                    int xtemperature = (int) (temperature);
                                    mLocation.setText(cityName);
                                    mTemparature.setText("" + xtemperature + "°C");
                                    mHumity.setText("Humidity " + humidity + "%");
                                    mWind.setText("Wind " + wind + " k/h");
                                    mWeatherStatus.setText(weather);


                                    String url = "https://openweathermap.org/img/w/" + icon + ".png";
                                    Picasso.get().load(url).into(mIcon);
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


            public void makeJsonRequestOne() {
                // Tag used to cancel the request
                String tag_json_obj = "json_obj_req";

                String url = "https://api.openweathermap.org/data/2.5/weather?q=Pabna&appid=1c81badd61cac2d74f125a3121a86de6";


                ProgressDialog pDialog = new ProgressDialog(this);
                pDialog.setMessage("Loading...");
                pDialog.show();

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("TAG", response.toString());

                                try {
                                    pDialog.hide();

                                    String cityName = response.getString("name");

                                    JSONObject MainData = response.getJSONObject("main");
                                    double temperature = MainData.getDouble("temp");
                                    String humidity = MainData.getString("humidity");

                                    JSONObject WindData = response.getJSONObject("wind");
                                    double wind = WindData.getDouble("speed");

                                    JSONArray WeatherArray = response.getJSONArray("weather");
                                    JSONObject weatherStatus = WeatherArray.getJSONObject(0);

                                    String weather = weatherStatus.getString("main");
                                    String icon = weatherStatus.getString("icon");

                                    wind = (wind * 3.6);
                                    wind = roundTwoDecimals(wind);

                                    temperature = (temperature - 273.15);
                                    int xtemperature = (int) (temperature);
                                    mLocation.setText(cityName);
                                    mTemparature.setText("" + xtemperature + "°C");
                                    mHumity.setText("Humidity " + humidity + "%");
                                    mWind.setText("Wind " + wind + " k/h");
                                    mWeatherStatus.setText(weather);


                                    String url = "https://openweathermap.org/img/w/" + icon + ".png";
                                    Picasso.get().load(url).into(mIcon);
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


            ///two decimals value
            double roundTwoDecimals(double d) {
                DecimalFormat twoDForm = new DecimalFormat("#.##");

                return Double.valueOf(twoDForm.format(d));
            }

            double roundOneDecimals(double d) {
                DecimalFormat twoDForm = new DecimalFormat("#");

                return Double.valueOf(twoDForm.format(d));
            }

    public void inflateDialogue() {
        LayoutInflater layoutInflater=MainActivity.this.getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.dialog_group_name, null);
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Search City");

        alertDialog.setCancelable(false);

        edt_groupName = (EditText) view.findViewById(R.id.edt_groupName);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                str_getTextFrom=edt_groupName.getText().toString();
                //here we have to call Database firebase
                makeJsonRequest(str_getTextFrom);
                dialog.dismiss();

            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }


}