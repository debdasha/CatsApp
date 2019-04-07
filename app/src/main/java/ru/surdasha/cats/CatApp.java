package ru.surdasha.cats;

import android.app.Application;

import ru.surdasha.cats.di.AppComponent;
import ru.surdasha.cats.di.ContextModule;
import ru.surdasha.cats.di.DaggerAppComponent;
import ru.surdasha.cats.di.DatabaseModule;
import ru.surdasha.cats.di.DownloadManagerModule;

public class CatApp extends Application {
    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .downloadManagerModule(new DownloadManagerModule(this))
                .databaseModule(new DatabaseModule(this))
                .build();
    }

    public static AppComponent getAppComponent(){
        return component;
    }

}
