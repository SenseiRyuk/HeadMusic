package com.example.if26new;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.if26new.Model.UserModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

public class ConcertActivity extends AppCompatActivity implements OnMapReadyCallback,View.OnClickListener {
    private MapView mMapView;
    private TextView mTextViewTitle;
    private TextView mTextViewDate;
    private TextView mTextViewLocation;
    private TextView mTextViewArtist;
    private ImageView mImageViewArtist;
    private GoogleMap map;
    private Button focusBtn;
    private LatLng city;
    private String artistName;
    private String date;
    private String location;
    private String locationCity;
    private double locationLat;
    private double locationLgn;
    private String titleConcert;
    private int artistImage;
    private SaveMyMusicDatabase db;
    public static final String MAP_VIEW_BUNDLE_KEY="MapViewBundleKey";
    private ImageButton returnButton;
    private String fragmentName;
    private ConstraintLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        artistName=getIntent().getStringExtra("ARTIST_NAME");
        date=getIntent().getStringExtra("DATE");
        location=getIntent().getStringExtra("LOCATION");
        locationCity=getIntent().getStringExtra("LOCATION_CITY");
        locationLat=getIntent().getDoubleExtra("LOCATION_LAT",0);
        locationLgn=getIntent().getDoubleExtra("LOCATION_LGN",0);
        titleConcert=getIntent().getStringExtra("TITLE_CONCERT");
        artistImage=getIntent().getIntExtra("ARTIST_IMAGE_ID",0);
        fragmentName=getIntent().getExtras().getString("FRAGMENT_NAME");
        setContentView(R.layout.activity_concert);
        returnButton=findViewById(R.id.returnButtonConcertView);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnMethod();
            }
        });

        db=SaveMyMusicDatabase.getInstance(this);
        background=findViewById(R.id.layoutConcert);
        UserModel currentUser=db.userDao().getUserFromId(db.getActualUser());
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {currentUser.getStartColorGradient(),currentUser.getEndColorGradient()});
        gd.setCornerRadius(0f);
        background.setBackground(gd);

        mMapView = findViewById(R.id.mapView);
        mTextViewTitle = findViewById(R.id.txtTitleConcertID);
        mTextViewDate = findViewById(R.id.txtDateConcertID);
        mTextViewLocation = findViewById(R.id.txtLocationConcertID);
        mTextViewArtist = findViewById(R.id.txtArtistConcertID);
        mImageViewArtist = findViewById(R.id.imageViewArtistImage);
        mImageViewArtist.setImageResource(artistImage);
        android.view.ViewGroup.LayoutParams params3 = mImageViewArtist.getLayoutParams();
        params3.height=450;
        params3.width=450;
        mImageViewArtist.setLayoutParams(params3);
        mImageViewArtist.setOnClickListener(this);
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

        mTextViewArtist.setText(artistName);
        mTextViewArtist.setOnClickListener(this);
        mTextViewLocation.setText(location+", "+locationCity);
        mTextViewDate.setText(date);
        mTextViewTitle.setText(titleConcert);

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
        city = new LatLng(locationLat,locationLgn);
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
    public void onClick(View v){
        Bundle bundle=new Bundle();
        bundle.putString("ARTIST_NAME",artistName);
        bundle.putInt("ARTIST_IMAGE_ID",artistImage);
        bundle.putInt("ARTIST_BIO",db.mArtistDao().getArtistFromName(artistName).getBio());
        bundle.putString("FRAGMENT_NAME",fragmentName);
        Intent playListActivity = new Intent(ConcertActivity.this, ActivityArtist.class);
        playListActivity.putExtras(bundle);
        startActivity(playListActivity);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returnMethod();
    }
    public void  returnMethod(){
        Bundle bundle = new Bundle();
        bundle.putString("FRAGMENT_NAME",fragmentName);
        Intent playListActivity = new Intent(ConcertActivity.this, HomeActivity.class);
        playListActivity.putExtras(bundle);
        startActivity(playListActivity);
    }
}
