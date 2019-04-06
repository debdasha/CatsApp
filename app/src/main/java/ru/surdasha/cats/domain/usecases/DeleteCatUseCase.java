package ru.surdasha.cats.domain.usecases;

import io.reactivex.Completable;
import ru.surdasha.cats.domain.CatRepository;
import ru.surdasha.cats.domain.models.Cat;

public class DeleteCatUseCase {
    private CatRepository catRepository;

    public DeleteCatUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Completable deleteCat(Cat cat){
        return catRepository.deleteCat(cat);
    }
}
