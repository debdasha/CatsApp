package com.debdasha.catsapp.di.modules;

import android.app.DownloadManager;
import android.content.Context;

import androidx.annotation.NonNull;

import com.debdasha.catsapp.di.scopes.PerApplication;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class DownloadManagerModule {

    @Provides
    @PerApplication
    @NonNull
    public DownloadManager provideDownloadManager(@Named("AppContext") Context context) {
        return (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

}
