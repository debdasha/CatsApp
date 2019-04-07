package ru.surdasha.cats.domain.usecases;

import java.util.List;

import io.reactivex.Maybe;
import ru.surdasha.cats.domain.CatRepository;
import ru.surdasha.cats.domain.models.Cat;

public class GetAllCatsUseCase {
    private final CatRepository catRepository;

    public GetAllCatsUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Maybe<List<Cat>> getAllCats(){
        return catRepository.getAllCats();
    }
}
