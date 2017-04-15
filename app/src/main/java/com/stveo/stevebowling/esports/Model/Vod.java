package com.stveo.stevebowling.esports.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stevebowling on 4/15/17
 */

public class Vod {


    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    @SerializedName("language")
    private String language;

    @SerializedName("category")
    private String category;

    public Vod(){
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getLanguage() {
        return language;
    }

    public String getCategory() {
        return category;
    }

}
