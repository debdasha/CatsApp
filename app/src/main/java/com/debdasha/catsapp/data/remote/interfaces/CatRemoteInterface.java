package com.debdasha.catsapp.data.remote.interfaces;

import com.debdasha.catsapp.data.remote.models.CatRemote;

import java.util.List;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface CatRemoteInterface {
    @GET("images/search")
    @Headers("content-type: application/json")
    Maybe<List<CatRemote>> getCats(@Query("limit") int count, @Query("page") int page);
}
