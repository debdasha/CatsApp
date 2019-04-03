package ru.surdasha.cats.data;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import ru.surdasha.cats.data.mappers.CatMapper;
import ru.surdasha.cats.data.remote.NetworkSource;
import ru.surdasha.cats.domain.CatRepository;
import ru.surdasha.cats.domain.models.Cat;

public class CatsRepositoryImpl implements CatRepository {
    private final NetworkSource networkSource;
    private final CatMapper catMapper;

    public CatsRepositoryImpl(NetworkSource networkSource, CatMapper catMapper) {
        this.networkSource = networkSource;
        this.catMapper = catMapper;
    }

    @Override
    public Completable deleteCat(int id) {
        return null;
    }

    @Override
    public Completable addCat(Cat cat) {
        return null;
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
        return null;
    }
}
