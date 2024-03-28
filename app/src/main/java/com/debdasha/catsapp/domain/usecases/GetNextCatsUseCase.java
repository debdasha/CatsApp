package com.debdasha.catsapp.domain.usecases;

import java.util.List;

import io.reactivex.Maybe;
import com.debdasha.catsapp.domain.CatRepository;
import com.debdasha.catsapp.domain.models.Cat;

public class GetNextCatsUseCase {

    private final CatRepository catRepository;

    public GetNextCatsUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Maybe<List<Cat>> getNextCats(){
        return catRepository.getNextCats();
    }
}
