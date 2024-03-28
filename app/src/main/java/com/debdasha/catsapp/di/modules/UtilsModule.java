package com.debdasha.catsapp.di.modules;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import com.debdasha.catsapp.common.Utils;
import com.debdasha.catsapp.di.scopes.PerApplication;

@Module
public class UtilsModule {
    @NonNull
    @PerApplication
    @Provides
    Utils provideMemoryCache() {
        return new Utils();
    }
}
