package com.pathteam.hikeitv2.Model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by alexhughes on 11/16/16.
 */
// this is the marker object we can add to this if need be.
public class hMarker {

    @SerializedName("markerId")
    private Integer markerId;

    @SerializedName("markerPos")
    private LatLng  markerPos;

    @SerializedName("Date")
    private Date date;

    public hMarker(Integer markerId, LatLng markerPos, Date date) {
        this.markerId = markerId;
        this.markerPos = markerPos;
        this.date = date;
    }

    public Integer getMarkerId() {
        return markerId;
    }

    public void setMarkerId(Integer markerId) {
        this.markerId = markerId;
    }

    public LatLng getMarkerPos() {
        return markerPos;
    }

    public void setMarkerPos(LatLng markerPos) {
        this.markerPos = markerPos;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
