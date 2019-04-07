package ru.surdasha.cats.di;

import android.app.DownloadManager;

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

@Module(includes = {DatabaseModule.class, NetworkModule.class, DownloadManagerModule.class})
public class DataModule {
    @NonNull
    @Singleton
    @Provides
    NetworkSource provideNetworkSource(CatRemoteInterface catRemoteInterface, DownloadManager downloadManager) {
        return new NetworkSource(catRemoteInterface, downloadManager);
    }

    @NonNull
    @Singleton
    @Provides
    DbSource provideDbSource(CatDao catDao) {
        return new DbSource(catDao);
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
}
