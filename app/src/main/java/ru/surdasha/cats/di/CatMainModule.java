package ru.surdasha.cats.di;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import ru.surdasha.cats.data.CatsRepositoryImpl;
import ru.surdasha.cats.data.mappers.CatMapper;
import ru.surdasha.cats.data.remote.NetworkSource;
import ru.surdasha.cats.domain.CatRepository;
import ru.surdasha.cats.domain.usecases.AddCatUseCase;
import ru.surdasha.cats.domain.usecases.DeleteCatUseCase;
import ru.surdasha.cats.domain.usecases.GetCatsUseCase;
import ru.surdasha.cats.domain.usecases.GetFavoriteCatsUseCase;

@Module
public class CatMainModule {
    @NonNull
    @MainScreen
    @Provides
    GetCatsUseCase provideGetAllCats(@NonNull CatRepository catRepository) {
        return new GetCatsUseCase(catRepository);
    }

    @NonNull
    @MainScreen
    @Provides
    GetFavoriteCatsUseCase provideGetFavoriteCatsUseCase(@NonNull CatRepository catRepository) {
        return new GetFavoriteCatsUseCase(catRepository);
    }

    @NonNull
    @MainScreen
    @Provides
    AddCatUseCase provideAddCatUseCase(@NonNull CatRepository catRepository) {
        return new AddCatUseCase(catRepository);
    }

    @NonNull
    @MainScreen
    @Provides
    DeleteCatUseCase provideDeleteCatUseCase(@NonNull CatRepository catRepository) {
        return new DeleteCatUseCase(catRepository);
    }

    @NonNull
    @MainScreen
    @Provides
    CatRepository provideCatRepository(@NonNull NetworkSource networkSource, CatMapper catMapper) {
        return new CatsRepositoryImpl(networkSource, catMapper);
    }

    @NonNull
    @MainScreen
    @Provides
    CatMapper provideCatMapper() {
        return new CatMapper();
    }
}
