package com.debdasha.catsapp.data.remote.models;

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

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }
}
