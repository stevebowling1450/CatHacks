package com.stveo.stevebowling.esports.Model;


import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stevebowling on 4/15/17.
 *
 */

public class Disciplines  implements Comparable<Disciplines> {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("shortname")
    private String shortname;

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("copyrights")
    private String copyrights;

    public Disciplines() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullname() {
        return fullname;
    }

    public String getCopyrights() {
        return copyrights;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(@NonNull Disciplines disciplines) {
        return getName().compareTo(disciplines.getName());
    }
}
