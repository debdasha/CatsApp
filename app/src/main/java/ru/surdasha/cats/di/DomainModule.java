package ru.surdasha.cats.di;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import ru.surdasha.cats.domain.CatRepository;
import ru.surdasha.cats.domain.usecases.AddCatUseCase;
import ru.surdasha.cats.domain.usecases.DeleteCatUseCase;
import ru.surdasha.cats.domain.usecases.DownloadImageUseCase;
import ru.surdasha.cats.domain.usecases.GetAllCatsUseCase;
import ru.surdasha.cats.domain.usecases.GetFavoriteCatsUseCase;
import ru.surdasha.cats.domain.usecases.GetNextCatsUseCase;
import ru.surdasha.cats.domain.usecases.RefreshCatsUseCase;

@Module
public class DomainModule {
    @NonNull
    @Singleton
    @Provides
    GetAllCatsUseCase provideGetAllCats(@NonNull CatRepository catRepository) {
        return new GetAllCatsUseCase(catRepository);
    }

    @NonNull
    @Singleton
    @Provides
    RefreshCatsUseCase provideRefreshCatsUseCase(@NonNull CatRepository catRepository) {
        return new RefreshCatsUseCase(catRepository);
    }

    @NonNull
    @Singleton
    @Provides
    GetNextCatsUseCase provideGetNextCatsUseCase(@NonNull CatRepository catRepository) {
        return new GetNextCatsUseCase(catRepository);
    }


    @NonNull
    @Singleton
    @Provides
    GetFavoriteCatsUseCase provideGetFavoriteCatsUseCase(@NonNull CatRepository catRepository) {
        return new GetFavoriteCatsUseCase(catRepository);
    }

    @NonNull
    @Singleton
    @Provides
    AddCatUseCase provideAddCatUseCase(@NonNull CatRepository catRepository) {
        return new AddCatUseCase(catRepository);
    }

    @NonNull
    @Singleton
    @Provides
    DeleteCatUseCase provideDeleteCatUseCase(@NonNull CatRepository catRepository) {
        return new DeleteCatUseCase(catRepository);
    }

    @NonNull
    @Singleton
    @Provides
    DownloadImageUseCase provideDownloadImageUseCase(@NonNull CatRepository catRepository) {
        return new DownloadImageUseCase(catRepository);
    }
}
