package com.debdasha.catsapp.domain.usecases;

import java.util.List;

import io.reactivex.Maybe;
import com.debdasha.catsapp.domain.CatRepository;
import com.debdasha.catsapp.domain.models.Cat;

public class GetFavoriteCatsUseCase {
    private CatRepository catRepository;

    public GetFavoriteCatsUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Maybe<List<Cat>> getFavoriteCats(){
        return catRepository.getFavoriteCats();
    }
}
