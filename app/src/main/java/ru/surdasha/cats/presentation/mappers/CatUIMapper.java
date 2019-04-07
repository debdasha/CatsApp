package ru.surdasha.cats.presentation.mappers;

import java.util.ArrayList;
import java.util.List;

import ru.surdasha.cats.domain.models.Cat;
import ru.surdasha.cats.presentation.models.CatUI;

public class CatUIMapper {

    public CatUI domainToUI(Cat cat){
        CatUI catUI = new CatUI();
        catUI.setServerId(cat.getServerId());
        catUI.setUrl(cat.getUrl());
        catUI.setImageHeight(cat.getImageHeight());
        catUI.setImageWidth(cat.getImageWidth());
        return catUI;
    }

    public Cat uiToDomain(CatUI catUI){
        Cat cat = new Cat();
        cat.setServerId(catUI.getServerId());
        cat.setUrl(catUI.getUrl());
        cat.setImageHeight(catUI.getImageHeight());
        cat.setImageWidth(catUI.getImageWidth());
        return cat;
    }

    public List<CatUI> domainToUI(List<Cat> cats){
        List<CatUI> result = new ArrayList<>();
        for (Cat cat: cats){
            result.add(domainToUI(cat));
        }
        return result;
    }
}
