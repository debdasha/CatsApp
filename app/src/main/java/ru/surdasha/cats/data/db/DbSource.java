package ru.surdasha.cats.data.db;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Maybe;

public class DbSource {
    CatDao catDao;

    public DbSource(@NonNull CatDao catDao){
        this.catDao = catDao;
    }

    public Completable addCat(CatDb cat){
        return catDao.add(cat);
    }

    public Completable deleteCat(CatDb catDb){
        return catDao.delete(catDb);
    }

    public Maybe<List<CatDb>> getAll(){
        return catDao.getAll();
    }
}
