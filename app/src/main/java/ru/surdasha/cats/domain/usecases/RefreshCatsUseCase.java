package ru.surdasha.cats.domain.usecases;

import java.util.List;

import io.reactivex.Maybe;
import ru.surdasha.cats.domain.CatRepository;
import ru.surdasha.cats.domain.models.Cat;

public class RefreshCatsUseCase {
    private final CatRepository catRepository;

    public RefreshCatsUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Maybe<List<Cat>> getRefreshedCats(){
        return catRepository.deleteCats()
                .andThen(catRepository.getNextCats());
    }
}
