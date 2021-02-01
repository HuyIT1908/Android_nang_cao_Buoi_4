package com.example.buoi_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tv_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_1 = findViewById(R.id.tv_1);
    }
    public void get_GPs(View view){
        // xin quyen
        if (ContextCompat.checkSelfPermission(this , Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    99);
        }else {
            // lay du lieu cho vao text
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            lay gps tu vi tri cuoi cung luu tren thiet bi
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null){
                tv_1.setText( "getLatitude : " + location.getLatitude() + "\ngetLongitude:  " + location.getLongitude());
            }
//            lay gps tu internet hoac song gps cua thiet bi
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,
                    0, 0,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            tv_1.setText( "getLatitude : " + location.getLatitude() + "\ngetLongitude:  " + location.getLongitude());
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    });
        }
    }
    public void check_network(View view){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi =
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifi.isConnected()){
            tv_1.setText("wifi connected");
        }
        NetworkInfo mobile_3g =
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobile_3g.isConnected()){
            tv_1.setText("Mobile 3G/4G Connected");
            Toast.makeText(getApplicationContext() , "3G/4G Connected",
                    Toast.LENGTH_SHORT).show();
        }
//        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
//        startActivity(new Intent(Settings.ACTION_SETTINGS));
    }
}