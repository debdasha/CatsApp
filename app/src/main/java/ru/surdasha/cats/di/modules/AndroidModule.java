package ru.surdasha.cats.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.surdasha.cats.common.AndroidUtils;
import ru.surdasha.cats.di.scopes.PerApplication;

@Module
public class AndroidModule {

    @Provides
    @PerApplication
    AndroidUtils provideAndroidUtils() {
        return new AndroidUtils();
    }
}
