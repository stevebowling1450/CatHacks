package com.pathteam.hikeitv2.Views;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pathteam.hikeitv2.Components.Utils;
import com.pathteam.hikeitv2.MainActivity;
import com.pathteam.hikeitv2.Model.HikeList;
import com.pathteam.hikeitv2.Model.hMarker;
import com.pathteam.hikeitv2.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.pathteam.hikeitv2.MainActivity.position;

/**
 * Created by nicholashall on 11/17/16.
 */

public class HikeDetailView extends RelativeLayout implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener {

    private Integer i;

    private GoogleMap mMap;
    public LatLng Home;
    public String name;
    public String id;
    LatLng oldcoord;


    private Context context;

    private ArrayList<hMarker> markers;

    public HikeList hike;
    public List<HikeList> hikelist;

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.date)
    TextView date;

    @Bind(R.id.location)
    TextView location;

    @Bind(R.id.mapview)
    MapView mapView;

    @Bind(R.id.hike_notes)
    TextView hikeNotes;

    @Bind(R.id.back)
    Button back;

    @Bind(R.id.take_a_hike)
    Button takeHike;

    @Bind(R.id.saved_image_view)
    ImageView savedImage;

    public HikeDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        hikelist= new ArrayList<>(((MainActivity)context).hikelist);

        mapView.getMapAsync(this);
        mapView.onCreate(((MainActivity) getContext()).savedInstanceState);
        mapView.onResume();

        hike = hikelist.get(position);
        markers = hike.hmarker;

        //Set information from Array to our Hike Detail UI
        title.setText(hike.getTitle());
        date.setText(hike.hmarker.get(0).getDate().toString());
        hikeNotes.setText(hike.getHikeNotes());
        savedImage.setImageBitmap(Utils.decodeImage(hike.getImageString()));
        location.setText("Calories Burned "+ hike.getCaloriesBurned());

        //Resets i to 0 everytime screen inflates, in order to help select first marker for poly line
        i = 0;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        UiSettings UiSettings = mMap.getUiSettings();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        mMap.setMyLocationEnabled(true);


        for(hMarker marker : markers) {
            mMap.addMarker(new MarkerOptions().position(marker.getMarkerPos()));
            Home = marker.getMarkerPos();


            // Determines first marker of the Marker Array
            if (oldcoord != null){
//                draws the poly lines
                mMap.addPolyline((new PolylineOptions())
                        .add(Home, oldcoord)
                        .color(Color.RED)
                        .width(25));

            i++;
            }

            oldcoord=Home;
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Home, 18));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }
}