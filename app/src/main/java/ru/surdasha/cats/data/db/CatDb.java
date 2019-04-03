package ru.surdasha.cats.data.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cats")
public class CatDb {
    @PrimaryKey
    private long id;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "serverId")
    private String serverId;

    public void setId(long id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
}
