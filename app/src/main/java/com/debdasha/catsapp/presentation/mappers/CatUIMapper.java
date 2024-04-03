package com.debdasha.catsapp.presentation.mappers;

import com.debdasha.catsapp.domain.models.Cat;
import com.debdasha.catsapp.presentation.misc.ViewUtils;
import com.debdasha.catsapp.presentation.models.CatUI;

import java.util.ArrayList;
import java.util.List;

public class CatUIMapper {
    private final ViewUtils viewUtils;

    public CatUIMapper(ViewUtils viewUtils) {
        this.viewUtils = viewUtils;
    }

    public CatUI domainToUI(Cat cat) {
        CatUI catUI = new CatUI();
        catUI.setServerId(cat.getServerId());
        catUI.setUrl(cat.getUrl());
        catUI.setImageHeight(cat.getImageHeight());
        catUI.setImageWidth(cat.getImageWidth());
        int screenWidth = viewUtils.getScreenWidth();
        catUI.setScreenImageWidth(screenWidth);
        catUI.setScreenImageHeight(viewUtils.countAspectRatioHeight(screenWidth,
                catUI.getImageHeight(), catUI.getImageWidth()));
        return catUI;
    }

    public Cat uiToDomain(CatUI catUI) {
        Cat cat = new Cat();
        cat.setServerId(catUI.getServerId());
        cat.setUrl(catUI.getUrl());
        cat.setImageHeight(catUI.getImageHeight());
        cat.setImageWidth(catUI.getImageWidth());
        return cat;
    }

    public List<CatUI> domainToUI(List<Cat> cats) {
        List<CatUI> result = new ArrayList<>();
        for (Cat cat : cats) {
            result.add(domainToUI(cat));
        }
        return result;
    }
}
