package com.stveo.stevebowling.esports.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stevebowling on 4/15/17
 *
 */

public class Schedules {
    @SerializedName("name")
    private String name;

    @SerializedName("timeZone")
    private String tineZone;

    @SerializedName("events")
    private Events[] events;

    public Schedules(){
    }

    public String getName() {
        return name;
    }

    public String getTineZone() {
        return tineZone;
    }

    public Events[] getEvents() {
        return events;
    }
}
