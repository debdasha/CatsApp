package com.debdasha.catsapp;

import android.app.Application;

import com.debdasha.catsapp.di.components.AppComponent;
import com.debdasha.catsapp.di.components.DaggerAppComponent;
import com.debdasha.catsapp.di.modules.ContextModule;

public class CatApp extends Application {
    private static AppComponent component;

    public static AppComponent getAppComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }
}
