package ru.surdasha.cats.presentation.models;

public class CatUI {
    private long Id;
    private String serverId;
    private String url;
    private int imageHeight;
    private int imageWidth;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
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
