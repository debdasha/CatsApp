package com.debdasha.catsapp.di.modules;

import android.content.Context;

import androidx.room.Room;

import com.debdasha.catsapp.data.db.CatDao;
import com.debdasha.catsapp.data.db.CatDatabase;
import com.debdasha.catsapp.di.scopes.PerApplication;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    @PerApplication
    CatDatabase provideDatabase(@Named("AppContext") Context context) {
        return Room.databaseBuilder(context,
                CatDatabase.class, "cats.db").build();
    }

    @Provides
    @PerApplication
    CatDao providesProductDao(CatDatabase demoDatabase) {
        return demoDatabase.catDao();
    }
}
