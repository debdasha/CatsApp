package ru.surdasha.cats.data.db;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import ru.surdasha.cats.data.db.models.CatDb;

public interface DbSource {

    Completable addCat(CatDb cat);

    Completable deleteCat(CatDb catDb);

    Maybe<List<CatDb>> getCats();
}
