package ru.surdasha.cats.data.remote;

import java.util.List;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import ru.surdasha.cats.data.remote.models.CatRemote;

public interface CatRemoteInterface {
    @GET("images/search")
    @Headers("content-type: application/json")
    Maybe<List<CatRemote>> getCats(@Query("limit")int count, @Query("page")int page);
}
