package com.debdasha.catsapp.di.modules;

import android.app.DownloadManager;

import androidx.annotation.NonNull;

import com.debdasha.catsapp.common.AndroidUtils;
import com.debdasha.catsapp.data.CatsRepositoryImpl;
import com.debdasha.catsapp.data.db.CatDao;
import com.debdasha.catsapp.data.db.DbSource;
import com.debdasha.catsapp.data.db.DbSourceImpl;
import com.debdasha.catsapp.data.mappers.CatMapper;
import com.debdasha.catsapp.data.remote.MemoryCacheImpl;
import com.debdasha.catsapp.data.remote.MemorySourceImpl;
import com.debdasha.catsapp.data.remote.NetworkSourceImpl;
import com.debdasha.catsapp.data.remote.interfaces.CatRemoteInterface;
import com.debdasha.catsapp.data.remote.interfaces.MemoryCache;
import com.debdasha.catsapp.data.remote.interfaces.MemorySource;
import com.debdasha.catsapp.data.remote.interfaces.NetworkSource;
import com.debdasha.catsapp.di.scopes.PerApplication;
import com.debdasha.catsapp.domain.CatRepository;

import dagger.Module;
import dagger.Provides;

@Module(includes = {DatabaseModule.class, NetworkModule.class, DownloadManagerModule.class})
public class DataModule {
    @NonNull
    @PerApplication
    @Provides
    NetworkSource provideNetworkSource(CatRemoteInterface catRemoteInterface, DownloadManager downloadManager,
                                       AndroidUtils androidUtils) {
        return new NetworkSourceImpl(catRemoteInterface, downloadManager, androidUtils);
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
