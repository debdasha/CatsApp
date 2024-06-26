package com.debdasha.catsapp.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.debdasha.catsapp.data.db.models.CatDb;

@Database(entities = {CatDb.class}, version = 1, exportSchema = false)
public abstract class CatDatabase extends RoomDatabase {
    public abstract CatDao catDao();
}
