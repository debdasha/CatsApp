package com.debdasha.catsapp.data.remote.interfaces;

import com.debdasha.catsapp.data.remote.models.CatRemote;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface MemorySource {
    Completable addCats(List<CatRemote> remotes);

    Single<List<CatRemote>> getCats();

    Completable deleteCats();
}
