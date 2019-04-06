package ru.surdasha.cats.di;

import android.app.DownloadManager;
import android.content.Context;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import ru.surdasha.cats.data.db.CatDatabase;

@Module
public class DownloadManagerModule {
    private DownloadManager downloadManager;

    public DownloadManagerModule(Context context) {
        downloadManager =  (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    @Provides
    @Singleton
    DownloadManager provideDatabase(){
        return downloadManager;
    }

}
