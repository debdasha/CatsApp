package ru.surdasha.cats.di.modules;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import ru.surdasha.cats.common.Utils;
import ru.surdasha.cats.di.scopes.PerApplication;

@Module
public class UtilsModule {
    @NonNull
    @PerApplication
    @Provides
    Utils provideMemoryCache() {
        return new Utils();
    }
}
