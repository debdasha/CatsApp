package com.debdasha.catsapp.domain.usecases;

import com.debdasha.catsapp.domain.CatRepository;
import com.debdasha.catsapp.domain.models.Cat;

import io.reactivex.Single;

public class DownloadImageUseCase {
    CatRepository catRepository;

    public DownloadImageUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Single<Long> downloadImage(Cat cat) {
        return catRepository.downloadImage(cat);
    }
}
