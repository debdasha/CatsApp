package ru.surdasha.cats.di;

import android.content.Context;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import ru.surdasha.cats.data.db.CatDao;
import ru.surdasha.cats.data.db.CatDatabase;

@Module
public class DatabaseModule {
    private CatDatabase catDatabase;

    public DatabaseModule(Context context) {
        catDatabase = Room.databaseBuilder(context,
                CatDatabase.class, "cats.db").build();
    }

    @Provides
    @Singleton
    CatDatabase provideDatabase(){
        return catDatabase;
    }

    @Singleton
    @Provides
    CatDao providesProductDao(CatDatabase demoDatabase) {
        return demoDatabase.catDao();
    }
}
