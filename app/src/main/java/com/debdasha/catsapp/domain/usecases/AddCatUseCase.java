package com.debdasha.catsapp.domain.usecases;

import io.reactivex.Completable;
import com.debdasha.catsapp.domain.CatRepository;
import com.debdasha.catsapp.domain.models.Cat;

public class AddCatUseCase {
    private final CatRepository catRepository;

    public AddCatUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Completable addCat(Cat cat){
        return catRepository.addCat(cat);
    }
}
