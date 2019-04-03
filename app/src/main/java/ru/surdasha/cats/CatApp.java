package ru.surdasha.cats;

import android.app.Application;
import android.content.Context;

import ru.surdasha.cats.di.AppComponent;
import ru.surdasha.cats.di.ContextModule;
import ru.surdasha.cats.di.DaggerAppComponent;

public class CatApp extends Application {
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }
}
