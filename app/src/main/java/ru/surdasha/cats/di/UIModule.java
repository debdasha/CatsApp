package ru.surdasha.cats.di;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import ru.surdasha.cats.presentation.mappers.CatUIMapper;

@Module
public class UIModule {
    @NonNull
    @Singleton
    @Provides
    CatUIMapper provideCatUIMapper() {
        return new CatUIMapper();
    }
}
