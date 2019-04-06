package ru.surdasha.cats.presentation.misc;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

public class ViewUtils {

    public static int getScreenWidth(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;
    }

    public static int countAspectRatioHeight(int screenWidth, int imageHeight, int imageWidth){
        int newImageHeight = imageHeight * screenWidth / imageWidth;
        return newImageHeight;
    }

    public static CircularProgressDrawable createCircularImageDrawable(Context context) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(3f);
        circularProgressDrawable.setCenterRadius(20f);
        circularProgressDrawable.start();
        return circularProgressDrawable;
    }

}
