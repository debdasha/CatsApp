package ru.surdasha.cats.di;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import ru.surdasha.cats.data.CatsRepositoryImpl;
import ru.surdasha.cats.data.db.CatDao;
import ru.surdasha.cats.data.db.DbSource;
import ru.surdasha.cats.data.mappers.CatMapper;
import ru.surdasha.cats.data.remote.CatRemoteInterface;
import ru.surdasha.cats.data.remote.NetworkSource;
import ru.surdasha.cats.domain.CatRepository;
import ru.surdasha.cats.domain.usecases.AddCatUseCase;
import ru.surdasha.cats.domain.usecases.DeleteCatUseCase;
import ru.surdasha.cats.domain.usecases.GetAllCatsUseCase;
import ru.surdasha.cats.domain.usecases.GetFavoriteCatsUseCase;
import ru.surdasha.cats.domain.usecases.GetNextCatsUseCase;
import ru.surdasha.cats.domain.usecases.RefreshCatsUseCase;
import ru.surdasha.cats.presentation.mappers.CatUIMapper;

@Module
public class CatMainModule {
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
    CatRepository provideCatRepository(@NonNull NetworkSource networkSource, CatMapper catMapper,
                                       DbSource dbSource) {
        return new CatsRepositoryImpl(networkSource, catMapper, dbSource);
    }

    @NonNull
    @Singleton
    @Provides
    CatMapper provideCatMapper() {
        return new CatMapper();
    }

    @NonNull
    @Singleton
    @Provides
    CatUIMapper provideCatUIMapper() {
        return new CatUIMapper();
    }

    @NonNull
    @Singleton
    @Provides
    NetworkSource provideNetworkSource(CatRemoteInterface catRemoteInterface) {
        return new NetworkSource(catRemoteInterface);
    }

    @NonNull
    @Singleton
    @Provides
    DbSource provideDbSource(CatDao catDao) {
        return new DbSource(catDao);
    }
}
