package com.debdasha.catsapp.data.db.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cats")
public class CatDb {
    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "serverId")
    private String serverId;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "imageHeight")
    private int imageHeight;
    @ColumnInfo(name = "imageWidth")
    private int imageWidth;

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
