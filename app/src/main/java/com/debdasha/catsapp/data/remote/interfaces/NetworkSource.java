package com.debdasha.catsapp.data.remote.interfaces;

import com.debdasha.catsapp.data.remote.models.CatRemote;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

public interface NetworkSource {
    Maybe<List<CatRemote>> getCats();

    Single<Long> downloadImage(CatRemote catRemote);
}
