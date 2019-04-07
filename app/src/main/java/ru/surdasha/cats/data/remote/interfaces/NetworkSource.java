package ru.surdasha.cats.data.remote.interfaces;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import ru.surdasha.cats.data.remote.models.CatRemote;

public interface NetworkSource {
    Maybe<List<CatRemote>> getCats();

    Single<Long> downloadImage(CatRemote catRemote);
}
