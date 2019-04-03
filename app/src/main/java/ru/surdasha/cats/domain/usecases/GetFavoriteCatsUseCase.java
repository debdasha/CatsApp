package ru.surdasha.cats.domain.usecases;

import java.util.List;

import io.reactivex.Maybe;
import ru.surdasha.cats.domain.CatRepository;
import ru.surdasha.cats.domain.models.Cat;

public class GetFavoriteCatsUseCase {
    private CatRepository catRepository;

    public GetFavoriteCatsUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Maybe<List<Cat>> getFavoriteCats(){
        return catRepository.getFavoriteCats();
    }
}
