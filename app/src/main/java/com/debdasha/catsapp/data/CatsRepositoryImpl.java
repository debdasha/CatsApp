package com.debdasha.catsapp.data;

import com.debdasha.catsapp.data.db.DbSource;
import com.debdasha.catsapp.data.mappers.CatMapper;
import com.debdasha.catsapp.data.remote.interfaces.MemorySource;
import com.debdasha.catsapp.data.remote.interfaces.NetworkSource;
import com.debdasha.catsapp.domain.CatRepository;
import com.debdasha.catsapp.domain.models.Cat;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class CatsRepositoryImpl implements CatRepository {
    private final NetworkSource networkSource;
    private final CatMapper catMapper;
    private final DbSource dbSource;
    private MemorySource memorySource;

    public CatsRepositoryImpl(NetworkSource networkSource, CatMapper catMapper, DbSource dbSource, MemorySource memorySource) {
        this.networkSource = networkSource;
        this.catMapper = catMapper;
        this.dbSource = dbSource;
        this.memorySource = memorySource;
    }

    @Override
    public Completable deleteCat(Cat cat) {
        return dbSource.deleteCat(catMapper.domainToDb(cat));
    }

    @Override
    public Completable addCat(Cat cat) {
        return dbSource.addCat(catMapper.domainToDb(cat));
    }

    @Override
    public Maybe<List<Cat>> getAllCats() {
        return memorySource
                .getCats()
                .flatMapMaybe(remotes -> {
                    if (remotes.isEmpty()) {
                        return getNextCats();
                    } else {
                        return Maybe.just(remotes)
                                .flattenAsObservable(catRemotes -> catRemotes)
                                .map(catRemote -> catMapper.remoteToDomain(catRemote))
                                .toList()
                                .toMaybe();
                    }
                });
    }

    @Override
    public Maybe<List<Cat>> getFavoriteCats() {
        return dbSource.getCats()
                .flattenAsObservable(catDbs -> catDbs)
                .map(catDb -> catMapper.dbToDomain(catDb))
                .toList()
                .toMaybe();
    }

    @Override
    public Maybe<List<Cat>> getNextCats() {
        return networkSource.getCats()
                .flatMap(newCats -> {
                    return memorySource.addCats(newCats)
                            .andThen(Maybe.just(newCats));
                })
                .flattenAsObservable(catRemotes -> catRemotes)
                .map(catRemote -> catMapper.remoteToDomain(catRemote))
                .toList()
                .toMaybe();
    }

    @Override
    public Completable deleteCats() {
        return memorySource.deleteCats();
    }

    @Override
    public Single<Long> downloadImage(Cat cat) {
        return networkSource.downloadImage(catMapper.domainToRemote(cat));
    }
}
