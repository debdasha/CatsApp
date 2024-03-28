package com.debdasha.catsapp.domain.usecases;

import java.util.List;

import io.reactivex.Maybe;
import com.debdasha.catsapp.domain.CatRepository;
import com.debdasha.catsapp.domain.models.Cat;

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
