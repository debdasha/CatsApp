package ru.surdasha.cats.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CatDb.class}, version = 1)
public abstract class CatDatabase extends RoomDatabase {
    public abstract CatDao catDao();
}
