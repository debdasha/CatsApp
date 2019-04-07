package ru.surdasha.cats.domain.usecases;

import java.util.List;

import io.reactivex.Maybe;
import ru.surdasha.cats.domain.CatRepository;
import ru.surdasha.cats.domain.models.Cat;

public class GetNextCatsUseCase {

    private final CatRepository catRepository;

    public GetNextCatsUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Maybe<List<Cat>> getNextCats(){
        return catRepository.getNextCats();
    }
}
