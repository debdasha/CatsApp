package com.debdasha.catsapp.domain.usecases;

import com.debdasha.catsapp.domain.CatRepository;
import com.debdasha.catsapp.domain.models.Cat;

import java.util.List;

import io.reactivex.Maybe;

public class GetNextCatsUseCase {

    private final CatRepository catRepository;

    public GetNextCatsUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Maybe<List<Cat>> getNextCats() {
        return catRepository.getNextCats();
    }
}
