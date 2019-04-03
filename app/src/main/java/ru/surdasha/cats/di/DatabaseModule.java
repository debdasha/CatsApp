package ru.surdasha.cats.di;

import android.content.Context;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import ru.surdasha.cats.CatApp;
import ru.surdasha.cats.data.db.CatDatabase;

@Module
public class DatabaseModule {
    @Provides
    @Singleton
    CatDatabase provideDatabase(Context context){
        return Room.databaseBuilder(context,
                CatDatabase.class, "cats.db").build();
    }
}
