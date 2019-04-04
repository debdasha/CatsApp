package ru.surdasha.cats.data;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import ru.surdasha.cats.data.db.DbSource;
import ru.surdasha.cats.data.mappers.CatMapper;
import ru.surdasha.cats.data.remote.NetworkSource;
import ru.surdasha.cats.domain.CatRepository;
import ru.surdasha.cats.domain.models.Cat;

public class CatsRepositoryImpl implements CatRepository {
    private final NetworkSource networkSource;
    private final CatMapper catMapper;
    private final DbSource dbSource;

    public CatsRepositoryImpl(NetworkSource networkSource, CatMapper catMapper, DbSource dbSource) {
        this.networkSource = networkSource;
        this.catMapper = catMapper;
        this.dbSource = dbSource;
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
    public Maybe<List<Cat>> getCats(int pageNumber) {
        return networkSource.getCats(pageNumber)
                .flattenAsObservable(catRemotes -> catRemotes)
                .map(catRemote -> catMapper.remoteToDomain(catRemote))
                .toList()
                .toMaybe();
    }

    @Override
    public Maybe<List<Cat>> getFavoriteCats() {
        return dbSource.getAll()
                .flattenAsObservable(catDbs -> catDbs)
                .map(catDb -> catMapper.dbToDomain(catDb))
                .toList()
                .toMaybe();
    }
}
