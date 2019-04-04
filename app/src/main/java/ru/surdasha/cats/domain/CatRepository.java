package ru.surdasha.cats.domain;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import ru.surdasha.cats.domain.models.Cat;

public interface CatRepository {
    Completable deleteCat(Cat cat);

    Completable addCat(Cat cat);

    Maybe<List<Cat>> getCats(int pageNumber);

    Maybe<List<Cat>> getFavoriteCats();
}
