package com.debdasha.catsapp.domain.usecases;

import com.debdasha.catsapp.domain.CatRepository;
import com.debdasha.catsapp.domain.models.Cat;

import io.reactivex.Completable;

public class DeleteCatUseCase {
    private final CatRepository catRepository;

    public DeleteCatUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Completable deleteCat(Cat cat) {
        return catRepository.deleteCat(cat);
    }
}
