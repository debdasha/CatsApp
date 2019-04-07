package ru.surdasha.cats.di;

import android.app.DownloadManager;
import android.content.Context;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;

@Module
public class DownloadManagerModule {

    @Provides
    @Singleton
    @NonNull
    public DownloadManager provideDownloadManager(Context context){
        return (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

}
