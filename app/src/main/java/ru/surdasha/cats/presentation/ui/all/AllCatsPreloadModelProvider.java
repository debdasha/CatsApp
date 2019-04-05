package ru.surdasha.cats.presentation.ui.all;

import android.content.Context;
import android.text.TextUtils;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import ru.surdasha.cats.presentation.GlideApp;
import ru.surdasha.cats.presentation.models.CatUI;

public class AllCatsPreloadModelProvider implements ListPreloader.PreloadModelProvider<CatUI> {
    private List<CatUI> catUIS;
    private Context context;
    private int width;

    public AllCatsPreloadModelProvider(Context context, List<CatUI> catUIS,int width){
        this.catUIS = catUIS;
        this.context = context;
        this.width = width;
    }

    @Override
    @NonNull
    public List<CatUI> getPreloadItems(int position) {
        String url = catUIS.get(position).getUrl();
        if (TextUtils.isEmpty(url)) {
            return Collections.emptyList();
        }
        return Collections.singletonList(catUIS.get(position));
    }

    @Override
    @Nullable
    public RequestBuilder<?> getPreloadRequestBuilder(CatUI model) {
        return
                GlideApp.with(context)
                        .load(model.getUrl())
                        .placeholder(getCircularProgressDrawable(context))
                        .override(width, getScreenHeight(model))
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)));
    }

    private CircularProgressDrawable getCircularProgressDrawable(Context context) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(3f);
        circularProgressDrawable.setCenterRadius(20f);
        circularProgressDrawable.start();
        return circularProgressDrawable;
    }

    private int getScreenHeight(CatUI catUI) {
        int imageHeight = catUI.getImageHeight() * width / catUI.getImageWidth();
        return imageHeight;
    }
}
