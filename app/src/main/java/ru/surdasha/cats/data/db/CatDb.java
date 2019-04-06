package ru.surdasha.cats.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cats")
public class CatDb {
    private long id;
    @ColumnInfo(name = "url")
    private String url;
    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "serverId")
    private String serverId;
    @ColumnInfo(name = "imageHeight")
    private int imageHeight;
    @ColumnInfo(name = "imageWidth")
    private int imageWidth;

    public void setId(long id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getServerId() {
        return serverId;
    }

    public String getUrl() {
        return url;
    }

    public long getId() {
        return id;
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
