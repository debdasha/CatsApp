package ru.surdasha.cats.domain.usecases;

import io.reactivex.Completable;
import ru.surdasha.cats.domain.CatRepository;
import ru.surdasha.cats.domain.models.Cat;

public class AddCatUseCase {
    private final CatRepository catRepository;

    public AddCatUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Completable addCat(Cat cat){
        return catRepository.addCat(cat);
    }
}
