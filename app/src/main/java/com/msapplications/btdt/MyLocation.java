package com.msapplications.btdt;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import java.security.Permission;
import java.util.Timer;
import java.util.TimerTask;

public class MyLocation
{
    private Activity activity;
    private Timer timer;
    private LocationManager locationManager;
    private LocationResult locationResult;
    private boolean gps_enabled = false;
    private boolean network_enabled = false;

    public boolean getLocation(Activity activity, Context context, LocationResult result)
    {
        this.activity = activity;

        // I use LocationResult callback class to pass location value from MyLocation to user code.
        locationResult = result;
        if (locationManager == null)
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // Exceptions will be thrown if provider is not permitted.
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Don't start listeners if no provider is enabled
        if (!gps_enabled && !network_enabled)
            return false;

        String[] PERMISSIONS_LOCATION = { Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION };

        boolean grant = false;

        for (String permission : PERMISSIONS_LOCATION) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                grant = true;
                break;
            }
        }

        // We don't have permission so prompt the user
        if (grant)
            ActivityCompat.requestPermissions(activity, PERMISSIONS_LOCATION, 1);

        if (gps_enabled)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGPS);

        if (network_enabled)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);

        timer = new Timer();
        timer.schedule(new GetLastLocation(), 20000);
        return true;
    }

    private LocationListener locationListenerGPS = new LocationListener()
    {
        public void onLocationChanged(Location location)
        {
            timer.cancel();
            locationResult.gotLocation(location);
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerNetwork);
        }

        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    private LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer.cancel();
            locationResult.gotLocation(location);
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerGPS);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    class GetLastLocation extends TimerTask
    {
        @Override
        public void run()
        {
            locationManager.removeUpdates(locationListenerGPS);
            locationManager.removeUpdates(locationListenerNetwork);

            Location networkLocation = null;
            Location gpsLocation = null;

            String[] PERMISSIONS_LOCATION = { Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION };

            boolean grant = false;

            for (String permission : PERMISSIONS_LOCATION) {
                if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    grant = true;
                    break;
                }
            }

            // We don't have permission so prompt the user
            if (grant)
                ActivityCompat.requestPermissions(activity, PERMISSIONS_LOCATION, 1);

            if (gps_enabled)
                gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (network_enabled)
                networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            // If there are both values use the latest one
            if (gpsLocation != null && networkLocation != null)
            {
                if (gpsLocation.getTime() > networkLocation.getTime())
                    locationResult.gotLocation(gpsLocation);
                else
                    locationResult.gotLocation(networkLocation);
                return;
            }

            if (gpsLocation != null) {
                locationResult.gotLocation(gpsLocation);
                return;
            }

            if (networkLocation != null) {
                locationResult.gotLocation(networkLocation);
                return;
            }

            locationResult.gotLocation(null);
        }
    }

    public void cancelTimer()
    {
        timer.cancel();
        locationManager.removeUpdates(locationListenerGPS);
        locationManager.removeUpdates(locationListenerNetwork);
    }

    public static abstract class LocationResult {
        public abstract void gotLocation(Location location);
    }
}
