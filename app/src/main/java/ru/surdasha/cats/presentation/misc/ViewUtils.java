package ru.surdasha.cats.presentation.misc;

import android.app.Activity;
import android.util.DisplayMetrics;

public class ViewUtils {
    Activity activity;

    public ViewUtils(Activity context){
        this.activity = context;
    }

    public int getScreenWidth(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;
    }

    public int countAspectRatioHeight(int screenWidth, int imageHeight, int imageWidth){
        int newImageHeight = imageHeight * screenWidth / imageWidth;
        return newImageHeight;
    }

}
