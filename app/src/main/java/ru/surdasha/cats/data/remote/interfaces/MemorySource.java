package ru.surdasha.cats.data.remote.interfaces;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.surdasha.cats.data.remote.models.CatRemote;

public interface MemorySource {
    Completable addCats(List<CatRemote> remotes);

    Single<List<CatRemote>> getCats();

    Completable deleteCats();
}
