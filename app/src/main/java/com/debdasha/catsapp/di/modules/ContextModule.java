package com.debdasha.catsapp.di.modules;

import android.content.Context;

import com.debdasha.catsapp.di.scopes.PerApplication;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @PerApplication
    @Named("AppContext")
    Context getContext() {
        return context;
    }
}
