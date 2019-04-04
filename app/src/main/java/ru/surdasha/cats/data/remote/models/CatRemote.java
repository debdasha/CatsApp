package ru.surdasha.cats.data.remote.models;

import com.google.gson.annotations.SerializedName;

public class CatRemote {
    private String id;
    private String url;
    @SerializedName("height")
    private int imageHeight;
    @SerializedName("width")
    private int imageWidth;

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }
}
