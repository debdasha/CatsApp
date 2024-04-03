package com.debdasha.catsapp.domain;

import com.debdasha.catsapp.domain.models.Cat;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface CatRepository {
    Completable deleteCat(Cat cat);

    Completable addCat(Cat cat);

    Maybe<List<Cat>> getAllCats();

    Maybe<List<Cat>> getFavoriteCats();

    Maybe<List<Cat>> getNextCats();

    Completable deleteCats();

    Single<Long> downloadImage(Cat cat);
}
