package com.debdasha.catsapp.data.db;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import com.debdasha.catsapp.data.db.models.CatDb;

public interface DbSource {

    Completable addCat(CatDb cat);

    Completable deleteCat(CatDb catDb);

    Maybe<List<CatDb>> getCats();
}
