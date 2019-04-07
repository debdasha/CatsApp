package ru.surdasha.cats.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.surdasha.cats.data.remote.RequestInterceptor;
import ru.surdasha.cats.data.remote.interfaces.CatRemoteInterface;
import ru.surdasha.cats.di.scopes.PerApplication;

@Module
public class NetworkModule {
    private final String BASE_URL = "https://api.thecatapi.com/v1/";

    @NonNull
    @Provides
    @PerApplication
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
    @PerApplication
    public Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

    @NonNull
    @Provides
    @PerApplication
    public RequestInterceptor provideRequestInterceptor() {
        return new RequestInterceptor();
    }

    @NonNull
    @Provides
    @PerApplication
    public OkHttpClient provideOkHttpClient(@NonNull RequestInterceptor requestInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build();
    }
}
