package com.debdasha.catsapp.domain.usecases;

import io.reactivex.Completable;
import com.debdasha.catsapp.domain.CatRepository;
import com.debdasha.catsapp.domain.models.Cat;

public class DeleteCatUseCase {
    private CatRepository catRepository;

    public DeleteCatUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Completable deleteCat(Cat cat){
        return catRepository.deleteCat(cat);
    }
}
