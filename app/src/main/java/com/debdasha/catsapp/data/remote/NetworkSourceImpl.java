package com.debdasha.catsapp.data.remote;

import android.app.DownloadManager;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.debdasha.catsapp.common.AndroidUtils;
import com.debdasha.catsapp.data.remote.interfaces.CatRemoteInterface;
import com.debdasha.catsapp.data.remote.interfaces.NetworkSource;
import com.debdasha.catsapp.data.remote.models.CatRemote;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

public class NetworkSourceImpl implements NetworkSource {
    private final CatRemoteInterface catRemoteInterface;
    private final DownloadManager downloadManager;
    private final AndroidUtils androidUtils;
    private final int catsPageCount = 10;
    private final int DEFAULT_PAGE = 1;
    private int currentPageNumber = DEFAULT_PAGE;

    public NetworkSourceImpl(@NonNull CatRemoteInterface catRemoteInterface, DownloadManager downloadManager,
                             AndroidUtils androidUtils) {
        this.catRemoteInterface = catRemoteInterface;
        this.downloadManager = downloadManager;
        this.androidUtils = androidUtils;
    }

    @Override
    public Maybe<List<CatRemote>> getCats() {
        return catRemoteInterface.getCats(catsPageCount, currentPageNumber)
                .map(catRemotes -> {
                    currentPageNumber++;
                    return catRemotes;
                });
    }

    @Override
    public Single<Long> downloadImage(CatRemote catRemote) {
        return Single.fromCallable(() -> {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(catRemote.getUrl()))
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                    .setDestinationUri(getImageFileUri(catRemote))
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true);
            long downloadID = downloadManager.enqueue(request);
            return downloadID;
        });
    }

    @NotNull
    private Uri getImageFileUri(CatRemote catRemote) {
        return Uri.fromFile(createImageFile(catRemote));
    }

    @NotNull
    private File createImageFile(CatRemote catRemote) {
        final String filePathFormat = "%s/%s";
        String fileName = createImageFileName(catRemote);
        String downloadFolderPath = getDestinationFolderPath();
        File imageFile = new File(String.format(filePathFormat, downloadFolderPath, fileName));
        return imageFile;
    }

    @NotNull
    private String getDestinationFolderPath() {
        File downloadFolder = androidUtils.getDownloadsFolder();
        if (!downloadFolder.exists()) {
            downloadFolder.mkdir();
        }
        return downloadFolder.getAbsolutePath();
    }

    @NotNull
    private String createImageFileName(CatRemote catRemote) {
        final String fileNameFormat = "%s.jpg";
        return String.format(fileNameFormat, catRemote.getId());
    }
}
