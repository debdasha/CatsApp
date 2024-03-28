package com.debdasha.catsapp.data.db;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import com.debdasha.catsapp.data.db.models.CatDb;

public class DbSourceImpl implements DbSource{
    CatDao catDao;

    public DbSourceImpl(@NonNull CatDao catDao){
        this.catDao = catDao;
    }

    @Override
    public Completable addCat(CatDb cat){
        return catDao.add(cat);
    }

    @Override
    public Completable deleteCat(CatDb catDb){
        return catDao.delete(catDb);
    }

    @Override
    public Maybe<List<CatDb>> getCats(){
        return catDao.getAll();
    }
}
