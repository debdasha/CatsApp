package com.debdasha.catsapp.data.remote;

import com.debdasha.catsapp.data.remote.interfaces.MemoryCache;
import com.debdasha.catsapp.data.remote.interfaces.MemorySource;
import com.debdasha.catsapp.data.remote.models.CatRemote;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class MemorySourceImpl implements MemorySource {
    MemoryCache memoryCache;

    public MemorySourceImpl(MemoryCache memoryCache) {
        this.memoryCache = memoryCache;
    }

    @Override
    public Completable addCats(List<CatRemote> remotes) {
        return Completable.fromAction(() -> {
            memoryCache.addCats(remotes);
        });
    }

    @Override
    public Single<List<CatRemote>> getCats() {
        return Single.just(memoryCache.getCats());
    }

    @Override
    public Completable deleteCats() {
        return Completable.fromAction(() -> {
            memoryCache.clearCache();
        });
    }

}
