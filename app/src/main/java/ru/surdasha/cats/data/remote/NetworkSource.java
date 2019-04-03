package ru.surdasha.cats.data.remote;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Maybe;
import ru.surdasha.cats.data.remote.models.CatRemote;

public class NetworkSource {
    @NonNull
    private final CatRemoteInterface catRemoteInterface;
    private final int catsPageCount = 10;

    public NetworkSource(@NonNull CatRemoteInterface catRemoteInterface){
        this.catRemoteInterface = catRemoteInterface;
    }

    public Maybe<List<CatRemote>> getCats(int pageNumber){
        return catRemoteInterface.getCats(catsPageCount, pageNumber);
    }
}
