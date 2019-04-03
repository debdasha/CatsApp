package ru.surdasha.cats.data.remote;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {
    private final String API_KEY = "7457bb36-d5ec-4c81-ba0b-2bdd6b792a1e";
    private final String HEADER_AUTHORIZATION = "x-api-key";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader(HEADER_AUTHORIZATION, API_KEY);
        Response response = chain.proceed(builder.build());
        return response;
    }
}
