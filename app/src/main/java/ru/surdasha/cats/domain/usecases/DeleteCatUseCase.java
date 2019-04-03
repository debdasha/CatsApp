package ru.surdasha.cats.domain.usecases;

import io.reactivex.Completable;
import ru.surdasha.cats.domain.CatRepository;

public class DeleteCatUseCase {
    private CatRepository catRepository;

    public DeleteCatUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Completable deleteCat(int id){
        return catRepository.deleteCat(id);
    }
}
