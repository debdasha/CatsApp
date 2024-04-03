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

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }
}
