package ru.surdasha.cats.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.surdasha.cats.data.remote.CatRemoteInterface;
import ru.surdasha.cats.data.remote.RequestInterceptor;

@Module
public class NetworkModule {
    private final String BASE_URL = "https://api.thecatapi.com/v1/";

    @NonNull
    @Provides
    @Singleton
    public CatRemoteInterface provideRetrofit(@NonNull OkHttpClient okHttpClient, @NonNull Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build().create(CatRemoteInterface.class);
    }

    @NonNull
    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

    @NonNull
    @Provides
    @Singleton
    public RequestInterceptor provideRequestInterceptor() {
        return new RequestInterceptor();
    }

    @NonNull
    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(@NonNull RequestInterceptor requestInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build();
    }
}
