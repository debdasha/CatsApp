package ru.surdasha.cats.di.modules;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ru.surdasha.cats.di.scopes.PerApplication;

@Module
public class ContextModule {
    private Context context;

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
