package com.debdasha.catsapp.data.db;

import androidx.annotation.NonNull;

import com.debdasha.catsapp.data.db.models.CatDb;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public class DbSourceImpl implements DbSource {
    CatDao catDao;

    public DbSourceImpl(@NonNull CatDao catDao) {
        this.catDao = catDao;
    }

    @Override
    public Completable addCat(CatDb cat) {
        return catDao.add(cat);
    }

    @Override
    public Completable deleteCat(CatDb catDb) {
        return catDao.delete(catDb);
    }

    @Override
    public Maybe<List<CatDb>> getCats() {
        return catDao.getAll();
    }
}
