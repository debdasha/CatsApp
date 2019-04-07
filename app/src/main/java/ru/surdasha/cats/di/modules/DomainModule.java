package ru.surdasha.cats.di.modules;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import ru.surdasha.cats.di.scopes.PerApplication;
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
    @PerApplication
    @Provides
    GetAllCatsUseCase provideGetAllCats(@NonNull CatRepository catRepository) {
        return new GetAllCatsUseCase(catRepository);
    }

    @NonNull
    @PerApplication
    @Provides
    RefreshCatsUseCase provideRefreshCatsUseCase(@NonNull CatRepository catRepository) {
        return new RefreshCatsUseCase(catRepository);
    }

    @NonNull
    @PerApplication
    @Provides
    GetNextCatsUseCase provideGetNextCatsUseCase(@NonNull CatRepository catRepository) {
        return new GetNextCatsUseCase(catRepository);
    }


    @NonNull
    @PerApplication
    @Provides
    GetFavoriteCatsUseCase provideGetFavoriteCatsUseCase(@NonNull CatRepository catRepository) {
        return new GetFavoriteCatsUseCase(catRepository);
    }

    @NonNull
    @PerApplication
    @Provides
    AddCatUseCase provideAddCatUseCase(@NonNull CatRepository catRepository) {
        return new AddCatUseCase(catRepository);
    }

    @NonNull
    @PerApplication
    @Provides
    DeleteCatUseCase provideDeleteCatUseCase(@NonNull CatRepository catRepository) {
        return new DeleteCatUseCase(catRepository);
    }

    @NonNull
    @PerApplication
    @Provides
    DownloadImageUseCase provideDownloadImageUseCase(@NonNull CatRepository catRepository) {
        return new DownloadImageUseCase(catRepository);
    }
}
