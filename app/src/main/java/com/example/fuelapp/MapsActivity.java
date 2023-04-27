package com.example.fuelapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toast.makeText(this, "Click on a mapMarker!", Toast.LENGTH_LONG).show();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        LatLngBounds sriLankaWidth=new LatLngBounds(new LatLng(9.7938, 80.2210), new LatLng(5.9320, 80.5911));
//        mMap.setLatLngBoundsForCameraTarget(sriLankaWidth);

        String district = getIntent().getStringExtra("dis");

        FuelDB db = new FuelDB(this);//context of the main activity is passing
        db.open();
        String disList = db.getFillingStationDistrict(district);

        String[] words= disList.split(",");

        for (String w : words){
            MapMarker m=new MapMarker();
            m.markingMap(w);
        }
    }
        private class MapMarker {
            public void markingMap(String dis) {
                Geocoder geocoder=new Geocoder(MapsActivity.this.getApplicationContext(), Locale.getDefault());
                List addressList;

                try {
                    addressList = geocoder.getFromLocationName(dis, 1);

                if (addressList!=null){
                    Address address=(Address) addressList.get(0);

                    double lat=address.getLatitude();
                    double lon=address.getLongitude();

                    LatLng loc=new LatLng(lat, lon);

                    mMap.addMarker(new MarkerOptions().position(loc).title(dis));

                    float zoomLevel = (float)11.0;
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, zoomLevel));
                }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String name=marker.getTitle();

                        Toast.makeText(MapsActivity.this, name, Toast.LENGTH_LONG).show();

                        String password = getIntent().getStringExtra("password");

                        Intent i=new Intent(getBaseContext(), DistributingFuel.class);
                        i.putExtra("pwf", password);
                        i.putExtra("name", name);
                        startActivity(i);

                        return true;
                    }
                });
        }
    }
}