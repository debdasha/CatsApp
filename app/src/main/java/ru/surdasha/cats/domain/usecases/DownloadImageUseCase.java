package ru.surdasha.cats.domain.usecases;

import io.reactivex.Single;
import ru.surdasha.cats.domain.CatRepository;
import ru.surdasha.cats.domain.models.Cat;

public class DownloadImageUseCase {
    CatRepository catRepository;

    public DownloadImageUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Single<Long> downloadImage(Cat cat){
        return catRepository.downloadImage(cat);
    }
}
