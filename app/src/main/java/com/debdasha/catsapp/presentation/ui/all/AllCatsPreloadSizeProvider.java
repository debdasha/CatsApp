package com.debdasha.catsapp.presentation.ui.all;

import com.bumptech.glide.util.FixedPreloadSizeProvider;

public class AllCatsPreloadSizeProvider extends FixedPreloadSizeProvider {
    private final static int imageWidthPixels = 1024;
    private final static int imageHeightPixels = 768;

    public AllCatsPreloadSizeProvider() {
        super(imageWidthPixels, imageHeightPixels);
    }
}
