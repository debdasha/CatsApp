package ru.surdasha.cats.domain.usecases;

import java.util.List;

import io.reactivex.Maybe;
import ru.surdasha.cats.domain.CatRepository;
import ru.surdasha.cats.domain.models.Cat;

public class GetCatsUseCase {
    private final CatRepository catRepository;

    public GetCatsUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Maybe<List<Cat>> getCats(int pageNumber){
        return catRepository.getCats(pageNumber);
    }
}
