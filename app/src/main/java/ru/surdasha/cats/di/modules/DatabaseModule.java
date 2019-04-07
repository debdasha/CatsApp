package ru.surdasha.cats.di.modules;

import android.content.Context;

import javax.inject.Named;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import ru.surdasha.cats.data.db.CatDao;
import ru.surdasha.cats.data.db.CatDatabase;
import ru.surdasha.cats.di.scopes.PerApplication;

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
