package ru.surdasha.cats.di.modules;

import android.app.DownloadManager;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import ru.surdasha.cats.common.AndroidUtils;
import ru.surdasha.cats.data.CatsRepositoryImpl;
import ru.surdasha.cats.data.db.CatDao;
import ru.surdasha.cats.data.db.DbSource;
import ru.surdasha.cats.data.mappers.CatMapper;
import ru.surdasha.cats.data.remote.CatRemoteInterface;
import ru.surdasha.cats.data.remote.NetworkSource;
import ru.surdasha.cats.di.scopes.PerApplication;
import ru.surdasha.cats.domain.CatRepository;

@Module(includes = {DatabaseModule.class, NetworkModule.class, DownloadManagerModule.class})
public class DataModule {
    @NonNull
    @PerApplication
    @Provides
    NetworkSource provideNetworkSource(CatRemoteInterface catRemoteInterface, DownloadManager downloadManager,
                                       AndroidUtils androidUtils) {
        return new NetworkSource(catRemoteInterface, downloadManager,androidUtils);
    }

    @NonNull
    @PerApplication
    @Provides
    DbSource provideDbSource(CatDao catDao) {
        return new DbSource(catDao);
    }

    @NonNull
    @PerApplication
    @Provides
    CatRepository provideCatRepository(@NonNull NetworkSource networkSource, CatMapper catMapper,
                                       DbSource dbSource) {
        return new CatsRepositoryImpl(networkSource, catMapper, dbSource);
    }

    @NonNull
    @PerApplication
    @Provides
    CatMapper provideCatMapper() {
        return new CatMapper();
    }
}
