package com.debdasha.catsapp.presentation.misc;

import android.app.Activity;
import android.util.DisplayMetrics;

public class ViewUtils {
    private final Activity activity;

    public ViewUtils(Activity context) {
        this.activity = context;
    }

    public int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public int countAspectRatioHeight(int screenWidth, int imageHeight, int imageWidth) {
        return imageHeight * screenWidth / imageWidth;
    }


}
