package com.bytepace.uzone;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Viktor Matskevich on 28-Aug-17.
 * Company: Bytepace
 * EMAIL: viktor.matskevich@sy-dev.com
 */

class Museum {

    private String mTitle;
    private LatLng mLocation;

    Museum(String title, LatLng location) {
        mTitle = title;
        mLocation = location;
    }

    String getTitle() {
        return mTitle;
    }

    LatLng getLocation() {
        return mLocation;
    }
}
