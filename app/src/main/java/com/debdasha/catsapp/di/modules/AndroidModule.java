package com.debdasha.catsapp.di.modules;

import com.debdasha.catsapp.common.AndroidUtils;
import com.debdasha.catsapp.di.scopes.PerApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule {

    @Provides
    @PerApplication
    AndroidUtils provideAndroidUtils() {
        return new AndroidUtils();
    }
}
