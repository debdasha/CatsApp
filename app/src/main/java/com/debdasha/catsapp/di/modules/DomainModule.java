package com.debdasha.catsapp.di.modules;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import com.debdasha.catsapp.di.scopes.PerApplication;
import com.debdasha.catsapp.domain.CatRepository;
import com.debdasha.catsapp.domain.usecases.AddCatUseCase;
import com.debdasha.catsapp.domain.usecases.DeleteCatUseCase;
import com.debdasha.catsapp.domain.usecases.DownloadImageUseCase;
import com.debdasha.catsapp.domain.usecases.GetAllCatsUseCase;
import com.debdasha.catsapp.domain.usecases.GetFavoriteCatsUseCase;
import com.debdasha.catsapp.domain.usecases.GetNextCatsUseCase;
import com.debdasha.catsapp.domain.usecases.RefreshCatsUseCase;

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
