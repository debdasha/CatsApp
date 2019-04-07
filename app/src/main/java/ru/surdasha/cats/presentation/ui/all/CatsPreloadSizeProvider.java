package ru.surdasha.cats.presentation.ui.all;

import com.bumptech.glide.util.FixedPreloadSizeProvider;

public class CatsPreloadSizeProvider extends FixedPreloadSizeProvider {
    private final static int imageWidthPixels = 1024;
    private final static int imageHeightPixels = 768;

    public CatsPreloadSizeProvider() {
        super(imageWidthPixels, imageHeightPixels);
    }
}
