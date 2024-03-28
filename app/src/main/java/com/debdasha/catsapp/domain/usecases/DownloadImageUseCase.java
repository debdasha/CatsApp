package com.debdasha.catsapp.domain.usecases;

import io.reactivex.Single;
import com.debdasha.catsapp.domain.CatRepository;
import com.debdasha.catsapp.domain.models.Cat;

public class DownloadImageUseCase {
    CatRepository catRepository;

    public DownloadImageUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Single<Long> downloadImage(Cat cat){
        return catRepository.downloadImage(cat);
    }
}
