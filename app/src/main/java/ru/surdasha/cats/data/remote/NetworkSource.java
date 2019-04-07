package ru.surdasha.cats.data.remote;

import android.app.DownloadManager;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Maybe;
import io.reactivex.Single;
import ru.surdasha.cats.common.AndroidUtils;
import ru.surdasha.cats.data.remote.models.CatRemote;

public class NetworkSource {
    @NonNull
    private final CatRemoteInterface catRemoteInterface;
    DownloadManager downloadManager;
    private final int catsPageCount = 10;
    private final int DEFAULT_PAGE = 1;
    private int currentPageNumber = DEFAULT_PAGE;

    public NetworkSource(@NonNull CatRemoteInterface catRemoteInterface, DownloadManager downloadManager){
        this.catRemoteInterface = catRemoteInterface;
        this.downloadManager = downloadManager;
    }

    public Maybe<List<CatRemote>> getCats(){
        return catRemoteInterface.getCats(catsPageCount, currentPageNumber)
                .map(catRemotes -> {
                    Log.d("CurrentPage", String.valueOf(currentPageNumber));
                    currentPageNumber++;
                    return catRemotes;
                });
    }

    public Single<Long> downloadImage(CatRemote catRemote){
        return Single.fromCallable(() -> {
            String fileName = catRemote.getId() + ".jpg";
            File file = AndroidUtils.getDownloadsFolder();
            if (!file.exists()){
                file.mkdir();
            }
            File imageFile = new File(file.getAbsolutePath() + "/" +fileName);
            DownloadManager.Request request=new DownloadManager.Request(Uri.parse(catRemote.getUrl()))
                    .setTitle("Cats")
                    .setDescription("Скачиваем картинку")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                    .setDestinationUri(Uri.fromFile(imageFile))
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true);
            long downloadID = downloadManager.enqueue(request);
            return downloadID;
        });
    }
}
