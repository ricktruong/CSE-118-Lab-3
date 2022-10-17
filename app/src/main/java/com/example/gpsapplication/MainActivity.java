package com.example.gpsapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.gpsapplication.databinding.ActivityMainBinding;

import org.w3c.dom.Text;

public class MainActivity extends Activity{

    private TextView mTextView;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws SecurityException {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mTextView = binding.text;

        // TASK 2: Localize yourself using GPS
        if (ContextCompat.checkSelfPermission( this,android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(
                    this,
                    new String [] { android.Manifest.permission.ACCESS_COARSE_LOCATION , Manifest.permission.ACCESS_FINE_LOCATION },
                    200
            );
        }

        LocationManager myGPSLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener myGPSLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                double altitude;

                TextView latitude_text = findViewById(R.id.latitude_text);
                latitude_text.setText("" + latitude);

                TextView longitude_text = findViewById(R.id.longitude_text);
                longitude_text.setText("" + longitude);

                if (location.hasAltitude()) {
                    altitude = location.getAltitude();
                    TextView altitude_text = findViewById(R.id.altitude_text);
                    altitude_text.setText("" + altitude);
                }
            }
        };

        myGPSLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, myGPSLocationListener);

        // TASK 3: Wifi
        WifiManager wifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifi.getConnectionInfo();
        int level = wifi.calculateSignalLevel(wifiInfo.getRssi());

        TextView wifi_text = findViewById(R.id.wifi_text);
        wifi_text.setText("" + level);

    }
}