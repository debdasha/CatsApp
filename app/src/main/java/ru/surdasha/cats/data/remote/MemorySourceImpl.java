package ru.surdasha.cats.data.remote;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.surdasha.cats.data.remote.interfaces.MemoryCache;
import ru.surdasha.cats.data.remote.interfaces.MemorySource;
import ru.surdasha.cats.data.remote.models.CatRemote;

public class MemorySourceImpl implements MemorySource {
    MemoryCache memoryCache;

    public MemorySourceImpl(MemoryCache memoryCache){
        this.memoryCache = memoryCache;
    }

    @Override
    public Completable addCats(List<CatRemote> remotes){
        return Completable.fromAction(() -> {
            memoryCache.addCats(remotes);
        });
    }

    @Override
    public Single<List<CatRemote>> getCats(){
        return Single.just(memoryCache.getCats());
    }

    @Override
    public Completable deleteCats(){
        return Completable.fromAction(() -> {
            memoryCache.clearCache();
        });
    }

}
