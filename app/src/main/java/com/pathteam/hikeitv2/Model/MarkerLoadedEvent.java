package com.pathteam.hikeitv2.Model;

import java.util.ArrayList;

/**
 * Created by JoshuaMabry on 11/21/16.
 */

public class MarkerLoadedEvent {

    public ArrayList<hMarker> markers;

    public MarkerLoadedEvent(ArrayList<hMarker> markers) {
        this.markers = markers;
    }
}
