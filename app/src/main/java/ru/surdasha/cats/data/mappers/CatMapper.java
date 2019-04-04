package ru.surdasha.cats.data.mappers;

import ru.surdasha.cats.data.db.CatDb;
import ru.surdasha.cats.data.remote.models.CatRemote;
import ru.surdasha.cats.domain.models.Cat;

public class CatMapper {

    public Cat remoteToDomain(CatRemote catRemote){
        Cat cat = new Cat();
        cat.setServerId(catRemote.getId());
        cat.setUrl(catRemote.getUrl());
        cat.setImageHeight(catRemote.getImageHeight());
        cat.setImageWidth(catRemote.getImageWidth());
        return cat;
    }

    public CatDb domainToDb(Cat cat){
        CatDb catDb = new CatDb();
        catDb.setId(cat.getId());
        catDb.setServerId(cat.getServerId());
        catDb.setUrl(cat.getUrl());
        return catDb;
    }
}
