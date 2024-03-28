package com.debdasha.catsapp.data.mappers;

import com.debdasha.catsapp.data.db.models.CatDb;
import com.debdasha.catsapp.data.remote.models.CatRemote;
import com.debdasha.catsapp.domain.models.Cat;

public class CatMapper {

    public Cat remoteToDomain(CatRemote catRemote){
        Cat cat = new Cat();
        cat.setServerId(catRemote.getId());
        cat.setUrl(catRemote.getUrl());
        cat.setImageHeight(catRemote.getImageHeight());
        cat.setImageWidth(catRemote.getImageWidth());
        return cat;
    }

    public CatRemote domainToRemote(Cat cat){
        CatRemote catRemote = new CatRemote();
        catRemote.setId(cat.getServerId());
        catRemote.setUrl(cat.getUrl());
        catRemote.setImageHeight(cat.getImageHeight());
        catRemote.setImageWidth(cat.getImageWidth());
        return catRemote;
    }

    public CatDb domainToDb(Cat cat){
        CatDb catDb = new CatDb();
        catDb.setServerId(cat.getServerId());
        catDb.setUrl(cat.getUrl());
        catDb.setImageHeight(cat.getImageHeight());
        catDb.setImageWidth(cat.getImageWidth());
        return catDb;
    }

    public Cat dbToDomain(CatDb catDb){
        Cat cat = new Cat();
        cat.setServerId(catDb.getServerId());
        cat.setUrl(catDb.getUrl());
        cat.setImageHeight(catDb.getImageHeight());
        cat.setImageWidth(catDb.getImageWidth());
        return cat;
    }
}
