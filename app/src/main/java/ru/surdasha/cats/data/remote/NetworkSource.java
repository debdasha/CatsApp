package ru.surdasha.cats.data.remote;

import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Maybe;
import ru.surdasha.cats.data.remote.models.CatRemote;

public class NetworkSource {
    @NonNull
    private final CatRemoteInterface catRemoteInterface;
    private final int catsPageCount = 10;
    private final int DEFAULT_PAGE = 1;
    private int currentPageNumber = DEFAULT_PAGE;

    public NetworkSource(@NonNull CatRemoteInterface catRemoteInterface){
        this.catRemoteInterface = catRemoteInterface;
    }

    public Maybe<List<CatRemote>> getCats(){
        return catRemoteInterface.getCats(catsPageCount, currentPageNumber)
                .map(catRemotes -> {
                    Log.d("CurrentPage", String.valueOf(currentPageNumber));
                    currentPageNumber++;
                    return catRemotes;
                });
    }
}
