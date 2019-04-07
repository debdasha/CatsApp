package ru.surdasha.cats.common;

import android.os.Build;
import android.os.Environment;

import java.io.File;

public class AndroidUtils {


    public File getDownloadsFolder(){
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }

    public boolean checkRequiredPermission(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
