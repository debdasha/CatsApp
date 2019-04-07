package ru.surdasha.cats.common;

import android.os.Environment;

import java.io.File;

public class AndroidUtils {


    public static File getDownloadsFolder(){
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }
}
