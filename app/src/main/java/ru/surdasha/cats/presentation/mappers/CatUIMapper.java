package ru.surdasha.cats.presentation.mappers;

import java.util.ArrayList;
import java.util.List;

import ru.surdasha.cats.common.Utils;
import ru.surdasha.cats.domain.models.Cat;
import ru.surdasha.cats.presentation.misc.ViewUtils;
import ru.surdasha.cats.presentation.models.CatUI;

public class CatUIMapper {
    private final ViewUtils viewUtils;
    private final Utils utils;

    public CatUIMapper(ViewUtils viewUtils, Utils utils) {
        this.viewUtils = viewUtils;
        this.utils = utils;
    }

    public CatUI domainToUI(Cat cat){
        CatUI catUI = new CatUI();
        catUI.setServerId(cat.getServerId());
        catUI.setUrl(cat.getUrl());
        catUI.setImageHeight(cat.getImageHeight());
        catUI.setImageWidth(cat.getImageWidth());
        int screenWidth = viewUtils.getScreenWidth();
        catUI.setScreenImageWidth(screenWidth);
        catUI.setScreenImageHeight(utils.countAspectRatioHeight(screenWidth,
                catUI.getImageHeight(), catUI.getImageWidth()));
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
