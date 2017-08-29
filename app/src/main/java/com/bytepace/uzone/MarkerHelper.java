package com.bytepace.uzone;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viktor Matskevich on 28-Aug-17.
 * Company: Bytepace
 * EMAIL: viktor.matskevich@sy-dev.com
 */

class MarkerHelper {

    static List<MarkerOptions> getMuseumMarkers() {
        List<MarkerOptions> markerOptionses = new ArrayList<>();
        for(Museum museum : getMuseums()) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(museum.getLocation())
                    .title(museum.getTitle());
            markerOptionses.add(markerOptions);
        }
        return markerOptionses;
    }

    private static List<Museum> getMuseums() {
        List<Museum> museumList = new ArrayList<>();

        museumList.add(new Museum("Freud Museum London", new LatLng(51.548303, -0.177480)));
        museumList.add(new Museum("The Sherlock Holmes Museum", new LatLng(51.523755, -0.158583)));
        museumList.add(new Museum("Madame Tussauds London", new LatLng(51.522820, -0.155026)));
        museumList.add(new Museum("The Peep Gallery", new LatLng(51.519362, -0.162666)));
        museumList.add(new Museum("The Wallace Collection", new LatLng(51.517434, -0.153174)));
        museumList.add(new Museum("Grant Museum of Zoology", new LatLng(51.523707, -0.134348)));
        museumList.add(new Museum("The Petrie Museum of Egyptian Archaeology", new LatLng(51.523514, -0.133065)));
        museumList.add(new Museum("Charles Dickens Museum", new LatLng(51.523473, -0.116143)));
        museumList.add(new Museum("Sir John Soane's Museum", new LatLng(51.516996, -0.117396)));
        museumList.add(new Museum("Dr Johnson's House", new LatLng(51.515030, -0.108135)));
        museumList.add(new Museum("Museum of London", new LatLng(51.517570, -0.096762)));
        museumList.add(new Museum("Jewish Museum London", new LatLng(51.537270, -0.144721)));
        museumList.add(new Museum("London Canal Museum", new LatLng(51.534224, -0.120169)));
        museumList.add(new Museum("The Guards Museum", new LatLng(51.499350, -0.137240)));
        museumList.add(new Museum("English Heritage", new LatLng(51.498420, -0.126406)));
        museumList.add(new Museum("Florence Nightingale Museum", new LatLng(51.500114, -0.117656)));
        museumList.add(new Museum("Simpson Philip", new LatLng(51.500895, -0.112279)));
        museumList.add(new Museum("Fashion and Textile Museum", new LatLng(51.501208, -0.081832)));
        museumList.add(new Museum("Gordon Museum", new LatLng(51.503876, -0.090240)));

        return museumList;
    }
}
