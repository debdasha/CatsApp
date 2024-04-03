package com.debdasha.catsapp.domain.usecases;

import com.debdasha.catsapp.domain.CatRepository;
import com.debdasha.catsapp.domain.models.Cat;

import io.reactivex.Completable;

public class AddCatUseCase {
    private final CatRepository catRepository;

    public AddCatUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Completable addCat(Cat cat) {
        return catRepository.addCat(cat);
    }
}
