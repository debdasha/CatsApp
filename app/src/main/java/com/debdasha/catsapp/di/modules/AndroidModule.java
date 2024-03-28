package com.debdasha.catsapp.di.modules;

import dagger.Module;
import dagger.Provides;
import com.debdasha.catsapp.common.AndroidUtils;
import com.debdasha.catsapp.di.scopes.PerApplication;

@Module
public class AndroidModule {

    @Provides
    @PerApplication
    AndroidUtils provideAndroidUtils() {
        return new AndroidUtils();
    }
}
