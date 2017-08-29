package com.bytepace.uzone;

import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viktor Matskevich on 28-Aug-17.
 * Company: Bytepace
 * EMAIL: viktor.matskevich@sy-dev.com
 */

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, DrawPanel.Listener {

    private ImageButton mOpenDrawButton;
    private ImageButton mCloseDrawButton;
    private ImageButton mClearMapButton;
    private DrawPanel mDrawPanel;

    private List<LatLng> mBoundPointsList;
    private List<Marker> mMarkersList;
    private boolean isStartDraw;

    private GoogleMap mGoogleMap;
    private Polygon mCurrentPolygon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mMapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

        mDrawPanel = (DrawPanel) findViewById(R.id.draw_panel);
        mDrawPanel.setListener(this);

        mOpenDrawButton = (ImageButton) findViewById(R.id.ib_open_draw);
        mOpenDrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(View.GONE, View.VISIBLE);
            }
        });

        mCloseDrawButton = (ImageButton) findViewById(R.id.ib_close_draw);
        mCloseDrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(View.VISIBLE, View.GONE);
            }
        });

        mClearMapButton = (ImageButton) findViewById(R.id.ib_clear_map);
        mClearMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawPanel.cleanPanel();
                removePolygon();
            }
        });

        mBoundPointsList = new ArrayList<>();
        mMarkersList = new ArrayList<>();
        isStartDraw = true;

        setTitle(getString(R.string.draw_a_polygon_on_the_map));
    }

    private void setVisibility(int visibilityDrawBtn, int visibilityCloseBtn) {
        mOpenDrawButton.setVisibility(visibilityDrawBtn);
        mCloseDrawButton.setVisibility(visibilityCloseBtn);
        mDrawPanel.setVisibility(visibilityCloseBtn);
    }

    private void addPoint(float x, float y) {
        if(mGoogleMap == null)
            return;

        if(isStartDraw)  {
            mBoundPointsList.clear();
            isStartDraw = false;
        }

        LatLng latLng = mGoogleMap.getProjection().fromScreenLocation(new Point(Math.round(x), Math.round(y)));
        if(latLng != null)
            mBoundPointsList.add(latLng);
    }

    private void removePolygon() {
        if(mCurrentPolygon != null) {
            mCurrentPolygon.remove();
            mCurrentPolygon = null;
        }

        for(Marker marker : mMarkersList)
            marker.setVisible(true);

        mClearMapButton.setVisibility(View.GONE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        initMap();
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(51.522362, -0.132591))
                .zoom((float) 12.5).build();
        mGoogleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }

    private void initMap() {
        if(mGoogleMap == null) return;

        for (MarkerOptions markerOptions : MarkerHelper.getMuseumMarkers())
            mMarkersList.add(mGoogleMap.addMarker(markerOptions));
    }

    @Override
    public void onStartDraw(float x, float y) {
        removePolygon();
        addPoint(x, y);
    }

    @Override
    public void onMove(float x, float y) {
        addPoint(x, y);
    }

    @Override
    public void onEndDraw() {
        isStartDraw = true;

        if(mGoogleMap == null) return;

        PolygonOptions polygonOptions = new PolygonOptions()
                .addAll(mBoundPointsList)
                .strokeColor(Color.argb(100, 0, 0, 255))
                .fillColor(Color.argb(51, 0, 0, 255));
        mCurrentPolygon = mGoogleMap.addPolygon(polygonOptions);

        mDrawPanel.cleanPanel();
        removeMarkers();
    }

    private void removeMarkers() {
        boolean nullMarkers = true;
        for(Marker marker : mMarkersList) {
            if (!PolyUtil.containsLocation(marker.getPosition(), mCurrentPolygon.getPoints(), false))
                marker.setVisible(false);
            else if(nullMarkers)
                nullMarkers = false;
        }

        if (nullMarkers)
            mClearMapButton.performClick();
        else
            mClearMapButton.setVisibility(View.VISIBLE);
    }
}
