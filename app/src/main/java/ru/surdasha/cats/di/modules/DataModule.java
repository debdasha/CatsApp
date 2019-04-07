package ru.surdasha.cats.di.modules;

import android.app.DownloadManager;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import ru.surdasha.cats.common.AndroidUtils;
import ru.surdasha.cats.data.CatsRepositoryImpl;
import ru.surdasha.cats.data.db.CatDao;
import ru.surdasha.cats.data.db.DbSource;
import ru.surdasha.cats.data.db.DbSourceImpl;
import ru.surdasha.cats.data.mappers.CatMapper;
import ru.surdasha.cats.data.remote.MemoryCacheImpl;
import ru.surdasha.cats.data.remote.MemorySourceImpl;
import ru.surdasha.cats.data.remote.NetworkSourceImpl;
import ru.surdasha.cats.data.remote.interfaces.CatRemoteInterface;
import ru.surdasha.cats.data.remote.interfaces.MemoryCache;
import ru.surdasha.cats.data.remote.interfaces.MemorySource;
import ru.surdasha.cats.data.remote.interfaces.NetworkSource;
import ru.surdasha.cats.di.scopes.PerApplication;
import ru.surdasha.cats.domain.CatRepository;

@Module(includes = {DatabaseModule.class, NetworkModule.class, DownloadManagerModule.class})
public class DataModule {
    @NonNull
    @PerApplication
    @Provides
    NetworkSource provideNetworkSource(CatRemoteInterface catRemoteInterface, DownloadManager downloadManager,
                                       AndroidUtils androidUtils) {
        return new NetworkSourceImpl(catRemoteInterface, downloadManager,androidUtils);
    }

    @NonNull
    @PerApplication
    @Provides
    DbSource provideDbSource(CatDao catDao) {
        return new DbSourceImpl(catDao);
    }

    @NonNull
    @PerApplication
    @Provides
    CatRepository provideCatRepository(@NonNull NetworkSource networkSource, CatMapper catMapper,
                                       DbSource dbSource, MemorySource memorySource) {
        return new CatsRepositoryImpl(networkSource, catMapper, dbSource, memorySource);
    }

    @NonNull
    @PerApplication
    @Provides
    CatMapper provideCatMapper() {
        return new CatMapper();
    }

    @NonNull
    @PerApplication
    @Provides
    MemorySource provideMemorySource(MemoryCache memoryCache) {
        return new MemorySourceImpl(memoryCache);
    }

    @NonNull
    @PerApplication
    @Provides
    MemoryCache provideMemoryCache() {
        return new MemoryCacheImpl();
    }

}
