package com.debdasha.catsapp.di.modules;

import android.content.Context;

import javax.inject.Named;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import com.debdasha.catsapp.data.db.CatDao;
import com.debdasha.catsapp.data.db.CatDatabase;
import com.debdasha.catsapp.di.scopes.PerApplication;

@Module
public class DatabaseModule {

    @Provides
    @PerApplication
    CatDatabase provideDatabase(@Named("AppContext") Context context){
        return Room.databaseBuilder(context,
                CatDatabase.class, "cats.db").build();
    }

    @Provides
    @PerApplication
    CatDao providesProductDao(CatDatabase demoDatabase) {
        return demoDatabase.catDao();
    }
}
