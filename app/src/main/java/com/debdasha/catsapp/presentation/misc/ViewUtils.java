package com.debdasha.catsapp.presentation.misc;

import android.app.Activity;
import android.util.DisplayMetrics;

public class ViewUtils {
    private Activity activity;

    public ViewUtils(Activity context){
        this.activity = context;
    }

    public int getScreenWidth(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;
    }



}
