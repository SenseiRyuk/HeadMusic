package com.example.if26new;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

public class ConcertActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mMapView;
    private TextView mTextViewTitle;
    private TextView mTextViewDate;
    private TextView mTextViewLocation;
    private TextView mTextViewArtist;
    private GoogleMap map;
    private Button focusBtn;
    private LatLng city;
    public static final String MAP_VIEW_BUNDLE_KEY="MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concert);

        mMapView = findViewById(R.id.mapView);
        mTextViewTitle = findViewById(R.id.txtTitleConcertID);
        mTextViewDate = findViewById(R.id.txtDateConcertID);
        mTextViewLocation = findViewById(R.id.txtLocationConcertID);
        mTextViewArtist = findViewById(R.id.txtArtistConcertID);
        focusBtn=findViewById(R.id.buttonFocusMap);
        focusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (focusBtn.getText().equals("Focus")){
                    float zoomLevel = 16.0f;
                    map.moveCamera((CameraUpdateFactory.newLatLngZoom(city,zoomLevel)));
                    focusBtn.setText("UnFocus");
                }else if (focusBtn.getText().equals("UnFocus")){
                    float zoomLevel = 3.0f;
                    map.moveCamera((CameraUpdateFactory.newLatLngZoom(city,zoomLevel)));
                    focusBtn.setText("Focus");
                }
            }
        });

        mTextViewArtist.setText("Post Malone");
        mTextViewLocation.setText("Capital one Arena, Washington DC");
        mTextViewDate.setText("10/12/2019");
        mTextViewTitle.setText("Runaway Tour");

        initGoogleMap(savedInstanceState);
    }

    private void initGoogleMap(Bundle savedInstanceState){
        Bundle mapViewBundle = null;
        if(savedInstanceState !=null){
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }



    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        city = new LatLng(38.8981251,-77.0208804);
        map.addMarker(new MarkerOptions().position(city));
        float zoomLevel = 3.0f;
        map.moveCamera((CameraUpdateFactory.newLatLngZoom(city,zoomLevel)));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

    }
}
