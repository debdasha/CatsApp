package ru.surdasha.cats.data.remote;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.surdasha.cats.data.remote.models.CatRemote;

public class MemorySource {
    MemoryCatsCache memoryCatsCache = new MemoryCatsCache();

    public Completable addCats(List<CatRemote> remotes){
        return Completable.fromAction(() -> {
            memoryCatsCache.addCats(remotes);
        });
    }

    public Single<List<CatRemote>> getCats(){
        return Single.just(memoryCatsCache.getCats());
    }

    public Completable deleteCats(){
        return Completable.fromAction(() -> {
            memoryCatsCache.clearCache();
        });
    }

}
